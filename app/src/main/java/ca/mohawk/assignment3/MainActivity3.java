package ca.mohawk.assignment3;

import static ca.mohawk.assignment3.MainActivity.conversionRates;

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

    private static final String PREFS_NAME = "CurrencyPrefs";
    private static final String PREF_CURRENCY_INDEX = "currency_index";

    private Spinner currencySpinner;
    private TextView conversionRateText;
    private Button saveButton;
    private int selectedIndex = 0;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        currencySpinner = findViewById(R.id.spinner);
        conversionRateText = findViewById(R.id.textView7);
        saveButton = findViewById(R.id.button3);

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        selectedIndex = sharedPreferences.getInt(PREF_CURRENCY_INDEX, 0);
        currencySpinner.setSelection(selectedIndex);
        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedIndex = position;
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
        editor.putInt(PREF_CURRENCY_INDEX, selectedIndex);
        editor.apply();
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);

        finish();  // Close activity and return to MainActivity
    }

    private void updateRateDisplay() {
        conversionRateText.setText("" + conversionRates[selectedIndex]);
    }



}