package com.leo.banking.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationManagementService notificationManagementService;

    // Kafka Listener for consuming messages and updating their status
    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void listen(String message) {
        // Save the notification when received from producer
        Long notificationId = notificationManagementService.saveNotification(message);

        // After saving, update the status to 'CONSUMED'
        notificationManagementService.updateNotificationStatus(notificationId, "CONSUMED");
    }

    // Method to simulate sending message from Kafka Producer
    public void sendNotification(String message) {
        // Send message to Kafka producer (you can inject KafkaTemplate and send the message)
        // kafkaTemplate.send("notification-topic", message);
        System.out.println("Sending notification: " + message);
    }
}
