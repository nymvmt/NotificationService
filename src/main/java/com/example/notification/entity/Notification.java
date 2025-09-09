package com.example.notification.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @Column(name = "notification_id", length = 100, nullable = false)
    private String notificationId;

    @Column(name = "user_id", length = 100, nullable = false)
    private String userId;

    @Column(name = "appointment_id", length = 100, nullable = false)
    private String appointmentId;

    @Column(name = "guest_id", length = 100, nullable = false)
    private String guestId;

    @Column(name = "notification_time", nullable = false)
    private LocalDateTime notificationTime;

    public Notification() {}

    public Notification(String notificationId, String userId, String appointmentId, String guestId, LocalDateTime notificationTime) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.appointmentId = appointmentId;
        this.guestId = guestId;
        this.notificationTime = notificationTime;
    }

    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public String getGuestId() { return guestId; }
    public void setGuestId(String guestId) { this.guestId = guestId; }
    public LocalDateTime getNotificationTime() { return notificationTime; }
    public void setNotificationTime(LocalDateTime notificationTime) { this.notificationTime = notificationTime; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification that)) return false;
        return Objects.equals(notificationId, that.notificationId);
    }
    @Override
    public int hashCode() { return Objects.hash(notificationId); }
}