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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class BookAppointment extends AppCompatActivity {

    private TextView doctorName, doctorContact, doctorAddress, doctorFees;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button book;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

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
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;  // Month is 0-indexed
                int year = datePicker.getYear();

                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();

                // Create a Map to store the appointment data
                Map<String, Object> appointmentData = new HashMap<>();
                appointmentData.put("doctor_name", name);
                appointmentData.put("doctor_contact", contact);
                appointmentData.put("doctor_address", address);
                appointmentData.put("doctor_fees", fees);
                appointmentData.put("appointment_date", day + "/" + month + "/" + year);
                appointmentData.put("appointment_time", hour + ":" + minute);

                // Store data in Firestore
                db.collection("appointments")
                        .add(appointmentData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Appointment Booked", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Failed to book appointment", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });
    }
}
