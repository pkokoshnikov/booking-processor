package com.pkokoshnikov.bookingservice.process.response;

import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseDayBookingItems;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: pako1113
 * Date: 27.07.15
 * This class provides functionality for packaging of booking items by day for response
 */
public class GroupByDayPacker implements ResponsePacker {

    //result of packaging
    private List<ResponseDayBookingItems> dayBookingItemsList = new ArrayList<>();
    //current day for algorithm
    private Date curDay;
    //current set of items for current day
    private ResponseDayBookingItems curDayBookingItems;

    /**
     * main method of Packer
     * @param bookingItems items for packaging
     * @return list grouping meetings
     */
    @Override
    public List<ResponseDayBookingItems> packResponse(List<BookingItem> bookingItems) {
        if (bookingItems.isEmpty()) return dayBookingItemsList;

        initNewDay(bookingItems.get(0));

        for (BookingItem bookingItem : bookingItems) {
            if (DateUtils.isSameDay(curDay, bookingItem.getMeetingStartTime())) {
                addBookingItem(bookingItem);
            } else {
                nextDay(bookingItem);
            }
        }

        closeCurrentDay();

        return dayBookingItemsList;
    }

    private void nextDay(BookingItem bookingItem) {
        closeCurrentDay();

        initNewDay(bookingItem);
        addBookingItem(bookingItem);
    }

    private void addBookingItem(BookingItem bookingItem) {
        curDayBookingItems.addResponseBookingItem(bookingItem);
    }

    private void closeCurrentDay() {
        dayBookingItemsList.add(curDayBookingItems);
    }

    private void initNewDay(BookingItem bookingItem) {
        curDay = bookingItem.getMeetingStartTime();
        curDayBookingItems = new ResponseDayBookingItems();
        curDayBookingItems.setDateOfMeeting(curDay);
    }
}
