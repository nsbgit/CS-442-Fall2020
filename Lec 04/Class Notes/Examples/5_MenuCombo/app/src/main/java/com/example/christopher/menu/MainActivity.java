package com.example.christopher.menu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuA:
                textView.setText(R.string.a);
                return true;
            case R.id.menuB:
                textView.setText(R.string.b);
                return true;
            case R.id.menuC:
                textView.setText(R.string.c);
                return true;
            case R.id.menuD:
                textView.setText(R.string.d);
                return true;
            case R.id.menuE:
                textView.setText(R.string.e);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
