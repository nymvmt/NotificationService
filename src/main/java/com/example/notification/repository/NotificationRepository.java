package com.example.notification.repository;

import com.example.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    // 사용자별 알림 목록 조회
    List<Notification> findByUserIdOrderByNotificationTimeDesc(String userId);

    // 특정 알림 조회
    Optional<Notification> findByNotificationId(String notificationId);

    // 같은 Appointment 내 같은 Guest에 대한 중복 Notification 확인 (INV-N004)
    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.appointmentId = :appointmentId AND n.guestId = :guestId")
    boolean existsByAppointmentIdAndGuestId(@Param("appointmentId") String appointmentId, @Param("guestId") String guestId);

    // 특정 Appointment의 모든 알림 조회
    List<Notification> findByAppointmentId(String appointmentId);

    // 특정 Guest의 모든 알림 조회
    List<Notification> findByGuestId(String guestId);
}
