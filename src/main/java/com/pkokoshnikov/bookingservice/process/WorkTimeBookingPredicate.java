package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * User: pako
 * Date: 16.03.18
 * This class provides implementation of predicate for work time of BookingItem
 */
@Slf4j
@AllArgsConstructor
public class WorkTimeBookingPredicate implements Predicate<BookingItem> {
    private LocalTime startWorkTime;
    private LocalTime endWorkTime;

    /**
     * This method detects that meeting is booked in work hours
     *
     * @return true if meeting is booked in work hours
     */
    private boolean isWorkingHoursMeeting(BookingItem bookingItem, LocalTime workingStartTime,
                                          LocalTime workingEndTime) {
        LocalDateTime meetingStartTime = bookingItem.getMeetingStartTime();
        LocalDateTime meetingEndTime = bookingItem.getMeetingEndTime();

        if (LocalTime.from(meetingStartTime)
                     .compareTo(workingStartTime) < 0
                || LocalTime.from(meetingEndTime)
                            .compareTo(workingEndTime) > 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean test(BookingItem bookingItem) {
        return isWorkingHoursMeeting(bookingItem, startWorkTime, endWorkTime);
    }
}
