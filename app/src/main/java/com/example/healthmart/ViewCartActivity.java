package com.example.healthmart;

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

public class ViewCartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList = new ArrayList<>(); // Initialize it here to avoid null
    private FirebaseFirestore db;
    private Button placeOrderButton;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        placeOrderButton = findViewById(R.id.buttonPlaceOrder);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        db = FirebaseFirestore.getInstance();

        // Clear the list and load fresh items
        cartItemList.clear();
        loadCartItems();

        placeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
                Intent I = new Intent(ViewCartActivity.this, placeorder.class);
                startActivity(I);
            }
        });
    }

    private void placeOrder() {
        if (cartItemList != null && !cartItemList.isEmpty()) {
            // Check if the user is logged in
            if (currentUser != null) {
                String userEmail = currentUser.getEmail(); // Get the logged-in user's email

                // Create an order map with cart items and user email
                List<Map<String, Object>> orderItems = new ArrayList<>();

                // Loop through cart items and convert each item to a map
                for (CartItem cartItem : cartItemList) {
                    Map<String, Object> itemMap = new HashMap<>();
                    itemMap.put("name", cartItem.getName());
                    itemMap.put("price", cartItem.getPrice());
                    orderItems.add(itemMap); // Add the item map to the order list
                }

                Map<String, Object> order = new HashMap<>();
                order.put("items", orderItems);
                order.put("timestamp", System.currentTimeMillis());
                order.put("user_email", userEmail); // Store the user's email

                // Add order to Firestore "Orders" collection
                db.collection("Orders").add(order)
                        .addOnSuccessListener(documentReference -> {
                            // Clear the cart once the order is placed
                            clearCart();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ViewCartActivity.this, "Failed to place order", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(this, "User is not logged in", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadCartItems() {
        // Ensure the list is empty before loading new data
        cartItemList.clear();

        db.collection("Cart")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        CartItem cartItem = doc.toObject(CartItem.class);
                        cartItemList.add(cartItem);
                    }

                    // Check if the cart is still empty after loading data
                    if (cartItemList.isEmpty()) {
                        Toast.makeText(this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                    }

                    // Set up adapter after data is loaded
                    cartAdapter = new CartAdapter(this, cartItemList);
                    cartRecyclerView.setAdapter(cartAdapter);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to load cart items", Toast.LENGTH_SHORT).show();
                });
    }

    private void clearCart() {
        // Clear cart data from Firestore and update UI
        db.collection("Cart")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        doc.getReference().delete();
                    }
                    // Clear the cart list and notify adapter of changes
                    cartItemList.clear();
                    cartAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to clear cart", Toast.LENGTH_SHORT).show();
                });
    }
}
