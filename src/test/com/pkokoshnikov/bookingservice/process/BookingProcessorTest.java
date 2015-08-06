package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.dao.PropertiesDAOImpl;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;

import com.pkokoshnikov.bookingservice.model.Property;
import com.pkokoshnikov.bookingservice.util.date.formatter.DateConstants;
import org.junit.*;
import org.mockito.Mockito;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by pavel on 12.07.2015.
 */

public class BookingProcessorTest {
    private BookingProcessor bookingProcessor;

    private final SimpleDateFormat requestTimeSubmissionFormatter = new SimpleDateFormat(DateConstants.REQUEST_SUBMISSION_FORMAT);
    private final SimpleDateFormat meetingStartDateFormatter = new SimpleDateFormat(DateConstants.MEETING_START_FORMAT);

    @Before
    public void init() {
        PropertiesDAOImpl propertiesDAO = Mockito.mock(PropertiesDAOImpl.class);
        when(propertiesDAO.findPropertyByName("startWorkTime")).thenReturn(new Property("startWorkTime", "0900"));
        when(propertiesDAO.findPropertyByName("endWorkTime")).thenReturn(new Property("endWorkTime", "1730"));
        bookingProcessor = new BookingProcessorImpl(propertiesDAO, );
    }

    @Test
    public void testProcessSimpleBatch() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:18:06"), meetingStartDateFormatter.parse("2011-03-21 11:00"),
                "EMP001", 2);
        BookingItem bookingItem3 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:19:06"), meetingStartDateFormatter.parse("2011-03-22 14:00"),
                "EMP001", 3);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2, bookingItem3});

        List<BookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(bookingItems.size(), 3);
        assertEquals(bookingItems.get(0), bookingItem1);
        assertEquals(bookingItems.get(1), bookingItem2);
        assertEquals(bookingItems.get(2), bookingItem3);
    }

    @Test
    public void testProcessBatchWithDifferentDaysStartMeetings() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-22 11:00"),
                "EMP001", 2);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2});

        List<BookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(bookingItems.size(), 2);
        assertEquals(bookingItems.get(0), bookingItem1);
        assertEquals(bookingItems.get(1), bookingItem2);
    }

    @Test
    public void testCutOffIntersectedMeetings() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-16 12:34:56"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP002", 2);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2});

        List<BookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(bookingItems.size(), 1);
        assertEquals(bookingItems.get(0), bookingItem2);
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

        List<BookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);

        assertEquals(bookingItems.size(), 0);
    }
}
