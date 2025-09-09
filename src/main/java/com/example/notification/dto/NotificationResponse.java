package com.example.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public class NotificationResponse {

    @JsonProperty("notification_id")
    private String notificationId;

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("appointment_id")
    private String appointmentId;

    @JsonProperty("guest_id")
    private String guestId;

    @JsonProperty("notification_time")
    private LocalDateTime notificationTime;

    // 기본 생성자
    public NotificationResponse() {}

    // 생성자
    public NotificationResponse(String notificationId, String userId, String appointmentId, String guestId, LocalDateTime notificationTime) {
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
