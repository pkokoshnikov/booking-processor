package com.pkokoshnikov.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pkokoshnikov.bookingservice.util.Constants;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@Entity
public class BookingItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = Constants.REQUEST_SUBMISSION_FORMAT)
    private LocalDateTime requestSubmissionTime;

    @JsonFormat(pattern = Constants.MEETING_START_FORMAT)
    private LocalDateTime meetingStartTime;

    private String userId;
    private Integer duration;

    public BookingItem(LocalDateTime requestSubmissionTime, LocalDateTime meetingStartTime, String userId,
                       Integer duration) {
        this.requestSubmissionTime = requestSubmissionTime;
        this.meetingStartTime = meetingStartTime;
        this.userId = userId;
        this.duration = duration;
    }

    public LocalDateTime getMeetingEndTime() {
        return meetingStartTime.plusHours(duration);
    }

    @Override
    public String toString() {
        return "BookingItem{" +
                "requestSubmissionTime=" + requestSubmissionTime +
                ", meetingStartTime=" + meetingStartTime +
                ", userId='" + userId + '\'' +
                ", duration=" + duration +
                '}';
    }
}
