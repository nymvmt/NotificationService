package com.example.notification.repository;

import com.example.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    List<Notification> findByUserId(String userId);
    Optional<Notification> findByAppointmentIdAndGuestId(String appointmentId, String guestId);
    
    @Query("SELECT COUNT(n) > 0 FROM Notification n WHERE n.appointmentId = :appointmentId AND n.guestId = :guestId")
    boolean existsByAppointmentIdAndGuestId(@Param("appointmentId") String appointmentId, @Param("guestId") String guestId);
}