package com.leo.banking.notification;

import com.leo.banking.notification.Notification;
import com.leo.banking.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationManagementService {

    private final NotificationRepository notificationRepository;

    // Save message to the database when received from Kafka Producer
    @Transactional
    public Long saveNotification(String message) {
        Notification notification = new Notification(message);
        notificationRepository.save(notification);
        System.out.println("Notification saved with message: " + message);
        return notification.getId(); // Return the ID of the saved notification
    }

    // Update the status of the notification when consumed
    @Transactional
    public void updateNotificationStatus(Long notificationId, String status) {
        Optional<Notification> notificationOptional = notificationRepository.findById(notificationId);

        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setStatus(status);
            notification.setUpdatedAt(java.time.LocalDateTime.now());
            notificationRepository.save(notification);
            System.out.println("Notification status updated to: " + status);
        } else {
            System.out.println("Notification not found with ID: " + notificationId);
        }
    }
}
