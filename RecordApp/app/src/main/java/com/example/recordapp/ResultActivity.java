package com.example.recordapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvLatest = findViewById(R.id.tv_latest);
        TextView tvTotalExpense = findViewById(R.id.tv_total_expense);
        TextView tvTotalIncome = findViewById(R.id.tv_total_income);
        ListView listView = findViewById(R.id.list_view);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // 接收 Intent 传来的最新数据
        String latestRecord = getIntent().getStringExtra("LATEST_RECORD");
        if (latestRecord != null) {
            tvLatest.setText("最新保存：" + latestRecord);
        }

        // 获取历史记录列表
        List<String> displayList = MainActivity.globalRecordList;

        // 计算总支出和总收入
        double totalExpense = 0.0;
        double totalIncome = 0.0;

        for (String record : displayList) {
            // 字符串格式是："支出 - 餐饮 - 35.00元"
            String[] parts = record.split(" - ");
            if (parts.length == 3) {
                String type = parts[0]; // "支出" 或 "收入"
                String amountStr = parts[2].replace("元", ""); // 去掉"元"字

                try {
                    double amount = Double.parseDouble(amountStr);
                    if (type.equals("支出")) {
                        totalExpense += amount;
                    } else if (type.equals("收入")) {
                        totalIncome += amount;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        tvTotalExpense.setText(String.format(Locale.CHINA, "总支出：%.2f 元", totalExpense));
        tvTotalIncome.setText(String.format(Locale.CHINA, "总收入：%.2f 元", totalIncome));

        // 设置 ListView 适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );
        listView.setAdapter(adapter);

        // 返回按钮
        btnBack.setOnClickListener(v -> finish());
    }
}