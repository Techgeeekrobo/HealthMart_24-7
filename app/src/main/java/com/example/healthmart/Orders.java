package com.example.healthmart;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Orders extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> dataList;

    // Firebase Firestore instance
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        listView = findViewById(R.id.ordersListView);
        dataList = new ArrayList<>();

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // Set up adapter for ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        // Fetch and display data from both Orders and Appointments collections
        fetchAppointments();
        fetchOrders(); // Fetch orders as well
    }

    private void fetchAppointments() {
        db.collection("appointments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                            for (DocumentSnapshot document : documents) {
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
                                        "Fees: " + doctorFees;

                                // Add the string to the data list
                                dataList.add(appointmentDetails);
                            }
                            // Notify the adapter about the updated data
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Orders.this, "No Appointments found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("Firebase", "Error getting documents: ", task.getException());
                        Toast.makeText(Orders.this, "Error fetching appointments", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchOrders() {
        db.collection("Orders")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                String medicineName = document.getString("medicine_name");
                                String orderQuantity = document.getString("quantity");
                                String deliveryAddress = document.getString("delivery_address");
                                Long totalPrice = document.getLong("total_price");

                                // Build a string to display the order details
                                String orderDetails = "Order - Medicine: " + medicineName + "\n" +

                                        "Price: " + orderQuantity + "\n" +
                                        "Address: " + deliveryAddress + "\n" +
                                        "Total Price: " + totalPrice;

                                // Add the string to the data list
                                dataList.add(orderDetails);
                            }
                            // Notify the adapter about the updated data
                            adapter.notifyDataSetChanged();
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
