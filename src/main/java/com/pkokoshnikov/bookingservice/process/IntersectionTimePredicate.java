package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

/**
 * User: pako
 * Date: 16.03.18
 * This class provides implementation of predicate for intersection of BookingItem's
 */
@AllArgsConstructor
public class IntersectionTimePredicate implements Predicate<BookingItem> {
    private List<BookingItem> bookingItems;

    @Override
    public boolean test(BookingItem bookingItemToTest) {
        return bookingItems.stream()
                           // filter all item which was submitted later than current
                           .filter(bookingItem -> bookingItemToTest.getRequestSubmissionTime().compareTo(bookingItem.getRequestSubmissionTime()) > 0)
                           // all of the rest items mustn't intersect with bookingItemToTest
                           .noneMatch(bookingItem -> isIntersected(bookingItemToTest, bookingItem));
    }

    /**
     * @param item1 to find intersection
     * @param item2 to find intersection
     * @return true if we have intersection and false otherwise
     */
    protected boolean isIntersected(BookingItem item1, BookingItem item2) {
        LocalDateTime item1StartTime = item1.getMeetingStartTime();
        LocalDateTime item1EndTime = item1.getMeetingEndTime();

        LocalDateTime item2StartTime = item2.getMeetingStartTime();
        LocalDateTime item2EndTime = item2.getMeetingEndTime();

        if (item1EndTime.isEqual(item2StartTime) || item2EndTime.isEqual(item1StartTime)) {
            return false;
        }

        return item1StartTime.compareTo(item2StartTime) <= 0 && item1EndTime.compareTo(item2StartTime) >= 0 ||
                item1StartTime.compareTo(item2EndTime) <= 0 && item1EndTime.compareTo(item2EndTime) >= 0 ||
                item1StartTime.compareTo(item2StartTime) >= 0 && item1EndTime.compareTo(item2EndTime) <= 0;
    }
}
