package com.example.recordapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvResult = findViewById(R.id.tv_result);
        ListView listView = findViewById(R.id.list_view);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // 1. 获取传过来的 Bundle 数据
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String amount = bundle.getString("AMOUNT");
            String type = bundle.getString("TYPE");
            String category = bundle.getString("CATEGORY");

            // 展示在红字 TextView 上
            tvResult.setText("最新记录：" + type + " - " + category + " - " + amount + "元");
        }

        // 2. 准备 ListView 的数据源
        List<String> historyList = new ArrayList<>();
        historyList.add("支出 - 餐饮 - 35.0元");
        historyList.add("支出 - 交通 - 12.5元");
        historyList.add("收入 - 其他 - 2000.0元");

        // 把刚传过来的数据也加到列表第一条
        if (bundle != null) {
            historyList.add(0, bundle.getString("TYPE") + " - " + bundle.getString("CATEGORY") + " - " + bundle.getString("AMOUNT") + "元");
        }

        // 3. 使用 ArrayAdapter 绑定数据
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                historyList
        );
        listView.setAdapter(adapter);

        // 4. 返回按钮点击事件
        btnBack.setOnClickListener(v -> finish());
    }
}