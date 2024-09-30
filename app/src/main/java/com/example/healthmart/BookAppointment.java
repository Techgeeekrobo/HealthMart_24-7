package com.example.healthmart;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class BookAppointment extends AppCompatActivity {

    private TextView doctorName, doctorContact, doctorAddress, doctorFees;
    private TextView selectedDate, selectedTime;
    private Button book;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private String appointmentDate = "";
    private String appointmentTime = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // Initializing Views
        doctorName = findViewById(R.id.textViewDoctorName);
        doctorContact = findViewById(R.id.textViewDoctorContact);
        doctorAddress = findViewById(R.id.textViewDoctorAddress);
        doctorFees = findViewById(R.id.textViewDoctorFees);
        selectedDate = findViewById(R.id.textViewDate);
        selectedTime = findViewById(R.id.textViewTime);
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

        // Date picker dialog
        selectedDate.setOnClickListener(v -> showDatePickerDialog());

        // Time picker dialog
        selectedTime.setOnClickListener(v -> showTimePickerDialog());

        // Book appointment button click
        book.setOnClickListener(view -> bookAppointment(name, contact, address, fees));
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
            appointmentDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear; // Month is 0-indexed
            selectedDate.setText("Selected Date: " + appointmentDate);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
            appointmentTime = selectedHour + ":" + String.format("%02d", selectedMinute); // Format minutes
            selectedTime.setText("Selected Time: " + appointmentTime);
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void bookAppointment(String name, String contact, String address, double fees) {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Create a Map to store the appointment data
            Map<String, Object> appointmentData = new HashMap<>();
            appointmentData.put("doctor_name", name);
            appointmentData.put("doctor_contact", contact);
            appointmentData.put("doctor_address", address);
            appointmentData.put("doctor_fees", fees);
            appointmentData.put("appointment_date", appointmentDate);
            appointmentData.put("appointment_time", appointmentTime);
            appointmentData.put("email", userEmail);

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
        } else {
            // If no user is logged in, show an error or redirect to login
            Toast.makeText(BookAppointment.this, "User not logged in. Please login to book an appointment.", Toast.LENGTH_SHORT).show();

            // Optional: Redirect to login activity
            Intent loginIntent = new Intent(BookAppointment.this, Login.class);
            startActivity(loginIntent);
        }
    }
}

