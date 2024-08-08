package com.example.tessentuhdigitalpart1.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.tessentuhdigitalpart1.R;
import com.google.android.material.button.MaterialButton;

public class MyService extends Service {

    private final IBinder binder = new LocalBinder();
    private Callback callback;

    public interface Callback {
        void onServiceMessage(String message);
    }

    public class LocalBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M && android.provider.Settings.canDrawOverlays(this)) {
            showFloatingButton();
        } else {
            Toast.makeText(this, "Overlay permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void showFloatingButton() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        View floatingView = LayoutInflater.from(this).inflate(R.layout.myservice_layout, null);
        Button sendMessageButton = floatingView.findViewById(R.id.btn_tap_service);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.onServiceMessage("Hello from Service!");
                }
            }
        });

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                android.graphics.PixelFormat.TRANSLUCENT);

        windowManager.addView(floatingView, params);
    }
}
