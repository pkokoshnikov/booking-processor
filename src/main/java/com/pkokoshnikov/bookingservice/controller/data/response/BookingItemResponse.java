package com.pkokoshnikov.bookingservice.controller.data.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import lombok.*;

import java.time.LocalTime;

import static com.pkokoshnikov.bookingservice.util.Constants.RESPONSE_MEETING_START_END_TIME_FORMAT;

/**
 * User: pako
 * Date: 13.07.15
 * This class provides model for response of booking item
 */
@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookingItemResponse {
    @JsonFormat(pattern = RESPONSE_MEETING_START_END_TIME_FORMAT)
    private LocalTime meetingEndTime;
    @JsonFormat(pattern = RESPONSE_MEETING_START_END_TIME_FORMAT)
    private LocalTime meetingStartTime;
    private String userId;

    public BookingItemResponse(BookingItem bookingItem) {
        meetingStartTime = bookingItem.getMeetingStartTime().toLocalTime();
        meetingEndTime = bookingItem.getMeetingEndTime().toLocalTime();
        userId = bookingItem.getUserId();
    }
}
