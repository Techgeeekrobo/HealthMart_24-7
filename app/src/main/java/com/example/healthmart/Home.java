package com.example.healthmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        SharedPreferences sf = getSharedPreferences("shared_pref", Context.MODE_PRIVATE);
       String username = sf.getString("username","").toString();
        Toast.makeText(this,"Welcome  "+username,Toast.LENGTH_LONG).show();
        CardView exit = findViewById(R.id.Logout);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor ed = sf.edit();
                ed.clear();
                ed.apply();
                startActivity(new Intent(Home.this, Login.class));

            }
        } );
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CardView finddoctor = findViewById(R.id.cardFindDoctor);
        finddoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Find_doctor.class));
            }
        });
        CardView buymedicine = findViewById(R.id.cardBuyMedicine);
        buymedicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, BuyMedicine.class));
            }
        });




    }
}