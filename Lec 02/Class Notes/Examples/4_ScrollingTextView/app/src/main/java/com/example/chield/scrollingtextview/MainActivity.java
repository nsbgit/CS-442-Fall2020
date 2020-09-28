package com.example.chield.scrollingtextview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;

    private static final String text1 = "This is a one-line TextView";
    private static final String text2 = "This is a multi-line TextView without scrolling\n";
    private static final String text3 = "This is a multi-line TextView WITH scrolling\n";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #1
        textView1 = findViewById(R.id.oneLine);

        // #2
        textView2 = findViewById(R.id.multiLine);

        // #3
        textView3 = findViewById(R.id.multiLineScroll);

        // This next line is required for proper scrolling behavior
        textView3.setMovementMethod(new ScrollingMovementMethod());

        setValues();
    }

    private void setValues() {
        textView1.setText(text1);

        StringBuilder sb = new StringBuilder(text2);
        for (int i = 0; i < 20; i++)
            sb.append(String.format(Locale.getDefault(), "Line #%d%n", i));
        textView2.setText(sb.toString());

        sb = new StringBuilder(text3);
        for (int i = 0; i < 20; i++)
            sb.append(String.format(Locale.getDefault(), "Line #%d%n", i));
        textView3.setText(sb.toString());

    }
}
