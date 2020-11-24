package com.christopherhield.imagedownload;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
    }

    public void loadImage(View v) {
        switch (v.getId()) {
            case R.id.button1:
                loadRemoteImage(getString(R.string.image1));
                break;
            case R.id.button2:
                loadRemoteImage(getString(R.string.image2));
                break;
            case R.id.button3:
                loadRemoteImage(getString(R.string.image3));
                break;
        }
    }

    private void loadRemoteImage(final String imageURL) {
        // Needs gradle  implementation 'com.squareup.picasso:picasso:2.71828'

        final long start = System.currentTimeMillis(); // Used for timing

        Picasso.get().load(imageURL)
                .error(R.drawable.image_not_found)
                .placeholder(R.drawable.placeholder)
                //.into(imageView); // Use this if you don't want a callback
                .into(imageView,
                        new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "onSuccess: Size: " +
                                        ((BitmapDrawable) imageView.getDrawable()).getBitmap().getByteCount());
                                long dur = System.currentTimeMillis() - start;
                                Log.d(TAG, "onSuccess: Time: " + dur);
                            }

                            @Override
                            public void onError(Exception e) {
                                Log.d(TAG, "onError: " + e.getMessage());
                            }
                        });
    }

}
