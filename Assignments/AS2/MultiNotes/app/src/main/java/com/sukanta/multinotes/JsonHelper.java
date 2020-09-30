package com.sukanta.multinotes;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsonHelper {
    private static final String format = "yyMMddHHmmssZ";
    DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);

    public void writeJsonStream(OutputStream out, List<Note> noteList) throws Exception {
        try (JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {
            writer.setIndent("  ");
            writeNoteListArray(writer, noteList);
        }
    }

    public void writeNoteListArray(JsonWriter writer, List<Note> noteList) throws Exception {
        try {
            writer.beginArray();
            for (Note note : noteList) {
                writeNote(writer, note);
            }
        } finally {
            writer.endArray();
        }
    }

    public void writeNote(JsonWriter writer, Note note) throws Exception {
        try {
            writer.beginObject();
            writer.name("title").value(note.getTitle());
            writer.name("text").value(note.getText());
            writer.name("lastUpdatedTime").value(dateFormat.format(note.getLastUpdatedTime()));
        } finally {
            writer.endObject();
        }
    }

    public void readJsonStream(InputStream in, List<Note> noteList) throws Exception {
        try (JsonReader reader = new JsonReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            readNoteListArray(reader, noteList);
        }
    }

    public void readNoteListArray(JsonReader reader, List<Note> noteList) throws Exception {
        try {
            reader.beginArray();
            while (reader.hasNext()) {
                noteList.add(readNote(reader));
            }
        } finally {
            reader.endArray();
        }
    }

    public Note readNote(JsonReader reader) throws Exception {
        Note note = new Note();

        try {
            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();

                switch (name) {
                    case "title":
                        note.setTitle(reader.nextString());
                        break;
                    case "text":
                        note.setText(reader.nextString());
                        break;
                    case "lastUpdatedTime":
                        String dateString = reader.nextString();

                        if (dateString == null)
                            note.setLastUpdatedTime(new Date());
                        else {
                            try {
                                note.setLastUpdatedTime(dateFormat.parse(dateString));
                            } catch (ParseException e) {
                                note.setLastUpdatedTime(new Date());
                            }
                        }
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
        } finally {
            reader.endObject();
        }

        return note;
    }
}
