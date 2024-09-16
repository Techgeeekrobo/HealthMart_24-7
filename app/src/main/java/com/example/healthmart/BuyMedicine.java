package com.example.healthmart;

import android.content.Intent;
import android.os.Bundle;
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

public class BuyMedicine extends AppCompatActivity {
    private RecyclerView medicineRecyclerView;
    private MedicineAdapter adapter;
    private List<Medicine> medicineList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine);

        medicineRecyclerView = findViewById(R.id.medicineRecyclerView);
        medicineRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

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
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("name", medicine.getName());
        cartItem.put("price", medicine.getPrice());

        db.collection("Cart").add(cartItem).addOnSuccessListener(documentReference -> {
            Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
        });
    }
}
