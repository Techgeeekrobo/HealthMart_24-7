package com.example.healthmart;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Checkout extends AppCompatActivity {

    private EditText nameField, addressField, phoneField;
    private Button placeOrderButton;
    private Button datetime;
    private int selectedYear, selectedMonth, selectedDay;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_checkout);
        nameField = findViewById(R.id.editTextName1);
        addressField = findViewById(R.id.editTextAddress1);
        phoneField = findViewById(R.id.editTextPhone1);
        datetime = findViewById(R.id.buttonDatePicker1);
        placeOrderButton = findViewById(R.id.buttonPlaceOrder1);

        // Initialize date with the current date
        final Calendar calendar = Calendar.getInstance();
        selectedYear = calendar.get(Calendar.YEAR);
        selectedMonth = calendar.get(Calendar.MONTH);
        selectedDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Show DatePickerDialog when clicking the datetime button
        datetime.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    Checkout.this,
                    (view, year, monthOfYear, dayOfMonth) -> {
                        selectedYear = year;
                        selectedMonth = monthOfYear;
                        selectedDay = dayOfMonth;
                        // Update button text with the selected date
                        datetime.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    },
                    selectedYear, selectedMonth, selectedDay);

            // Show the DatePickerDialog
            datePickerDialog.show();
        });

        placeOrderButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String address = addressField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();

            // Validate input fields
            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                Toast.makeText(Checkout.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                return; // Exit the method if validation fails
            }

            // Validate phone number (basic validation)
            if (phone.length() < 10) {
                Toast.makeText(Checkout.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                return; // Exit the method if validation fails
            }

            // Get selected delivery date from DatePickerDialog
            Calendar deliveryDate = Calendar.getInstance();
            deliveryDate.set(selectedYear, selectedMonth, selectedDay);

            // Show success message and navigate to BuyMedicine activity
            Toast.makeText(Checkout.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Checkout.this, Lab.class);
            startActivity(intent);

            // Add code here to save the order details (name, address, phone, deliveryDate) to Firebase or any backend
        });


    }
}