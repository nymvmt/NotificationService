package com.example.notification.controller;

import com.example.notification.dto.CreateNotificationRequest;
import com.example.notification.dto.NotificationResponse;
import com.example.notification.dto.UpdateGuestStatusRequest;
import com.example.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // POST /notifications - 알림 등록
    @PostMapping
    public ResponseEntity<NotificationResponse> createNotification(@Valid @RequestBody CreateNotificationRequest request) {
        try {
            NotificationResponse response = notificationService.createNotification(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /notifications - 알림 전체 조회
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAllNotifications() {
        try {
            List<NotificationResponse> notifications = notificationService.getAllNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /notifications/{notification_id} - 1개 알림 조회
    @GetMapping("/{notification_id}")
    public ResponseEntity<NotificationResponse> getNotificationById(@PathVariable("notification_id") String notificationId) {
        try {
            NotificationResponse notification = notificationService.getNotificationById(notificationId);
            return ResponseEntity.ok(notification);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // GET /notifications/user/{user_id} - 사용자 별 알림 목록 조회
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<NotificationResponse>> getNotificationsByUserId(@PathVariable("user_id") String userId) {
        try {
            List<NotificationResponse> notifications = notificationService.getNotificationsByUserId(userId);
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // PATCH /notifications/{notification_id}/guests/{guest_id}/guest_status - 상태 변경
    @PatchMapping("/{notification_id}/guests/{guest_id}/guest_status")
    public ResponseEntity<Void> updateGuestStatus(
            @PathVariable("notification_id") String notificationId,
            @PathVariable("guest_id") String guestId,
            @Valid @RequestBody UpdateGuestStatusRequest request) {
        try {
            notificationService.updateGuestStatus(notificationId, guestId, request);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
