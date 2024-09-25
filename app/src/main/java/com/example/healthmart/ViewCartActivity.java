package com.example.healthmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewCartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private FirebaseFirestore db;
    Button placeOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        placeOrderButton = findViewById(R.id.buttonPlaceOrder);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();

        // Load Cart Items
        loadCartItems();
        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
                Intent I = new Intent(ViewCartActivity.this,placeorder.class);
                startActivity(I);

            }
        });
    }

    private void placeOrder() {
        if (cartItemList != null && !cartItemList.isEmpty()) {
            // Create an order in Firestore
            Map<String, Object> order = new HashMap<>();
            order.put("items", cartItemList);
            order.put("timestamp", System.currentTimeMillis());

            // Add order to Firestore "Orders" collection
            db.collection("Orders").add(order).addOnSuccessListener(documentReference -> {
                // Clear the cart once the order is placed
                clearCart();

                Toast.makeText(ViewCartActivity.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            }).addOnFailureListener(e -> {
                Toast.makeText(ViewCartActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
        }
    }


    private void loadCartItems() {
        db.collection("Cart").get().addOnSuccessListener(queryDocumentSnapshots -> {
            cartItemList = new ArrayList<>();
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                CartItem cartItem = doc.toObject(CartItem.class);
                cartItemList.add(cartItem);
            }
            // Set up adapter
            cartAdapter = new CartAdapter(this, cartItemList);
            cartRecyclerView.setAdapter(cartAdapter);
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
        });
    }

    private void clearCart() {
        db.collection("Cart").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot doc : queryDocumentSnapshots) {
                doc.getReference().delete();
            }
            // Clear the cart in the UI as well
            cartItemList.clear();
            cartAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Failed to clear cart", Toast.LENGTH_SHORT).show();
        });
    }
}

