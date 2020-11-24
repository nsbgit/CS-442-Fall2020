package com.christopherhield.linkifyexamples;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView url = findViewById(R.id.url);
        TextView phone = findViewById(R.id.phone);
        TextView phone2 = findViewById(R.id.phone2);
        TextView address = findViewById(R.id.address);
        TextView email = findViewById(R.id.email);

        Linkify.addLinks(url, Linkify.ALL);
        Linkify.addLinks(phone, Linkify.ALL);
        Linkify.addLinks(phone2, Linkify.ALL);
        Linkify.addLinks(address, Linkify.ALL);
        Linkify.addLinks(email, Linkify.ALL);

    }

}
