package com.example.recordapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvLatest = findViewById(R.id.tv_latest);
        ListView listView = findViewById(R.id.list_view);
        ImageButton btnBack = findViewById(R.id.btn_back);

        // 1. 接收 Intent 传来的最新数据（满足作业要求：至少一次传值）
        String latestRecord = getIntent().getStringExtra("LATEST_RECORD");
        if (latestRecord != null) {
            tvLatest.setText("最新保存：" + latestRecord);
        }

        // 2. 直接从 MainActivity 的静态变量中读取历史记录（满足数据不丢失）
        List<String> displayList = MainActivity.globalRecordList;

        // 3. 设置适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                displayList
        );
        listView.setAdapter(adapter);

        // 4. 返回按钮
        btnBack.setOnClickListener(v -> finish());
    }
}