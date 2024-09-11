package com.example.healthmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Find_doctor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_find_doctor);
        CardView  family= findViewById(R.id.cardFDfamilyphysician);
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Find_doctor.this, Doctor_details.class);
                it.putExtra("title","Family Physicians");
                startActivity(it);
            }
        });
        CardView  dietician= findViewById(R.id.cardFDdietician);
        dietician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Find_doctor.this, Doctor_details.class);
                it.putExtra("title","Dietician");
                startActivity(it);
            }
        });
        CardView  dentist= findViewById(R.id.cardFDDentist);
        dentist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Find_doctor.this, Doctor_details.class);
                it.putExtra("title","Dentist");
                startActivity(it);
            }
        });
        CardView  surgeon= findViewById(R.id.cardFDSurgeon);
        surgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Find_doctor.this, Doctor_details.class);
                it.putExtra("title","Surgeons");
                startActivity(it);
            }
        });
        CardView  heart= findViewById(R.id.cardiologist);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Find_doctor.this, Doctor_details.class);
                it.putExtra("title","Cardiologists");
                startActivity(it);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}