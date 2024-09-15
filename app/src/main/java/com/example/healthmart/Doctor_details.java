package com.example.healthmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Doctor_details extends AppCompatActivity {

    private FirebaseFirestore db;
    private ListView doctorListView;
    private ArrayAdapter<String> adapter;
    private List<String> doctorList;
    private List<Map<String, Object>> doctorDataList; // To store detailed doctor info
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize ListView and TextView
        doctorListView = findViewById(R.id.listViewDoctors);
        textViewTitle = findViewById(R.id.textviewDDtitle);

        // Get the doctor type from the previous activity
        Intent it = getIntent();
        String title = it.getStringExtra("title");
        textViewTitle.setText(title);
        String doctorType = textViewTitle.getText().toString();

        // Set the title text based on the selected type
        Log.e("Doctor type", String.valueOf(doctorType));
        if (doctorType != null) {
            textViewTitle.setText(doctorType);
        } else {
            textViewTitle.setText("Doctors List");
        }

        // Initialize the lists and adapter
        doctorList = new ArrayList<>();
        doctorDataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, doctorList);
        doctorListView.setAdapter(adapter);

        // Fetch the doctors by type
        fetchDoctorsByType(doctorType);

        // Set an onClickListener to handle item clicks
        doctorListView.setOnItemClickListener((parent, view, position, id) -> {
            // Get the selected doctor data
            Map<String, Object> selectedDoctor = doctorDataList.get(position);

            // Pass data to BookAppointmentActivity
            Intent intent = new Intent(Doctor_details.this, BookAppointment.class);
            intent.putExtra("doctor_name", (String) selectedDoctor.get("name"));
            intent.putExtra("doctor_contact", (String) selectedDoctor.get("contact"));
            intent.putExtra("doctor_address", (String) selectedDoctor.get("address"));
            intent.putExtra("doctor_fees", (Double) selectedDoctor.get("consultancyFees"));

            // Start the new activity
            startActivity(intent);
        });
    }

    private void fetchDoctorsByType(String doctorType) {
        // Check if the doctor type is not null
        if (doctorType == null || doctorType.isEmpty()) {
            Toast.makeText(this, "No doctor type specified", Toast.LENGTH_SHORT).show();
            return;
        }

        // Reference to the "doctors" collection in Firestore
        CollectionReference doctorRef = db.collection("doctors");

        // Fetch doctors of the selected type from Firestore
        doctorRef.whereEqualTo("type", doctorType)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        doctorList.clear(); // Clear the previous data
                        doctorDataList.clear(); // Clear detailed doctor data
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Retrieve the doctor details
                            String name = document.getString("name");
                            String contact = document.getString("contact");
                            String address = document.getString("address");
                            double consultancyFees = document.getDouble("consultancyFees");

                            // Store detailed doctor info in a Map
                            Map<String, Object> doctorData = new HashMap<>();
                            doctorData.put("name", name);
                            doctorData.put("contact", contact);
                            doctorData.put("address", address);
                            doctorData.put("consultancyFees", consultancyFees);

                            // Add doctor data to the list
                            doctorDataList.add(doctorData);

                            // Format the details into a string for display
                            String doctorDetails = "Name: " + name + "\nContact: " + contact +
                                    "\nAddress: " + address + "\nFees: " + consultancyFees;
                            doctorList.add(doctorDetails);
                        }
                        if (doctorList.isEmpty()) {
                            // Show a message if no doctors are found
                            Toast.makeText(this, "No doctors found for the selected type.", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged(); // Refresh the adapter
                    } else {
                        // Show an error message if fetching data fails
                        Toast.makeText(this, "Failed to load data from Firestore.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}


