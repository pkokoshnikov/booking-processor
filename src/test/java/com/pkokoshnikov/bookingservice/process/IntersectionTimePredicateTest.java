package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.not;

public class IntersectionTimePredicateTest {
    private static final String EMP_001 = "EMP_001";

    private static final BookingItem BOOKING_ITEM_0 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 0, 6), LocalDateTime.of(2011, 3, 21, 8, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_1 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 30, 6), LocalDateTime.of(2011, 3, 21, 10, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_2 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 10, 6), LocalDateTime.of(2011, 3, 21, 12, 0),
                                                                      EMP_001, 3);
    private static final BookingItem BOOKING_ITEM_3 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 20, 6), LocalDateTime.of(2011, 3, 21, 10, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_4 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 40, 6), LocalDateTime.of(2011, 3, 21, 11, 0),
                                                                      EMP_001, 1);

    public static final String BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE = "Booking item must be tested as true";
    public static final String BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE = "Booking item must be tested as false";

    private IntersectionTimePredicate intersectionTimePredicate = new IntersectionTimePredicate(asList(BOOKING_ITEM_0, BOOKING_ITEM_1, BOOKING_ITEM_2, BOOKING_ITEM_3, BOOKING_ITEM_4));

    @Test
    public void testIntersection() {
        assertThat("Mustn't intersect", not(intersectionTimePredicate.isIntersected(BOOKING_ITEM_1, BOOKING_ITEM_2)));
    }

    @Test
    public void testIsNotIntersected() {
        assertThat("Must intersect", intersectionTimePredicate.isIntersected(BOOKING_ITEM_1, BOOKING_ITEM_3));
    }

    @Test
    public void testFilterAllowableValues() {
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE, intersectionTimePredicate.test(BOOKING_ITEM_0));
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE, intersectionTimePredicate.test(BOOKING_ITEM_2));
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_TRUE, intersectionTimePredicate.test(BOOKING_ITEM_3));
    }

    @Test
    public void testFilterStartMeetingTooEarly() {
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE, not(intersectionTimePredicate.test(BOOKING_ITEM_1)));
        assertThat(BOOKING_ITEM_MUST_BE_TESTED_AS_FALSE, not(intersectionTimePredicate.test(BOOKING_ITEM_4)));
    }

    @Test
    public void testPredicateWorkWithStream() {
        List<BookingItem> filteredValues = Stream.of(BOOKING_ITEM_0, BOOKING_ITEM_1, BOOKING_ITEM_2, BOOKING_ITEM_3, BOOKING_ITEM_4)
                                                 .filter(intersectionTimePredicate)
                                                 .collect(Collectors.toList());

        assertThat(filteredValues, containsInAnyOrder(BOOKING_ITEM_0, BOOKING_ITEM_2, BOOKING_ITEM_3));
    }
}
