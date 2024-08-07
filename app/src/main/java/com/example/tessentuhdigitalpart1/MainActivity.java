package com.example.tessentuhdigitalpart1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tessentuhdigitalpart1.Services.MyService;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements MyService.Callback {

    MaterialButton btnMain;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain = findViewById(R.id.btn_tap_me);
        tvResult = findViewById(R.id.tv_result);

        btnMain.setOnClickListener(view -> {
            Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
            startService(serviceIntent);
        });

        MyService.setCallback(this);
    }

    @Override
    public void onStringReceived(String data) {
        tvResult.setText(data);
    }
}