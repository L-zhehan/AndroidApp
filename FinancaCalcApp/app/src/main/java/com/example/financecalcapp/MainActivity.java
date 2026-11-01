package com.example.financecalcapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etPrincipal = findViewById(R.id.et_principal);
        RadioGroup rgRisk = findViewById(R.id.rg_risk);
        Spinner spinnerYears = findViewById(R.id.spinner_years);
        CheckBox cbCompound = findViewById(R.id.cb_compound);
        Button btnCalc = findViewById(R.id.btn_calc);

        // 使用 Lambda 表达式简化点击事件
        btnCalc.setOnClickListener(v -> {
            String principalStr = etPrincipal.getText().toString();
            if (principalStr.isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.toast_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            double principal = Double.parseDouble(principalStr);

            // 获取风险等级及对应利率
            int selectedId = rgRisk.getCheckedRadioButtonId();
            double rate = 0.03; // 默认3%
            if (selectedId == R.id.rb_mid) rate = 0.05;
            if (selectedId == R.id.rb_high) rate = 0.08;

            // 获取年限
            int yearIndex = spinnerYears.getSelectedItemPosition();
            int years = 1;
            if (yearIndex == 1) years = 3;
            if (yearIndex == 2) years = 5;

            // 是否复利
            boolean isCompound = cbCompound.isChecked();

            // Intent 传值
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra("PRINCIPAL", principal);
            intent.putExtra("RATE", rate);
            intent.putExtra("YEARS", years);
            intent.putExtra("IS_COMPOUND", isCompound);
            startActivity(intent);
        });
    }
}