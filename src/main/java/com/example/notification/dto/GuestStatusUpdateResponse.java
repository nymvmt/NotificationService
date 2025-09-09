package com.example.notification.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuestStatusUpdateResponse {

    @JsonProperty("appointment_id")
    private String appointmentId;

    @JsonProperty("guest_id")
    private String guestId;

    @JsonProperty("guest_status")
    private String guestStatus;

    public GuestStatusUpdateResponse() {
    }

    public GuestStatusUpdateResponse(String appointmentId, String guestId, String guestStatus) {
        this.appointmentId = appointmentId;
        this.guestId = guestId;
        this.guestStatus = guestStatus;
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

    public String getGuestStatus() {
        return guestStatus;
    }

    public void setGuestStatus(String guestStatus) {
        this.guestStatus = guestStatus;
    }
}
