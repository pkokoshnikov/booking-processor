package com.pkokoshnikov.bookingservice.persistence.data;

import com.pkokoshnikov.bookingservice.controller.data.request.BookingItemRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User: pako
 * Date: 07.07.15
 * This class provides POJO model for booking items
 */
@Getter
@EqualsAndHashCode
@ToString
@Entity
public class BookingItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime requestSubmissionTime;
    private LocalDateTime meetingStartTime;
    private String userId;
    private Integer duration;
    private transient LocalDateTime meetingEndTime; // only for runtime

    public BookingItem(BookingItemRequest item) {
        this(item.getRequestSubmissionTime(), item.getMeetingStartTime(), item.getUserId(), item.getDuration());
    }

    public BookingItem(LocalDateTime requestSubmissionTime, LocalDateTime meetingStartTime, String userId, Integer duration) {
        this.requestSubmissionTime = requestSubmissionTime;
        this.meetingStartTime = meetingStartTime;
        this.meetingEndTime = meetingStartTime.plusHours(duration);
        this.userId = userId;
        this.duration = duration;
    }
}
