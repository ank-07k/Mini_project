package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnLogin, btnSignUp;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            btnLogin = findViewById(R.id.btnLogin);
            btnSignUp = findViewById(R.id.btnSignUp);

            btnLogin.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this,login.class);
                startActivity(intent);
            });

            btnSignUp.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            });
        }
    }
