package com.sukanta.distanceconverter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    enum TYPE_OF_CONVERSION {
        MILES_TO_KILOMETERS,
        KILOMETERS_TO_MILES
    }

    private TYPE_OF_CONVERSION typeOfConversion = TYPE_OF_CONVERSION.MILES_TO_KILOMETERS;
    private String converstionTypeString = "Mi to Km: ";
    private final String TAG = "MainActivity";
    private final String KEY_HISTORY = "HISTORY";
    private final String KEY_OUTPUT = "OUTPUT";
    private final String KEY_TYPE = "TYPE";
    private String conversationHistory = new String();
    //private String[] = new String[]();

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

    private void restoreSavedState(Bundle savedInstanceState) {
        try {
            conversationHistory = savedInstanceState.getString(KEY_HISTORY);
            tvConversionHistory.setText(conversationHistory);

            String outputValue = savedInstanceState.getString(KEY_OUTPUT);
            tvOutputValue.setText(outputValue);

            typeOfConversion = (TYPE_OF_CONVERSION)savedInstanceState.get(KEY_TYPE);
            updateScreenElments();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Something went wrong.",
                    Toast.LENGTH_SHORT).show();
            etInputValue.setText("");
            Log.e(TAG, "restoreSavedState: ", e);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Code here
        outState.putString(KEY_HISTORY, conversationHistory);
        outState.putString(KEY_OUTPUT, tvOutputValue.getText().toString());
        outState.putSerializable(KEY_TYPE, typeOfConversion);

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);

        // Code here
        if (savedInstanceState != null)
            restoreSavedState(savedInstanceState);
    }

    public void rgMiKm_Clicked(View v) {
        try {
            switch (v.getId()) {
                case R.id.rbMiToKm:
                    typeOfConversion = TYPE_OF_CONVERSION.MILES_TO_KILOMETERS;
                    break;
                case R.id.rbKmToMi:
                    typeOfConversion = TYPE_OF_CONVERSION.KILOMETERS_TO_MILES;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Something went wrong.",
                    Toast.LENGTH_SHORT).show();
            etInputValue.setText("");
            Log.e(TAG, "rgMiKm_Clicked: ", e);
        }
        finally {
            updateScreenElments();
        }
    }

    private void updateScreenElments() {
        switch (typeOfConversion) {
            case MILES_TO_KILOMETERS:
                tvInputLable.setText(R.string.miles_value);
                tvOutputLable.setText(R.string.kilometers_value);
                converstionTypeString = "Mi to Km: ";
                break;
            case KILOMETERS_TO_MILES:
                tvInputLable.setText(R.string.kilometers_value);
                tvOutputLable.setText(R.string.miles_value);
                converstionTypeString = "Km to Mi: ";
                break;
        }
    }

    public void bConvert_Clicked(View v) {
        try {
            String inputString = etInputValue.getText().toString();

            if (inputString == null || inputString.equals("")) {
                Toast.makeText(getApplicationContext(),
                        "Please enter a value.",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            etInputValue.setText("");
            double inputValue = Double.parseDouble(inputString);
            double resultValue = calculate(inputValue);

            String pattern = "0.0";
            char leftArrow = '\u2794';
            DecimalFormat numberFormat = new DecimalFormat(pattern);
            String resultString = numberFormat.format(resultValue);
            String historyString = converstionTypeString + inputString + " " + leftArrow + " " + resultString;
            addToHistory(historyString);
            tvOutputValue.setText(resultString);
            tvConversionHistory.setText(conversationHistory);
        } catch (NumberFormatException e) {
            //e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Number is not in proper format.",
                    Toast.LENGTH_SHORT).show();
            etInputValue.setText("");
            Log.e(TAG, "bConvert_Clicked: ", e);
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),
                    "Something went wrong.",
                    Toast.LENGTH_SHORT).show();
            etInputValue.setText("");
            Log.e(TAG, "bConvert_Clicked: ", e);
        }
    }

    private void addToHistory(String historyString) {
        conversationHistory = String.format("%s\n%s", historyString, conversationHistory);
    }

    public void bClear_Clicked(View v) {
        conversationHistory = "";
        tvConversionHistory.setText(conversationHistory);
    }

    private void createReferenceForScreenElements() {
        try {
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
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "Something went wrong.",
                    Toast.LENGTH_SHORT).show();
            etInputValue.setText("");
            Log.e(TAG, "createReferenceForScreenElements: ", e);
        }
    }

    public double calculate(double inputValue) {
        double conversionFactorMilesToKilometers = 1.60934;
        double conversionFactorKilometersToMiles = 0.621371;
        double resultValue = 0.0;

        switch (typeOfConversion) {
            case MILES_TO_KILOMETERS:
                resultValue = inputValue * conversionFactorMilesToKilometers;
                break;
            case KILOMETERS_TO_MILES:
                resultValue = inputValue * conversionFactorKilometersToMiles;
                break;
        }

        return resultValue;
    }
}