package com.example.myapplication;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // 檢查並申請錄音權限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }

        Button btnFlat = findViewById(R.id.btnFlat);
        Button btnSlope = findViewById(R.id.btnSlope);
        Button btnMember = findViewById(R.id.btnMember);

        // 平面模式 (Mode 0)
        btnFlat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(0);
                Toast.makeText(MainActivity.this,"你選擇平面模式",Toast.LENGTH_LONG).show();
            }
        });

        // 斜面模式 (Mode 1)
        btnSlope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(1);
                Toast.makeText(MainActivity.this,"你選擇斜面模式",Toast.LENGTH_LONG).show();
            }
        });

        // 會員專屬模式 (Mode 2) - 只有這裡有摩擦力
        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame(2);
                Toast.makeText(MainActivity.this,"你選擇會員專屬模式",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void startGame(int mode) {
        Intent intent = new Intent(MainActivity.this, SimulationActivity.class);
        intent.putExtra("MODE", mode);
        startActivity(intent);
    }
}