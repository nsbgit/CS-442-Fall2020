package com.christopherhield.twoactivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static int NAME_REQUEST = 123;
    private EditText nameText;
    private TextView responseText, greetText;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = findViewById(R.id.nameText);
        responseText = findViewById(R.id.responseText);
        greetText = findViewById(R.id.textView3);

    }

    public void goToNext(View v) {

        name = nameText.getText().toString();

        if (name.trim().isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("NAME", name);
        startActivityForResult(intent, NAME_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NAME_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null && data.hasExtra("STATUS")) {
                    String status = data.getStringExtra("STATUS");
                    nameText.setVisibility(View.INVISIBLE);
                    responseText.setText(String.format("Hello %s!%n%nI'm glad you are %s.", name, status));
                    findViewById(R.id.button3).setVisibility(View.INVISIBLE);
                    greetText.setText(R.string.i_know_you);
                }
            }
        }
    }
}
