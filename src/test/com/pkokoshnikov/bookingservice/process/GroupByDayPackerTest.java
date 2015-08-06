package com.pkokoshnikov.bookingservice.process;

import com.google.common.collect.Lists;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.process.response.GroupByDayPacker;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseBookingItem;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseDayBookingItems;
import com.pkokoshnikov.bookingservice.process.response.ResponsePacker;
import com.pkokoshnikov.bookingservice.util.date.formatter.DateConstants;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: pako1113
 * Date: 27.07.15
 */
public class GroupByDayPackerTest {
    private ResponsePacker responsePacker = new GroupByDayPacker();
    private final SimpleDateFormat requestTimeSubmissionFormatter = new SimpleDateFormat(DateConstants.REQUEST_SUBMISSION_FORMAT);
    private final SimpleDateFormat meetingStartDateFormatter = new SimpleDateFormat(DateConstants.MEETING_START_FORMAT);

    @Test
    public void packEmptyTest() {
        List<ResponseDayBookingItems> responseDayBookingItems = responsePacker.packResponse(new ArrayList<BookingItem>());
        assertEquals(responseDayBookingItems.size(), 0);
    }

    @Test
    public void packTest() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:18:06"), meetingStartDateFormatter.parse("2011-03-21 11:00"),
                "EMP001", 2);
        BookingItem bookingItem3 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:19:06"), meetingStartDateFormatter.parse("2011-03-22 14:00"),
                "EMP001", 3);
        BookingItem bookingItem4 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:19:06"), meetingStartDateFormatter.parse("2011-03-23 14:00"),
                "EMP001", 3);
        BookingItem bookingItem5 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:19:06"), meetingStartDateFormatter.parse("2011-03-23 16:00"),
                "EMP001", 3);

        List<ResponseDayBookingItems> responseDayBookingItems = responsePacker.packResponse(Lists.newArrayList(bookingItem1, bookingItem2, bookingItem3, bookingItem4, bookingItem5));

        assertEquals(responseDayBookingItems.size(), 3);
        assertEquals(responseDayBookingItems.get(0).getResponseBookingItems().get(0), new ResponseBookingItem(bookingItem1));
        assertEquals(responseDayBookingItems.get(0).getResponseBookingItems().get(1), new ResponseBookingItem(bookingItem2));
        assertEquals(responseDayBookingItems.get(1).getResponseBookingItems().get(0), new ResponseBookingItem(bookingItem3));
        assertEquals(responseDayBookingItems.get(2).getResponseBookingItems().get(0), new ResponseBookingItem(bookingItem4));
        assertEquals(responseDayBookingItems.get(2).getResponseBookingItems().get(1), new ResponseBookingItem(bookingItem5));
    }
}
