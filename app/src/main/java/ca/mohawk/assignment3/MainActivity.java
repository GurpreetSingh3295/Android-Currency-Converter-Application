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
    public static final String PREF_SOURCE_CURRENCY = "source_currency";
    public static final String PREF_TARGET_CURRENCY = "target_currency";

    private EditText inputAmount;
    private TextView convertedAmount;
    private ImageView sourceFlag;
    private ImageView targetFlag;
    private Button convertButton;
    private Button configButton;


    private String[] currencyNames = {"USD", "Canada", "Saudi Arabia", "Qatar", "Kuwait"};
    private int[] currencyFlags = {R.drawable.usa, R.drawable.canada, R.drawable.saudi, R.drawable.qatar, R.drawable.kuwait};
    public static double[][] conversionRates = {
            {1.0, 0.37, 4.56, 0.30, 0.25},
            {2.7, 1.0, 12.34, 0.81, 0.25},
            {0.22, 0.081, 1.0, 0.065, 0.25},
            {3.33, 1.23, 15.38, 1.0, 0.25},
            {4.00, 1.50, 16.00, 1.10, 1.0}
    };

    private int sourceCurrencyIndex = 0;
    private int targetCurrencyIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputAmount = findViewById(R.id.editTextNumberDecimal);
        convertedAmount = findViewById(R.id.textView2);
        sourceFlag = findViewById(R.id.imageView);
        targetFlag = findViewById(R.id.imageView2);
        convertButton = findViewById(R.id.button);
        configButton = findViewById(R.id.button2);
        loadPreferences();
        updateUI();
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
            double converted = amount * conversionRates[sourceCurrencyIndex][targetCurrencyIndex];
            convertedAmount.setText(String.format("%.2f", converted));
        } catch (NumberFormatException e) {
            convertedAmount.setText("Invalid input");
        }
    }



    public void nextActivity (View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity3.class);
        startActivityForResult(intent, 1);
    }


    private void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        sourceCurrencyIndex = prefs.getInt(PREF_SOURCE_CURRENCY, 0);
        targetCurrencyIndex = prefs.getInt(PREF_TARGET_CURRENCY, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            loadPreferences();
            updateUI();
        }
    }

    private void updateUI() {
        sourceFlag.setImageResource(currencyFlags[sourceCurrencyIndex]);
        targetFlag.setImageResource(currencyFlags[targetCurrencyIndex]);
        convertButton.setText("@" + conversionRates[sourceCurrencyIndex][targetCurrencyIndex] + " =");
    }
}