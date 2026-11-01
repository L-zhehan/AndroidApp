package com.example.recordapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rgType = findViewById(R.id.rg_type);
        Spinner spinnerCategory = findViewById(R.id.spinner_category);
        EditText etAmount = findViewById(R.id.et_amount);
        Button btnSave = findViewById(R.id.btn_save);

        // 使用 Lambda 表达式，代码更干净
        btnSave.setOnClickListener(v -> {
            String amount = etAmount.getText().toString().trim();
            if (amount.isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.toast_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            // 获取 RadioGroup 选中的值
            int selectedId = rgType.getCheckedRadioButtonId();
            RadioButton rb = findViewById(selectedId);
            String type = rb.getText().toString();

            // 获取 Spinner 选中的值
            String category = spinnerCategory.getSelectedItem().toString();

            // Bundle 传值 + 页面跳转
            Bundle bundle = new Bundle();
            bundle.putString("AMOUNT", amount);
            bundle.putString("TYPE", type);
            bundle.putString("CATEGORY", category);

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}