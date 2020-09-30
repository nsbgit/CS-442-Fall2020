package com.christopherhield.act2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int OTHER_CODE = 123;
    private Person person;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        person = new Person("John Smith", 49.99);

        textView = findViewById(R.id.textView);
        textView.setText(person.toString());
    }

    public void openAct2(View v) {

        Intent intent = new Intent(this, OtherActivity.class);
        intent.putExtra("Person", person);
        intent.putExtra("Time", System.currentTimeMillis());

        startActivityForResult(intent, OTHER_CODE);
    }

    @Override
    protected void onActivityResult(
            int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OTHER_CODE){
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    person = (Person) data.getSerializableExtra("PERSON");
                    if (person != null) {
                        textView.setText(person.toString());
                    }
                }
            } else {
                Toast.makeText(this, "OTHER result not OK!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Unexpected code received: " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }
}
