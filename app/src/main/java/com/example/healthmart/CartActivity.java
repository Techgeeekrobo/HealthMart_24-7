package com.example.healthmart;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthmart.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    SimpleAdapter sa;
    ArrayList<HashMap<String, String>> cartList;
    FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);
        cartList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // Fetch cart items from Firestore
        fetchCartItems();
    }

    private void fetchCartItems() {
        db.collection("labtest_cart")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            populateCartList(querySnapshot.getDocuments());
                        } else {
                            Toast.makeText(CartActivity.this, "Cart is empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CartActivity.this, "Failed to fetch cart items", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateCartList(List<DocumentSnapshot> documents) {
        for (DocumentSnapshot document : documents) {
            String packageName = document.getString("packageName");
            String packageDetails = document.getString("packageDetails");
            String price = document.getString("price");

            HashMap<String, String> item = new HashMap<>();
            item.put("line1", packageName);
            item.put("line2", packageDetails);
            item.put("line3", "Price: " + price + "/-");

            cartList.add(item);
        }

        // Setup the adapter for the ListView
        sa = new SimpleAdapter(
                this,
                cartList,
                R.layout.cartlayout,
                new String[]{"line1", "line2", "line3"},
                new int[]{R.id.line_a_cart, R.id.line_b_cart, R.id.line_c_cart}
        );
        listViewCart.setAdapter(sa);
    }
}
