package ca.mohawk.assignment3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity3 extends AppCompatActivity {

    private static final String PREF_CURRENCY_INDEX = "currency_index";
    private Spinner sourceCurrencySpinner;
    private Spinner targetCurrencySpinner;
    private TextView conversionRateText;
    private Button saveButton;
    private int sourceIndex = 0;
    private int targetIndex = 1;
    private int selectedIndex = 0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        sourceCurrencySpinner = findViewById(R.id.sourceSpinner);
        targetCurrencySpinner = findViewById(R.id.targetSpinner);
        conversionRateText = findViewById(R.id.textView7);
        saveButton = findViewById(R.id.button3);

        sharedPreferences = getSharedPreferences(MainActivity.PREFS_NAME, Context.MODE_PRIVATE);
        sourceIndex = sharedPreferences.getInt(MainActivity.PREF_SOURCE_CURRENCY, 0);
        targetIndex = sharedPreferences.getInt(MainActivity.PREF_TARGET_CURRENCY, 1);

        sourceCurrencySpinner.setSelection(sourceIndex);
        targetCurrencySpinner.setSelection(targetIndex);
        sourceCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sourceIndex = position;
                updateRateDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        targetCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                targetIndex = position;
                updateRateDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        updateRateDisplay();


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }


    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MainActivity.PREF_SOURCE_CURRENCY, sourceIndex);
        editor.putInt(MainActivity.PREF_TARGET_CURRENCY, targetIndex);
        editor.apply();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);

        finish();  // Close activity and return to MainActivity
    }





    private void updateRateDisplay() {
        conversionRateText.setText("Conversion Rate = " + MainActivity.conversionRates[sourceIndex][targetIndex]);
    }



}