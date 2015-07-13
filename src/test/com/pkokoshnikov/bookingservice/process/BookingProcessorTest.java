package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.model.BookingItemResponse;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;
import com.pkokoshnikov.bookingservice.util.formatters.Constants;
import org.junit.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by pavel on 12.07.2015.
 */
public class BookingProcessorTest {
    private final BookingProcessor bookingProcessor = new BookingProcessorImpl();
    private final SimpleDateFormat requestTimeSubmissionFormatter = new SimpleDateFormat(Constants.REQUEST_SUBMISSION_FORMAT);
    private final SimpleDateFormat meetingStartDateFormatter = new SimpleDateFormat(Constants.MEETING_START_FORMAT);

    @Test
    public void testProcessSimpleBatch() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:18:06"), meetingStartDateFormatter.parse("2011-03-21 11:00"),
                "EMP001", 2);
        BookingItem bookingItem3 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:19:06"), meetingStartDateFormatter.parse("2011-03-22 14:00"),
                "EMP001", 3);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2, bookingItem3});

        List<GroupByDayBookingItem> groupingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(groupingItems.size(), 2);
        assertEquals(groupingItems.get(0).getBookingItems().get(0), new BookingItemResponse(bookingItem1));
        assertEquals(groupingItems.get(0).getBookingItems().get(1), new BookingItemResponse(bookingItem2));
        assertEquals(groupingItems.get(1).getBookingItems().get(0), new BookingItemResponse(bookingItem3));
    }

    @Test
    public void testProcessBatchWithDifferentDaysStartMeetings() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-22 11:00"),
                "EMP001", 2);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2});

        List<GroupByDayBookingItem> groupingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(groupingItems.size(), 2);
        assertEquals(groupingItems.get(0).getBookingItems().get(0), new BookingItemResponse(bookingItem1));
        assertEquals(groupingItems.get(1).getBookingItems().get(0), new BookingItemResponse(bookingItem2));
    }

    @Test
    public void testCutOffIntersectedMeetings() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-16 12:34:56"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP002", 2);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2});

        List<GroupByDayBookingItem> groupingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(groupingItems.size(), 1);
        assertEquals(groupingItems.get(0).getBookingItems().get(0), new BookingItemResponse(bookingItem2));
    }

    @Test
    public void testCutOffNotHoursWorkItems() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 08:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 18:00"),
                "EMP002", 2);
        BookingItem bookingItem3 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 16:00"),
                "EMP003", 3);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2, bookingItem3});

        List<GroupByDayBookingItem> groupingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(groupingItems.size(), 0);
    }
}
