package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class login extends AppCompatActivity {

    EditText etMobile;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Update this to match your layout file

        etMobile = findViewById(R.id.email);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(view -> {
            String mobile = etMobile.getText().toString().trim();
            if (mobile.isEmpty()) {
                Toast.makeText(login.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();
            } else {
                if (validateLogin(mobile)) {
                    Intent intent = new Intent(login.this, profile.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(login.this, "Invalid mobile number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String mobile) {
        File file = new File(getExternalFilesDir(null), "YogaAppData.xlsx");
        if (!file.exists()) {
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file)) {
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("Users");
            if (sheet == null) {
                return false;
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null && row.getCell(1) != null) {
                    String storedMobile = row.getCell(1).getStringCellValue();
                    if (storedMobile.equals(mobile)) {
                        return true;
                    }
                }
            }
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
