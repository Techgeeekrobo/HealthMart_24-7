package com.example.healthmart;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Labdetails extends AppCompatActivity {

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnAddToCart, btnBack;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labdetails);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        tvPackageName = findViewById(R.id.textViewLDPackageName);
        tvTotalCost = findViewById(R.id.textViewLDTotalCost);
        edDetails = findViewById(R.id.editTextLDTextMultiLine);
        btnAddToCart = findViewById(R.id.buttonLDAddToCart);
        btnBack = findViewById(R.id.buttonLDBack);

        edDetails.setKeyListener(null); // Set EditText as non-editable

        // Retrieve data from Intent
        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost : " + intent.getStringExtra("text3") + "/-");

        // Back button listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Labdetails.this, Lab.class));
            }
        });

        // Add to Cart button listener
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(intent);
            }
        });
    }

    // Method to add the selected package to the cart
    private void addToCart(Intent intent) {
        // Prepare the cart item data
        String packageName = intent.getStringExtra("text1");
        String packageDetails = intent.getStringExtra("text2");
        String price = intent.getStringExtra("text3");

        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("packageName", packageName);
        cartItem.put("packageDetails", packageDetails);
        cartItem.put("price", price);

        // Add item to Firestore "labtest_cart" collection
        db.collection("labtest_cart")
                .add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(Labdetails.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(Labdetails.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                });
    }
}
