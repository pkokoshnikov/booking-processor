package com.pkokoshnikov.bookingservice.persistence.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User: pako
 * Date: 16.03.18
 * This class provides POJO model for output model of approved booking
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ApprovedBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_booking_id")
    private List<BookingItem> bookingItems = new ArrayList<>();

    public ApprovedBooking(List<BookingItem> bookingItems) {
        this.bookingItems.addAll(bookingItems);
    }
}
