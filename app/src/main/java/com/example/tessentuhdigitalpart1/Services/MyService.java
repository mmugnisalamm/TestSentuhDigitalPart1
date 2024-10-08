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

import com.example.tessentuhdigitalpart1.MainActivity;
import com.example.tessentuhdigitalpart1.R;
import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class MyService extends Service {
    private View floatingView;
    private WindowManager windowManager;
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
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        floatingView = LayoutInflater.from(this).inflate(R.layout.myservice_layout, null);
        Button sendMessageButton = floatingView.findViewById(R.id.btn_tap_service);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    Random random = new Random();
                    int randomNumber = random.nextInt(9999);
                    callback.onServiceMessage("Random number from Service: "+randomNumber+"");
                }
////                // Menghapus floating view sebelum menghentikan service
                if (floatingView != null && windowManager != null) {
                    windowManager.removeView(floatingView);
                }
//
//                // Menghentikan service
//                stopSelf();

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingView != null && windowManager != null) {
            windowManager.removeView(floatingView);
        }
    }
}
