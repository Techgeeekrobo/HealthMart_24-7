package com.example.healthmart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Find_doctor extends AppCompatActivity {

    // Firebase Firestore instance
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_doctor);

        // Set up CardView click listeners for different doctor types
        setupCardViewListeners();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupCardViewListeners() {
        CardView family = findViewById(R.id.cardFDfamilyphysician);
        family.setOnClickListener(view -> {
            Intent it = new Intent(Find_doctor.this, Doctor_details.class);
            it.putExtra("title", "physician");
            startActivity(it);
        });

        CardView dietician = findViewById(R.id.cardFDdietician);
        dietician.setOnClickListener(view -> {
            Intent it = new Intent(Find_doctor.this, Doctor_details.class);
            it.putExtra("title", "dietician");
            startActivity(it);
        });

        CardView dentist = findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(view -> {
            Intent it = new Intent(Find_doctor.this, Doctor_details.class);
            it.putExtra("title", "dentist");
            startActivity(it);
        });

        CardView surgeon = findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(view -> {
            Intent it = new Intent(Find_doctor.this, Doctor_details.class);
            it.putExtra("title", "surgeon");
            startActivity(it);
        });

        CardView heart = findViewById(R.id.cardiologist);
        heart.setOnClickListener(view -> {
            Intent it = new Intent(Find_doctor.this, Doctor_details.class);
            it.putExtra("title", "cardiologist");
            startActivity(it);
        });
    }
}
