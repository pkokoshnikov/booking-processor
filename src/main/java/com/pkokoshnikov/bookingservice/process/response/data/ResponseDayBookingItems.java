package com.pkokoshnikov.bookingservice.process.response.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.util.date.formatter.ResponseMetingDateSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: pako1113
 * Date: 13.07.15
 * This class provides model for group by day response
 */
public class ResponseDayBookingItems implements Serializable{
    private List<ResponseBookingItem> responseBookingItems = new ArrayList<>();

    @JsonSerialize(using = ResponseMetingDateSerializer.class)
    private Date dateOfMeeting;

    public void addResponseBookingItem(BookingItem bookingItem) {
        responseBookingItems.add(new ResponseBookingItem(bookingItem));
    }

    public List<ResponseBookingItem> getResponseBookingItems() {
        return responseBookingItems;
    }

    public void setResponseBookingItems(List<ResponseBookingItem> responseBookingItems) {
        this.responseBookingItems = responseBookingItems;
    }

    public Date getDateOfMeeting() {
        return dateOfMeeting;
    }

    public void setDateOfMeeting(Date dateOfMeeting) {
        this.dateOfMeeting = dateOfMeeting;
    }

    @Override
    public String toString() {
        return "ResponseDayBookingItems{" +
                "responseBookingItems=" + responseBookingItems +
                ", dateOfMeeting=" + dateOfMeeting +
                '}';
    }

}
