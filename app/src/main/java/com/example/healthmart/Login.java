package com.example.healthmart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Login extends AppCompatActivity {
    EditText un;
    EditText pass;
    Button log;
    TextView tv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        un = findViewById(R.id.username);
        pass = findViewById(R.id.password);
        log = findViewById(R.id.button);
        tv = findViewById(R.id.Register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = un.getText().toString().trim();
                String password = pass.getText().toString().trim();
                Database db = new Database(getApplicationContext(),"healthmart",null,1);

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with login logic here
                    if(db.login(username,password)==1){
                        Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(Login.this,Home.class));

                    }else {
                        Toast.makeText(Login.this, "Invalid username/password", Toast.LENGTH_SHORT).show();
                    }




                }
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
}
