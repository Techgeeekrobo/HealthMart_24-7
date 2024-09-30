package com.example.healthmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyMedicine extends AppCompatActivity {
    private RecyclerView medicineRecyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private FirebaseFirestore db;
    private Button viewCartButton,exit;
    private FirebaseAuth mAuth;  // Firebase Auth for getting the logged-in user

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        medicineRecyclerView = findViewById(R.id.medicineRecyclerView);
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         exit = findViewById(R.id.backButton1);
         exit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent I = new Intent(BuyMedicine.this,Home.class);
                 startActivity(I);

             }
         });
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();  // Initialize FirebaseAuth

        // Fetch medicines from Firestore
        loadMedicines();

        // Setup adapter
        adapter = new MedicineAdapter(this, new ArrayList<>(), new MedicineAdapter.OnAddToCartClickListener() {
            @Override
            public void onAddToCart(Medicine medicine) {
                addToCart(medicine);
            }
        });
        medicineRecyclerView.setAdapter(adapter);

        // Setup View Cart button
        viewCartButton = findViewById(R.id.viewCartButton);
        viewCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch ViewCartActivity to display all items in the cart
                Intent intent = new Intent(BuyMedicine.this, ViewCartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadMedicines() {
        db.collection("Medicines").get().addOnSuccessListener(queryDocumentSnapshots -> {
            medicineList = new ArrayList<>();
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                Medicine medicine = doc.toObject(Medicine.class);
                medicineList.add(medicine);
            }
            adapter.setMedicineList(medicineList);
            adapter.notifyDataSetChanged();
        });
    }

    private void addToCart(Medicine medicine) {
        FirebaseUser currentUser = mAuth.getCurrentUser();  // Get current logged-in user
        if (currentUser != null) {
            String userEmail = currentUser.getEmail();  // Get user's email
            Map<String, Object> cartItem = new HashMap<>();
            cartItem.put("name", medicine.getName());
            cartItem.put("price", medicine.getPrice());

            // Insert user's email in the correct field as "email"
            cartItem.put("email", userEmail);  // Use "email" as the field name

            db.collection("Cart").add(cartItem).addOnSuccessListener(documentReference -> {
                Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                // Log the error and display a failure message
                Toast.makeText(this, "Failed to add to cart: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        } else {
            // If no user is logged in, show an error or redirect to login
            Toast.makeText(this, "Please login to add items to cart.", Toast.LENGTH_SHORT).show();

            // Optional: Redirect to login activity
            Intent loginIntent = new Intent(BuyMedicine.this, Login.class);
            startActivity(loginIntent);
        }
    }

}
