package com.example.christopher.runnable;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView timeValue;
    private ProgressBar progressBar;
    private TextView textView;
    private TextView runnableStatusText;
    private boolean running = false;

    public static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS", Locale.US);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.secondsEditText);
        timeValue = findViewById(R.id.timeValue);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.startEndText);
        runnableStatusText = findViewById(R.id.runnableStatus);
    }

    public void doRunnable(View v) {
        if (running) {
            Toast.makeText(this, "Wait for Task to complete", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please provide a seconds-to-delay value", Toast.LENGTH_SHORT).show();
            return;
        }

        running = true;
        runnableStatusText.setText(getString(R.string.a_running));
        long delay = Long.parseLong(editText.getText().toString());

        progressBar.setMax((int) delay);
        progressBar.setProgress(0);

        textView.setText(String.format("%s START", MainActivity.formatter.format(new Date())));

        MyRunnable myRunnable = new MyRunnable(this, delay);
        new Thread(myRunnable).start();
    }

    public void updateTime(View v) {
        // onClick for the activity button
        timeValue.setText(formatter.format(new Date()));
    }

    public void updateProgressBar(int value) {
        progressBar.setProgress(value);
    }

    public void whenRunnableIsDone(String string) {
        running = false;
        runnableStatusText.setText(getString(R.string.a_stopped));
        textView.setText(String.format("%s\n%s END", textView.getText().toString(), string));
        Toast.makeText(this, "Runnable Complete", Toast.LENGTH_SHORT).show();
    }
}
