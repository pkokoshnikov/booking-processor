package com.pkokoshnikov.bookingservice.persistence;

import com.pkokoshnikov.bookingservice.persistence.data.ApprovedBooking;
import com.pkokoshnikov.bookingservice.service.PersistenceService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: pako
 * Date: 16.03.18
 */
@Service
public class JpaPersistenceService implements PersistenceService {
    private final ApprovedBookingRepository approvedBookingRepository;

    @Autowired
    public JpaPersistenceService(ApprovedBookingRepository approvedBookingRepository) {this.approvedBookingRepository = approvedBookingRepository;}

    @Override
    public @NonNull
    ApprovedBooking getLastApprovedBooking() {
        return approvedBookingRepository.findTopByOrderByCreateDateTimeDesc();
    }

    @Override
    public @NonNull Long storeApprovedBookings(@NonNull ApprovedBooking approvedBooking) {
        return approvedBookingRepository.save(approvedBooking)
                                        .getId();
    }

    @Override
    public @NonNull
    ApprovedBooking getApprovedBooking(@NonNull Long id) {
        return approvedBookingRepository.getOne(id);
    }
}
