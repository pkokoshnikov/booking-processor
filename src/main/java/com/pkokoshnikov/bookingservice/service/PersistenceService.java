package com.pkokoshnikov.bookingservice.service;

import com.pkokoshnikov.bookingservice.model.ApprovedBooking;
import lombok.NonNull;

/**
 * User: pako
 * Date: 16.03.18
 * This service provides persistence store for booking history
 */
public interface PersistenceService {
    @NonNull ApprovedBooking getLastApprovedBooking();
    @NonNull Long storeApprovedBookings(@NonNull ApprovedBooking approvedBooking);
    @NonNull ApprovedBooking getApprovedBooking(@NonNull Long id);
}
