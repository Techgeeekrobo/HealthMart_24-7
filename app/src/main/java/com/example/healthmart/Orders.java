package com.example.healthmart;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Orders extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;

    // Firebase Firestore instance
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;  // Firebase Authentication instance
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        // Initialize views and Firebase instances
        listView = findViewById(R.id.ordersListView);
        dataList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();  // Get the current logged-in user

        // Set up adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();
            // Fetch and display data from both Orders and Appointments collections for the logged-in user
            fetchAppointments(userEmail);
            fetchOrders(userEmail);
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAppointments(String userEmail) {
        db.collection("appointments")
                .whereEqualTo("email", userEmail)  // Filter by logged-in user's email
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                // Retrieve appointment details
                                String appointmentDate = document.getString("appointment_date");
                                String appointmentTime = document.getString("appointment_time");
                                String doctorName = document.getString("doctor_name");
                                String doctorAddress = document.getString("doctor_address");
                                Long doctorFees = document.getLong("doctor_fees");

                                // Build a string to display the appointment details
                                String appointmentDetails = "Appointment - Doctor: " + doctorName + "\n" +
                                        "Date: " + appointmentDate + "\n" +
                                        "Time: " + appointmentTime + "\n" +
                                        "Address: " + doctorAddress + "\n" +
                                        "Fees: " + (doctorFees != null ? doctorFees : "N/A");

                                // Add the string to the data list
                                dataList.add(appointmentDetails);
                            }
                            adapter.notifyDataSetChanged();  // Notify the adapter about the updated data
                        } else {
                            Toast.makeText(Orders.this, "No Appointments found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("Firebase", "Error getting documents: ", task.getException());
                        Toast.makeText(Orders.this, "Error fetching appointments", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchOrders(String userEmail) {
        db.collection("Orders")
                .whereEqualTo("user_email", userEmail)  // Filter by logged-in user's email
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                // Extract the list of items (array of medicine objects)
                                List<Map<String, Object>> items = (List<Map<String, Object>>) document.get("items");

                                if (items != null && !items.isEmpty()) {
                                    for (Map<String, Object> item : items) {
                                        String medicineName = (String) item.get("name");
                                        Double priceDouble = (Double) item.get("price"); // Cast to Double
                                        long totalPrice = priceDouble != null ? priceDouble.longValue() : 0; // Convert to Long

                                        // Build a string to display the order details
                                        String orderDetails = "Order - Medicine: " + (medicineName != null ? medicineName : "Unknown") + "\n" +
                                                "Total Price: " + totalPrice;

                                        // Add the string to the data list
                                        dataList.add(orderDetails);
                                    }
                                }
                            }
                            adapter.notifyDataSetChanged();  // Notify the adapter about the updated data
                        } else {
                            Toast.makeText(Orders.this, "No Orders found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("Firebase", "Error getting documents: ", task.getException());
                        Toast.makeText(Orders.this, "Error fetching orders", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
