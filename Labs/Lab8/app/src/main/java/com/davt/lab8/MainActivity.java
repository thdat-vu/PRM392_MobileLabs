package com.davt.lab8;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "lab8_notification_channel";
    private Button btnSendNotification;
    private Button btnSendCustomNotification;
    private Button btnClearAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create notification channel for Android 8.0 and higher
        createNotificationChannel();

        // Initialize buttons
        btnSendNotification = findViewById(R.id.btnSendNotification);
        btnSendCustomNotification = findViewById(R.id.btnSendCustomNotification);
        btnClearAll = findViewById(R.id.btnClearAll);

        // Set click listeners
        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(false);
            }
        });

        btnSendCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(true);
            }
        });

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllNotifications();
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ (Android 8.0) because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Lab8 Notifications";
            String description = "Channel for Lab8 notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            
            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void sendNotification(boolean useCustomIcon) {
        // Get bitmap for large icon
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        
        // Create notification builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Title push notification")
                .setContentText("Message push notification")
                .setLargeIcon(bitmap);
        
        // Set small icon based on parameter
        if (useCustomIcon) {
            builder.setSmallIcon(R.drawable.ic_notification_custom);
        } else {
            builder.setSmallIcon(R.drawable.ic_notification_bell);
        }
        
        // Add color accent
        builder.setColor(getResources().getColor(android.R.color.holo_purple));
        
        // Build the notification
        builder.build();
        
        // Get notification manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        
        // Check if notification manager is available
        if (notificationManager != null) {
            // Show the notification with unique ID
            notificationManager.notify(getNotificationId(), builder.build());
        }
    }

    private void clearAllNotifications() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancelAll();
    }

    private int getNotificationId() {
        // Generate a unique ID based on the current time
        return (int) new Date().getTime();
    }
}