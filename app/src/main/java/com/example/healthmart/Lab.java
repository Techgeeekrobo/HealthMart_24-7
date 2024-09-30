package com.example.healthmart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lab extends AppCompatActivity {

    // Array of package details
    private String[][] packages = {
            {"Package 1 : Full Body Checkup", "", "", "", "999"},
            {"Package 2 : Blood Glucose Fasting", "", "", "", "299"},
            {"Package 3 : COVID-19 Antibody - IgG", "", "", "", "899"},
            {"Package 4 : Thyroid Check", "", "", "", "499"},
            {"Package 5 : Immunity Check", "", "", "", "699"}
    };

    private String[] package_details = {
            "Blood Glucose Fasting\n" +
                    "Complete Hemogram\n" +
                    "HbA1c\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "LDH Lactate Dehydrogenase, Serum\n" +
                    "Lipid Profile\n" +
                    "Liver Function Test",
            "Blood Glucose Fasting\n" +  "The fasting plasma glucose test is the simplest and fastest way to measure blood glucose and diagnose diabetes.\n" +  "Fasting means that you have had nothing to eat or drink (except water) for 8 to 12 hours before the test.",
            "COVID-19 Antibody ",
            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\n" +
                    "CRP (C Reactive Protein) Quantitative, Serum\n" +
                    "Iron Studies\n" +
                    "Kidney Function Test\n" +
                    "Vitamin D Total-25 Hydroxy\n" +
                    "Liver Function Test\n" +
                    "Lipid Profile"
    };

    ArrayList<HashMap<String, String>> list;
    SimpleAdapter sa;
    Button btnGoToCart, btnBack,checkout;
    ListView listView;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        FirebaseApp.initializeApp(this); // Initialize Firebase

        db = FirebaseFirestore.getInstance(); // Initialize Firestore

        // Button and ListView setup
        btnGoToCart = findViewById(R.id.buttonLToCart);  // Ensure this ID is correct
        btnBack = findViewById(R.id.buttonLBack);
        listView = findViewById(R.id.listViewLT);
        checkout = findViewById(R.id.buttonCheckout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Lab.this,Checkout.class);
                startActivity(i);

            }
        });

        // Preparing data for the ListView
        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("line1", packages[i][0]);
            item.put("line5", "Total Cost: " + packages[i][4] + "/-");
            list.add(item);
        }

        // Setting up SimpleAdapter for ListView
        sa = new SimpleAdapter(
                this,
                list,
                R.layout.layout,  // Ensure this layout file exists and is correct
                new String[]{"line1", "line5"},
                new int[]{R.id.line_a, R.id.line_e}
        );
        listView.setAdapter(sa);

        // On item click to view package details

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Navigate to the lab details screen
                Intent it = new Intent(getApplicationContext(), Labdetails.class);
                it.putExtra("text1", packages[i][0]); // Package Name
                it.putExtra("text2", package_details[i]); // Package Details
                it.putExtra("text3", packages[i][4]); // Package Price
                startActivity(it);
            }
        });

        // "Go to Cart" button click listener
        btnGoToCart.setOnClickListener(view -> {
            // Navigate to Cart Activity
            Intent intent = new Intent(Lab.this, CartActivity.class);
            startActivity(intent);
        });

        // Long click to add item to cart
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            // Add the selected package to the cart
            addToCart(position);
            return true; // True indicates the event has been handled
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lab.this,Home.class));
            }
        });
    }


    // Method to add selected package to Firestore Cart
    private void addToCart(int position) {
        // Creating a map for the cart item
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("packageName", packages[position][0]); // Package Name
        cartItem.put("packageDetails", package_details[position]); // Package Details
        cartItem.put("price", packages[position][4]); // Package Price

        // Adding cart item to "labtest_cart" collection in Firestore
        db.collection("labtest_cart")
                .add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    // Display success message
                    Toast.makeText(Lab.this, "Added to cart!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Display error message
                    Toast.makeText(Lab.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                });
    }
}
