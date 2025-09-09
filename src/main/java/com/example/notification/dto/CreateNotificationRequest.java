package com.example.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNotificationRequest {

    @JsonProperty("user_id")
    @NotBlank(message = "user_id is required")
    private String userId;

    @JsonProperty("appointment_id")
    @NotBlank(message = "appointment_id is required")
    private String appointmentId;

    @JsonProperty("guest_id")
    @NotBlank(message = "guest_id is required")
    private String guestId;

    @JsonProperty("notification_time")
    @NotNull(message = "notification_time is required")
    private LocalDateTime notificationTime;
}
