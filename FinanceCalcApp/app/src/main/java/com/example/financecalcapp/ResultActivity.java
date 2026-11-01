package com.example.financecalcapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvTotal = findViewById(R.id.tv_total);
        ListView listView = findViewById(R.id.list_view);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // 接收数据
        double principal = getIntent().getDoubleExtra("PRINCIPAL", 0);
        double rate = getIntent().getDoubleExtra("RATE", 0);
        int years = getIntent().getIntExtra("YEARS", 1);
        boolean isCompound = getIntent().getBooleanExtra("IS_COMPOUND", false);

        // 计算总收益
        double totalAmount;
        if (isCompound) {
            totalAmount = principal * Math.pow((1 + rate), years);
        } else {
            totalAmount = principal * (1 + rate * years);
        }
        double totalProfit = totalAmount - principal;

        // 显示结果 (加入 Locale.CHINA 修复警告)
        String resultStr = String.format(Locale.CHINA, "总本息和：%.2f 元\n预期总收益：%.2f 元", totalAmount, totalProfit);
        tvTotal.setText(resultStr);

        // 生成明细列表
        List<String> details = new ArrayList<>();
        int[] months = {1, 6, 12, 24, 36};
        for (int m : months) {
            if (m <= years * 12) {
                double t = (double) m / 12;
                double amt;
                if (isCompound) {
                    amt = principal * Math.pow((1 + rate), t);
                } else {
                    amt = principal * (1 + rate * t);
                }
                details.add(String.format(Locale.CHINA, "第 %d 个月：预期本息 %.2f 元", m, amt));
            }
        }

        // 使用 ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                details
        );
        listView.setAdapter(adapter);

        // 使用 Lambda 表达式简化返回按钮
        btnBack.setOnClickListener(v -> finish());
    }
}