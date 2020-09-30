package com.example.christopher.activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int B_REQUEST_CODE = 1;
    private static final String TAG = "MainActivity";
    private TextView userText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userText = findViewById(R.id.userText);

    }

    public void doA(View v) {
        Intent intent = new Intent(MainActivity.this, ActivityA.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivity(intent);
    }

    public void doB(View v) {
        Intent intent = new Intent(MainActivity.this, ActivityB.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.class.getSimpleName());
        startActivityForResult(intent, B_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == B_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String text = data.getStringExtra("USER_TEXT_IDENTIFIER");

                if (text == null) {
                    Toast.makeText(this, "Null text value returned", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text.isEmpty())
                    Toast.makeText(this, "Empty text returned", Toast.LENGTH_SHORT).show();

                userText.setText(text);

                Log.d(TAG, "onActivityResult: User Text: " + text);
            } else {
                Log.d(TAG, "onActivityResult: result Code: " + resultCode);
            }

        } else {
            Log.d(TAG, "onActivityResult: Request Code " + requestCode);
        }
    }

}
