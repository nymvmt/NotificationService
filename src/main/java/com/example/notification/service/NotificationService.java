package com.example.notification.service;

import com.example.notification.dto.CreateNotificationRequest;
import com.example.notification.dto.NotificationResponse;
import com.example.notification.dto.UpdateGuestStatusRequest;
import com.example.notification.entity.Notification;
import com.example.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // 알림 생성
    public NotificationResponse createNotification(CreateNotificationRequest request) {
        // INV-N002: notification_time은 현재 시간보다 미래여야 함
        if (request.getNotificationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("notification_time must be in the future");
        }

        // INV-N004: 같은 Appointment 내 같은 Guest에 중복 Notification 생성 불가
        if (notificationRepository.existsByAppointmentIdAndGuestId(request.getAppointmentId(), request.getGuestId())) {
            throw new IllegalArgumentException("Duplicate notification for the same appointment and guest");
        }

        // TODO: INV-N003: appointment_status == 'cancelled' 인 경우 체크 (외부 서비스 연동 필요)
        // TODO: INV-N001: guest_status == 'noshow'인 guest에 대한 알림 발송 제한 (외부 서비스 연동 필요)

        String notificationId = UUID.randomUUID().toString();
        Notification notification = new Notification(
                notificationId,
                request.getUserId(),
                request.getAppointmentId(),
                request.getGuestId(),
                request.getNotificationTime()
        );

        Notification savedNotification = notificationRepository.save(notification);
        return convertToResponse(savedNotification);
    }

    // 알림 전체 조회
    @Transactional(readOnly = true)
    public List<NotificationResponse> getAllNotifications() {
        List<Notification> notifications = notificationRepository.findAll();
        return notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 특정 알림 조회
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(String notificationId) {
        Optional<Notification> notification = notificationRepository.findByNotificationId(notificationId);
        if (notification.isEmpty()) {
            throw new IllegalArgumentException("Notification not found with id: " + notificationId);
        }
        return convertToResponse(notification.get());
    }

    // 사용자별 알림 목록 조회
    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByUserId(String userId) {
        List<Notification> notifications = notificationRepository.findByUserIdOrderByNotificationTimeDesc(userId);
        return notifications.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // 게스트 상태 변경 (Guest Service 연동 필요)
    public void updateGuestStatus(String notificationId, String guestId, UpdateGuestStatusRequest request) {
        // 알림 존재 확인
        Optional<Notification> notificationOpt = notificationRepository.findByNotificationId(notificationId);
        if (notificationOpt.isEmpty()) {
            throw new IllegalArgumentException("Notification not found with id: " + notificationId);
        }

        Notification notification = notificationOpt.get();
        if (!notification.getGuestId().equals(guestId)) {
            throw new IllegalArgumentException("Guest ID does not match the notification");
        }

        // TODO: Guest Service의 PATCH /appointments/{appointment_id}/guests/{guest_id}/guest_status 호출
        // 현재는 로컬에서만 동작하도록 구현
        System.out.println("Updating guest status for notification: " + notificationId + 
                          ", guest: " + guestId + 
                          ", status: " + request.getGuestStatus());
    }

    // Entity를 Response DTO로 변환
    private NotificationResponse convertToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getUserId(),
                notification.getAppointmentId(),
                notification.getGuestId(),
                notification.getNotificationTime()
        );
    }
}
