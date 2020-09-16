package com.sukanta.distanceconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    enum TYPE_OF_CONVERSION {
        MILES_TO_KILOMETERS,
        KILOMETERS_TO_MILES
    }

    TYPE_OF_CONVERSION typeOfConversion = TYPE_OF_CONVERSION.MILES_TO_KILOMETERS;

    // reference of screen elements
    private RadioGroup rgMiKm;
    private RadioButton rbMiToKm, rbKmToMi;
    private TextView tvInputLable, tvOutputLable, tvOutputValue, tvConversionHistory;
    private EditText etInputValue;
    private Button bConvert, bClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createReferenceForScreenElements();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Code here

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        // Code here
    }

    public void rgMiKm_Clicked(View v) {
        switch (v.getId()) {
            case R.id.rbMiToKm:
                typeOfConversion = TYPE_OF_CONVERSION.MILES_TO_KILOMETERS;
                tvInputLable.setText(R.string.miles_value);
                tvOutputLable.setText(R.string.kilometers_value);
                break;
            case R.id.rbKmToMi:
                typeOfConversion = TYPE_OF_CONVERSION.KILOMETERS_TO_MILES;
                tvInputLable.setText(R.string.kilometers_value);
                tvOutputLable.setText(R.string.miles_value);
                break;
        }
    }

    public void bConvert_Clicked(View v) {}

    public void bClear_Clicked(View v) {}

    private void createReferenceForScreenElements() {
        rgMiKm = findViewById(R.id.rgMiKm);
        rbMiToKm = findViewById(R.id.rbMiToKm);
        rbKmToMi = findViewById(R.id.rbKmToMi);
        tvInputLable = findViewById(R.id.tvInputLable);
        tvOutputLable = findViewById(R.id.tvOutputLable);
        tvOutputValue = findViewById(R.id.tvOutputValue);
        tvConversionHistory = findViewById(R.id.tvConversionHistory);
        tvConversionHistory.setMovementMethod(new ScrollingMovementMethod());
        etInputValue = findViewById(R.id.etInputValue);
        bConvert = findViewById(R.id.bConvert);
        bClear = findViewById(R.id.bClear);
    }

    public float calculate(float input) {
        float conversionFactorMilesToKilometers = 1.60934f;
        float conversionFactorKilometersToMiles = 0.621371f;
        float result = 0.0f;

        switch (typeOfConversion) {
            case MILES_TO_KILOMETERS:
                result = input * conversionFactorMilesToKilometers;
                break;
            case KILOMETERS_TO_MILES:
                result = input * conversionFactorKilometersToMiles;
                break;
        }

        return result;
    }
}