package com.example.notification.service;

import com.example.notification.dto.CreateNotificationRequest;
import com.example.notification.dto.GuestStatusUpdateResponse;
import com.example.notification.dto.NotificationResponse;
import com.example.notification.dto.UpdateGuestStatusRequest;
import com.example.notification.entity.Notification;
import com.example.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    /** 알림 생성 */
    @Transactional
    public NotificationResponse create(CreateNotificationRequest req) {
        // 중복 방지: 동일 appointmentId + guestId의 알림이 이미 있으면 그대로 반환하거나 예외
        boolean exists = repository.existsByAppointmentIdAndGuestId(req.getAppointmentId(), req.getGuestId());
        if (exists) {
            // 이미 존재 시 그대로 찾아서 반환
            Notification already = repository.findByAppointmentIdAndGuestId(
                    req.getAppointmentId(), req.getGuestId()
            ).orElseThrow(() -> new IllegalStateException("Duplicate detected but not found."));
            return toResponse(already);
        }

        // noti1, noti2, noti3... 형식으로 ID 생성
        String id = generateSequentialId();
        LocalDateTime when = req.getNotificationTime() != null ? req.getNotificationTime() : LocalDateTime.now();

        Notification entity = new Notification(
                id,
                req.getUserId(),
                req.getAppointmentId(),
                req.getGuestId(),
                when
        );

        Notification saved = repository.save(entity);
        return toResponse(saved);
    }

    /** 전체 조회 */
    @Transactional(readOnly = true)
    public List<NotificationResponse> findAll() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    /** 단건 조회 */
    @Transactional(readOnly = true)
    public NotificationResponse findById(String notificationId) {
        Notification entity = repository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + notificationId));
        return toResponse(entity);
    }

    /** 사용자별 조회 */
    @Transactional(readOnly = true)
    public List<NotificationResponse> findByUserId(String userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * 게스트 상태 업데이트
     *
     * 주의: 현재 Notification 엔티티에는 guestStatus 필드가 없음.
     *      따라서 여기서는 존재성/일치성 검증만 하고, 응답 DTO를 생성해 돌려준다.
     *      진짜로 DB에 상태를 저장하려면 엔티티(또는 별도 테이블)에 필드를 추가해야 한다.
     */
    @Transactional
    public GuestStatusUpdateResponse updateGuestStatus(
            String notificationId,
            String guestId,
            UpdateGuestStatusRequest body
    ) {
        Notification entity = repository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found: " + notificationId));

        if (!entity.getGuestId().equals(guestId)) {
            throw new IllegalArgumentException("GuestId mismatch for notification: " + notificationId);
        }

        // 여기서 실제 저장을 하려면: 엔티티에 guestStatus(ENUM/STRING) 추가 후 set & save
        // repository.save(entity);

        // 응답 구성
        return new GuestStatusUpdateResponse(
                entity.getAppointmentId(),
                guestId,
                body.getGuestStatus()
        );
    }

    /** 엔티티 -> 응답 DTO 매핑 */
    private NotificationResponse toResponse(Notification e) {
        return new NotificationResponse(
                e.getNotificationId(),
                e.getUserId(),
                e.getAppointmentId(),
                e.getGuestId(),
                e.getNotificationTime()
        );
    }

    // 순차적 ID 생성 메서드 추가
    private String generateSequentialId() {
        long count = repository.count();
        return "noti" + (count + 1);
    }
}
