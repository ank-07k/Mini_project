package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        Button btnViewReport = findViewById(R.id.btnViewReport);
        btnViewReport.setOnClickListener(view -> {
            Intent intent = new Intent(profile.this, ReportActivity.class);
            startActivity(intent);
        });
    }
}
