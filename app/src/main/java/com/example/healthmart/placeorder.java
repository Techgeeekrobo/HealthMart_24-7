package com.example.healthmart;


import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class placeorder extends AppCompatActivity {

    private EditText nameField, addressField, phoneField;
    private DatePicker datePicker;
    private Button placeOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placeorder);

        // Initialize Views
        nameField = findViewById(R.id.editTextName);
        addressField = findViewById(R.id.editTextAddress);
        phoneField = findViewById(R.id.editTextPhone);
        datePicker = findViewById(R.id.datePicker);
        placeOrderButton = findViewById(R.id.buttonPlaceOrder);

        // Set listener for Place Order button
        placeOrderButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String address = addressField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            // Get selected delivery date
            Calendar deliveryDate = Calendar.getInstance();
            deliveryDate.set(year, month, day);

            // Validate input

            // Show success message


            // Code to handle storing order data in the backend can be added here

        });
    }
}
