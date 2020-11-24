package com.christopherhield.invisibility;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void changeImageVisibility(View v) {
        CheckBox cb = findViewById(R.id.imageCheckBox);
        ImageView iv = findViewById(R.id.imageView);
        if (cb.isChecked())
            iv.setVisibility(View.VISIBLE);
        else
            iv.setVisibility(View.INVISIBLE);
    }

    public void changeTextVisibility(View v) {
        CheckBox cb = findViewById(R.id.textCheckBox);
        TextView tv = findViewById(R.id.textView);
        if (cb.isChecked())
            tv.setVisibility(View.VISIBLE);
        else
            tv.setVisibility(View.INVISIBLE);
    }
}
