package com.example.todolist_project_group;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationService extends IntentService {

    private static final String CHANNEL_ID = "notification_channel";
    private static final int NOTIFICATION_ID = 1;

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // ข้อความที่จะใช้แสดงใน Notification
        String title = intent.getStringExtra("Title");
        String message = intent.getStringExtra("message");

        showNotification(title,message);
    }

    private void showNotification(String title,String message) {
        createNotificationChannel();

        // Intent สำหรับเปิด Activity เมื่อแตะ Notification
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // สร้าง Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher) // ไอคอนของ Notification
                .setContentTitle(title) // หัวข้อ
                .setContentText(message) // ข้อความ
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true) // ปิด Notification อัตโนมัติเมื่อแตะ
                .setContentIntent(pendingIntent); // Intent เมื่อแตะ

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }

        notificationManager.notify(NOTIFICATION_ID, builder.build());

        KeyguardManager keyguardManager = (KeyguardManager)getApplicationContext().getSystemService(Context.KEYGUARD_SERVICE);

        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("Scheduler");

        keyguardLock.disableKeyguard();

        PowerManager pm = (PowerManager)
                getApplicationContext().getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag")
        PowerManager.WakeLock wakeLock = pm.newWakeLock(
                (PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                        PowerManager.FULL_WAKE_LOCK |
                        PowerManager.ACQUIRE_CAUSES_WAKEUP), "Scheduler");
        wakeLock.acquire(10*60*1000L /*10 minutes*/);

    }

    // สร้าง Notification ที่ใช้ใน startForeground()
    private Notification createNotification(String message) {
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Reminder")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
    }

    private void createNotificationChannel(){
        // สร้าง Notification Manager
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        // สร้าง Notification Channel (สำหรับ Android 8.0 ขึ้นไป)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notification Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }
    }
}
