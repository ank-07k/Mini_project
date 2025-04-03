package com.example.miniproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;

public class ShowExcelDataActivity extends AppCompatActivity {

    Button btnOpenExcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.excel_data);

        btnOpenExcel = findViewById(R.id.btnOpenExcel);

        btnOpenExcel.setOnClickListener(view -> openExcelFile());
    }

    private void openExcelFile() {
        File file = new File(getExternalFilesDir(null), "YogaAppData.xlsx");

        if (!file.exists()) {
            Toast.makeText(this, "Excel file not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "No Excel app found to open the file", Toast.LENGTH_SHORT).show();
        }
    }
}
