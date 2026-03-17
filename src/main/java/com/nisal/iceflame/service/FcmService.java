package com.nisal.iceflame.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public void sendNotification(String token, String title, String body) {
        try {
            Message message = Message.builder()
                    .setToken(token)
                    .putData("title", title)
                    .putData("body", body)
                    .build();

            // Use the default FirebaseApp explicitly
            String response = FirebaseMessaging.getInstance(FirebaseApp.getInstance()).send(message);
            System.out.println("FCM Sent: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}