package com.example.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateGuestStatusRequest {

    @JsonProperty("guest_status")
    @NotBlank(message = "guest_status is required")
    @Pattern(regexp = "coming|came|no_show", message = "guest_status must be one of: coming, came, no_show")
    private String guestStatus;

    // 기본 생성자
    public UpdateGuestStatusRequest() {}

    // 생성자
    public UpdateGuestStatusRequest(String guestStatus) {
        this.guestStatus = guestStatus;
    }

    // Getter/Setter
    public String getGuestStatus() {
        return guestStatus;
    }

    public void setGuestStatus(String guestStatus) {
        this.guestStatus = guestStatus;
    }
}
