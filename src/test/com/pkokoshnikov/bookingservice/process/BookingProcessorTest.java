package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;
import com.pkokoshnikov.bookingservice.process.types.ProcessorType;
import com.pkokoshnikov.bookingservice.util.formatters.Constants;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by pavel on 12.07.2015.
 */
public class BookingProcessorTest {
    private final BookingProcessor bookingProcessor = new BookingProcessorImpl();
    private final SimpleDateFormat requestTimeSubmissionFormatter = new SimpleDateFormat(Constants.REQUEST_SUBMISSION_FORMAT);
    private final SimpleDateFormat meetingStartDateFormatter = new SimpleDateFormat(Constants.MEETING_START_FORMAT);

    @Test
    public void testProcessBatchWithOneItem() throws ParseException {
        BookingBatch bookingBatch = new BookingBatch();
        bookingBatch.setStartOfWorkDay("0900");
        bookingBatch.setEndOfWorkDay("1700");
        BookingItem bookingItem = new BookingItem();
        bookingItem.setUserId("EMP001");
        bookingItem.setDuration(2);
        bookingItem.setMeetingStartTime(meetingStartDateFormatter.parse("2011-03-21 09:00"));
        bookingItem.setRequestSubmissionTime(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"));
        bookingBatch.setBookingItems(new BookingItem[]{bookingItem});
        List<GroupByDayBookingItem> groupingItems = bookingProcessor.processBatch(bookingBatch);

        assertTrue(groupingItems.size() == 1);
        assertEquals(groupingItems.get(0).getBookingItems().get(0), bookingItem);

    }
}
