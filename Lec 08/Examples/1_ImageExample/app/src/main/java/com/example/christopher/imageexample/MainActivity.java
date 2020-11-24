package com.example.christopher.imageexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void imageButtonClick(View v) {
        Toast.makeText(this, "You clicked the ImageButton!", Toast.LENGTH_SHORT).show();
    }

    public void imageClick(View v) {
        Toast.makeText(this, "You clicked an ImageView with an onClick!", Toast.LENGTH_SHORT).show();
    }

}
