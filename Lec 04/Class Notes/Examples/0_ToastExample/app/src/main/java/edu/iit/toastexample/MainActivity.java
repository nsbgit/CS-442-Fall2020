package edu.iit.toastexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "MainActivity onCreate",
                Toast.LENGTH_LONG).show();
    }

    public void makeTime(View v) {
        String time = new Date().toString();
        Toast.makeText(this, time, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "MainActivity onStop",
                Toast.LENGTH_LONG).show();

        super.onStop();
    }
}