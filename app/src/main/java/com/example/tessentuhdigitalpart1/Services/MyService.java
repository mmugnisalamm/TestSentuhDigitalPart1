package com.example.tessentuhdigitalpart1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tessentuhdigitalpart1.R;
import com.google.android.material.button.MaterialButton;

public class MyService extends Service {

    public interface Callback {
        void onStringReceived(String data);
    }

    private static Callback callback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.myservice_layout, null);

        MaterialButton btnServcie = view.findViewById(R.id.btn_tap_service);
        btnServcie.setOnClickListener(v -> {
            if (callback != null) {
                callback.onStringReceived("This is message from service. \nHello, my name is Muhammad Mugnisalam Magfira!");
            }
        });

        return START_NOT_STICKY;
    }

    public static void setCallback(Callback callback) {
        MyService.callback = callback;
    }
}
