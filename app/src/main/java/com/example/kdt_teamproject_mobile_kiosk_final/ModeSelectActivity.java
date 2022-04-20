package com.example.kdt_teamproject_mobile_kiosk_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.kdt_teamproject_mobile_kiosk_final.admin.AdminMenuActivity;
import com.example.kdt_teamproject_mobile_kiosk_final.kiosk.KioskMainActivity;

public class ModeSelectActivity extends AppCompatActivity {

    Button btnAdminMode, btnKioskMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        btnAdminMode = findViewById(R.id.btnAdminMode);
        btnKioskMode = findViewById(R.id.btnKioskMode);

        btnAdminMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSelectActivity.this, AdminMenuActivity.class);
                startActivity(intent);
                Toast.makeText(ModeSelectActivity.this, "관리자 모드로 이동합니다", Toast.LENGTH_SHORT).show();
            }
        });

        btnKioskMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModeSelectActivity.this, KioskMainActivity.class);
                startActivity(intent);
                Toast.makeText(ModeSelectActivity.this, "키오스크 모드로 이동합니다", Toast.LENGTH_SHORT).show();

            }
        });


    }
}