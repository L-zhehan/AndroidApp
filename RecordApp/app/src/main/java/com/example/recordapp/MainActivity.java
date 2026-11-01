package com.example.recordapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static List<String> globalRecordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RadioGroup rgType = findViewById(R.id.rg_type);
        Spinner spinnerCategory = findViewById(R.id.spinner_category);
        EditText etAmount = findViewById(R.id.et_amount);
        Button btnSave = findViewById(R.id.btn_save);


        updateSpinner(spinnerCategory, R.array.expense_array);

        // 监听单选框切换，动态改变下拉框内容
        rgType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_expense) {
                updateSpinner(spinnerCategory, R.array.expense_array);
            } else if (checkedId == R.id.rb_income) {
                updateSpinner(spinnerCategory, R.array.income_array);
            }
        });

        // 保存按钮逻辑
        btnSave.setOnClickListener(v -> {
            String amount = etAmount.getText().toString().trim();
            if (amount.isEmpty()) {
                Toast.makeText(MainActivity.this, R.string.toast_empty, Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double money = Double.parseDouble(amount);
                // 格式化金额，保留两位小数
                String formattedMoney = String.format(java.util.Locale.CHINA, "%.2f", money);

                // 获取选中的类型和类别
                int selectedId = rgType.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedId);
                String type = rb.getText().toString(); // 支出/收入
                String category = spinnerCategory.getSelectedItem().toString(); // 餐饮/工资等

                // 拼接一条记录（例如："支出 - 餐饮 - 35.00元"）
                String recordItem = type + " - " + category + " - " + formattedMoney + "元";

                // 将当前数据存入全局 List
                globalRecordList.add(0, recordItem); // 加在第一条

                // Intent传值，演示给老师看
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("LATEST_RECORD", recordItem);
                startActivity(intent);

                // 清空输入框，方便下次输入
                etAmount.setText("");

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, R.string.toast_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 辅助方法：更新下拉框数据
    private void updateSpinner(Spinner spinner, int arrayResId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}