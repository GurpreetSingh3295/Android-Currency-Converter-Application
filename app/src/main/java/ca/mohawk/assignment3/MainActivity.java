package ca.mohawk.assignment3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "CurrencyConverter";
    public static final String PREFS_NAME = "CurrencyPrefs";
    public static final String PREF_CURRENCY_INDEX = "currency_index";

    private EditText inputAmount;
    private TextView convertedAmount;
    private ImageView currencyFlag;
    private Button convertButton;
    private Button configButton;


    public static final int[] currencyFlags = {R.drawable.usa, R.drawable.canada, R.drawable.saudi ,R.drawable.qatar, R.drawable.kuwait};
    public static double[] conversionRates = {0.68, 1, 2.25, 2.52, 0.21};
    private int selectedCurrencyIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputAmount = findViewById(R.id.editTextNumberDecimal);
        convertedAmount = findViewById(R.id.textView2);
        currencyFlag = findViewById(R.id.imageView2);
        convertButton = findViewById(R.id.button);
        configButton = findViewById(R.id.button2);


    }
    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
        updateUI();

    }

    public void convertCurrency(View view) {
        try {
            String inputText = inputAmount.getText().toString().trim();
            if (inputText.isEmpty()) {
                convertedAmount.setText("Enter a valid amount");
                return;
            }
            double amount = Double.parseDouble(inputText);
            if (amount < 0) {
                convertedAmount.setText("Amount cannot be negative");
                return;
            }
            double converted = amount * conversionRates[selectedCurrencyIndex];
            convertedAmount.setText(String.format("%.2f", converted));
        } catch (NumberFormatException e) {
            convertedAmount.setText("Invalid input");
        }
    }

    public void nextActivity (View view) {
        Intent switch2Activity2 = new Intent(MainActivity.this, MainActivity3.class);
        startActivityForResult(switch2Activity2,1);
    }


    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        selectedCurrencyIndex = prefs.getInt(PREF_CURRENCY_INDEX, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadPreferences();  // Reload saved preferences
            updateUI();         // Update flag and UI
        }
    }

    private void updateUI() {
        currencyFlag.setImageResource(currencyFlags[selectedCurrencyIndex]);
        convertButton.setText("@" + conversionRates[selectedCurrencyIndex] + " =");
    }
}