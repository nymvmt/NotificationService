package com.example.notification.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @Column(name = "notification_id", length = 100)
    private String notificationId;

    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Column(name = "appointment_id", length = 100, nullable = false)
    private String appointmentId;

    @Column(name = "guest_id", length = 100, nullable = false)
    private String guestId;

    @Column(name = "notification_time", nullable = false)
    private LocalDateTime notificationTime;

    // 기본 생성자
    public Notification() {}

    // 생성자
    public Notification(String notificationId, String userId, String appointmentId, String guestId, LocalDateTime notificationTime) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.guestId = guestId;
        this.notificationTime = notificationTime;
    }

    // Getter/Setter
    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public LocalDateTime getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }
}
