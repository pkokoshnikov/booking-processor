package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import com.pkokoshnikov.bookingservice.controller.data.response.BookingItemResponse;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;

public class GroupByDayCollectorTest {
    private static final String EMP_001 = "EMP_001";
    private static final BookingItem BOOKING_ITEM_0 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 10, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_1 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 9, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_2 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 22, 10, 0),
                                                                      EMP_001, 1);
    private static final BookingItem BOOKING_ITEM_3 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 22, 16, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_4 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 22, 13, 0),
                                                                      EMP_001, 2);

    private List<BookingItem> items = asList(BOOKING_ITEM_0, BOOKING_ITEM_1, BOOKING_ITEM_2, BOOKING_ITEM_3, BOOKING_ITEM_4);

    @Test
    public void testGroupByDateCollector() {
        Map<LocalDate, List<BookingItemResponse>> groupedMap = items.stream()
                                                                    .collect(ProcessCollectors.groupByDateCollector());

        LocalDate[] dateSet = groupedMap.keySet()
                                        .toArray(new LocalDate[0]);

        assertThat(dateSet.length, is(2));
        assertThat(groupedMap.get(dateSet[0]), contains(new BookingItemResponse(BOOKING_ITEM_1), new BookingItemResponse(BOOKING_ITEM_0)));
        assertThat(groupedMap.get(dateSet[1]), contains(new BookingItemResponse(BOOKING_ITEM_2), new BookingItemResponse(BOOKING_ITEM_4), new BookingItemResponse(BOOKING_ITEM_3)));
    }
}
