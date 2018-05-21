package com.pkokoshnikov.bookingservice.controller.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pkokoshnikov.bookingservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookingItemRequest {
    @JsonFormat(pattern = Constants.REQUEST_SUBMISSION_FORMAT)
    private LocalDateTime requestSubmissionTime;
    @JsonFormat(pattern = Constants.MEETING_START_FORMAT)
    private LocalDateTime meetingStartTime;
    private String userId;
    private Integer duration;
}
