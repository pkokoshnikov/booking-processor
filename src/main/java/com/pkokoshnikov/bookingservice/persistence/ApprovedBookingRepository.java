package com.pkokoshnikov.bookingservice.persistence;

import com.pkokoshnikov.bookingservice.model.ApprovedBooking;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * User: pako
 * Date: 16.03.18
 * This class provides repository for ApprovedBooking
 */
public interface ApprovedBookingRepository extends JpaRepository<ApprovedBooking, Long> {
    ApprovedBooking findTopByOrderByCreateDateTimeDesc();
}
