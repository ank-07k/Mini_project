package com.example.miniproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class signup extends AppCompatActivity {

    EditText etName, etMobile, etAge, etBatch, etHealthIssues;
    Button btnSubmit, btnShowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etAge = findViewById(R.id.etAge);
        etBatch = findViewById(R.id.etBatch);
        etHealthIssues = findViewById(R.id.etHealthIssues);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnShowData = findViewById(R.id.btnShowData);

        btnSubmit.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String mobile = etMobile.getText().toString().trim();
            String age = etAge.getText().toString().trim();
            String batch = etBatch.getText().toString().trim();
            String healthIssues = etHealthIssues.getText().toString().trim();

            if (name.isEmpty() || mobile.isEmpty() || age.isEmpty() || batch.isEmpty()) {
                Toast.makeText(signup.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                exportToExcel(name, mobile, age, batch, healthIssues);
            }
        });

        btnShowData.setOnClickListener(view -> openExcelFile());
    }

    private void exportToExcel(String name, String mobile, String age, String batch, String healthIssues) {
        File file = new File(getExternalFilesDir(null), "YogaAppData.xlsx");
        Workbook workbook;
        Sheet sheet;

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet("Users");
                if (sheet == null) {
                    sheet = workbook.createSheet("Users");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error reading Excel file", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Users");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Name");
            header.createCell(1).setCellValue("Mobile");
            header.createCell(2).setCellValue("Age");
            header.createCell(3).setCellValue("Batch");
            header.createCell(4).setCellValue("Health Issues");
        }

        int rowCount = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue(name);
        row.createCell(1).setCellValue(mobile);
        row.createCell(2).setCellValue(age);
        row.createCell(3).setCellValue(batch);
        row.createCell(4).setCellValue(healthIssues);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);
            workbook.close();
            Toast.makeText(this, "Data saved. Opening Excel...", Toast.LENGTH_SHORT).show();
            openExcelFile();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save data", Toast.LENGTH_SHORT).show();
        }
    }

    private void openExcelFile() {
        File file = new File(getExternalFilesDir(null), "YogaAppData.xlsx");
        if (file.exists()) {
            Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent, "Open Excel"));
        } else {
            Toast.makeText(this, "Excel file not found", Toast.LENGTH_SHORT).show();
        }
    }
}
