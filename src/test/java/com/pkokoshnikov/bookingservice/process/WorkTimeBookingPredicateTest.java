package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;

/**
 * Created by pavel on 12.07.2015.
 */
public class WorkTimeBookingPredicateTest {
    private static final String EMP_001 = "EMP_001";

    private static final BookingItem BOOKING_ITEM_0 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 8, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_1 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 9, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_2 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 16, 0),
                                                                      EMP_001, 1);
    private static final BookingItem BOOKING_ITEM_3 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 16, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_4 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 19, 0),
                                                                      EMP_001, 2);

    public static final String BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE = "Booking item must be tested as true";
    public static final String BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE = "Booking item must be tested as false";

    private WorkTimeBookingPredicate workTimeBookingPredicate = new WorkTimeBookingPredicate(LocalTime.of(9, 0), LocalTime.of(17, 0));

    @Test
    public void testAllowableValues() {
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE, workTimeBookingPredicate.test(BOOKING_ITEM_1));
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE, workTimeBookingPredicate.test(BOOKING_ITEM_2));
    }

    @Test
    public void testStartMeetingTooEarly() {
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE, not(workTimeBookingPredicate.test(BOOKING_ITEM_2)));
    }

    @Test
    public void testStartMeetingTooLate() {
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE, not(workTimeBookingPredicate.test(BOOKING_ITEM_4)));
    }

    @Test
    public void testEndMeetingTooLate() {
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE, not(workTimeBookingPredicate.test(BOOKING_ITEM_3)));
    }

    @Test
    public void testPredicateWorkWithStream() {
        List<BookingItem> filteredValues = Stream.of(BOOKING_ITEM_0, BOOKING_ITEM_1, BOOKING_ITEM_2, BOOKING_ITEM_3, BOOKING_ITEM_4)
                                                 .filter(workTimeBookingPredicate)
                                                 .collect(Collectors.toList());

        assertThat(filteredValues, containsInAnyOrder(BOOKING_ITEM_1, BOOKING_ITEM_2));
    }
}
