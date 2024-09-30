package com.example.healthmart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private EditText username, password;
    private Button submit;
    private TextView gotoSignUp;
    private FirebaseAuth auth;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "UserSession";
    private static final String IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and SharedPreferences
        auth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.button);
        gotoSignUp = findViewById(R.id.Register);

        // Check if the user is already logged in
        // NOTE: This will now require the user to log in again after a device restart
        if (isUserLoggedIn()) {
            Intent intent = new Intent(Login.this, Home.class);
            startActivity(intent);
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if (uname.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                } else {
                    auth.signInWithEmailAndPassword(uname, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    saveUserSession(true); // Save session state
                                    Intent intent = new Intent(Login.this, Home.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                                    Log.e("LoginError", e.getMessage());
                                }
                            });
                }
            }
        });

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear user session when the activity is destroyed (user must log in again)
        clearUserSession();
    }

    // Save user session in SharedPreferences
    private void saveUserSession(boolean isLoggedIn) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    // Clear user session in SharedPreferences
    private void clearUserSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(IS_LOGGED_IN);
        editor.apply();
    }

    // Check if the user session is saved
    private boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
