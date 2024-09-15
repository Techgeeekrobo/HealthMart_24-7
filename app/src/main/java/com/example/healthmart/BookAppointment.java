package com.example.healthmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookAppointment extends AppCompatActivity {

    private TextView doctorName, doctorContact, doctorAddress, doctorFees;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button book;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // Initializing Views
        doctorName = findViewById(R.id.textViewDoctorName);
        doctorContact = findViewById(R.id.textViewDoctorContact);
        doctorAddress = findViewById(R.id.textViewDoctorAddress);
        doctorFees = findViewById(R.id.textViewDoctorFees);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        book = findViewById(R.id.buttonBookAppointment);


        // Get data from intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("doctor_name");
        String contact = intent.getStringExtra("doctor_contact");
        String address = intent.getStringExtra("doctor_address");
        double fees = intent.getDoubleExtra("doctor_fees", 0.0);

        // Set doctor data
        doctorName.setText("Doctor Name: " + name);
        doctorContact.setText("Contact: " + contact);
        doctorAddress.setText("Address: " + address);
        doctorFees.setText("Consultancy Fees: $" + fees);

        // Set default Date and Time (optional)
        timePicker.setIs24HourView(true);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"AppointMent Booked",Toast.LENGTH_LONG).show();
            }
        });

    }
}
