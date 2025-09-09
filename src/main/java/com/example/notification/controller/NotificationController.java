package com.example.notification.controller;

import com.example.notification.service.NotificationService;
import com.example.notification.dto.CreateNotificationRequest;
import com.example.notification.dto.GuestStatusUpdateResponse;
import com.example.notification.dto.NotificationResponse;
import com.example.notification.dto.UpdateGuestStatusRequest;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService service; // ✅ 타입 수정

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    // POST /notifications - 알림 등록
    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody CreateNotificationRequest req) {
        NotificationResponse created = service.create(req); // ✅ 메서드 존재하는 서비스로 호출
        return ResponseEntity.created(URI.create("/notifications/" + created.getNotificationId()))
                .body(created);
    }

    // GET /notifications - 알림 전체 조회
    @GetMapping
    public List<NotificationResponse> findAll() {
        return service.findAll(); // ✅ 메서드 존재하는 서비스로 호출
    }

    // GET /notifications/{notification_id} - 1개 알림 조회
    @GetMapping("/{notification_id}")
    public NotificationResponse findById(@PathVariable("notification_id") String notificationId) {
        return service.findById(notificationId); // ✅ 메서드 존재하는 서비스로 호출
    }

    // GET /notifications/user/{user_id} - 사용자 별 알림 목록 조회
    @GetMapping("/user/{user_id}")
    public List<NotificationResponse> findByUser(@PathVariable("user_id") String userId) {
        return service.findByUserId(userId); // ✅ 메서드 존재하는 서비스로 호출
    }

    // PATCH /notifications/{notification_id}/guests/{guest_id}/guest_status - 상태 변경
    @PatchMapping("/{notification_id}/guests/{guest_id}/guest_status")
    public GuestStatusUpdateResponse updateGuestStatus(@PathVariable("notification_id") String notificationId,
        @PathVariable("guest_id") String guestId,
        @Valid @RequestBody UpdateGuestStatusRequest body) {
        return service.updateGuestStatus(notificationId, guestId, body); // ✅ 메서드 존재하는 서비스로 호출
    }
}