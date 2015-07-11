package com.pkokoshnikov.bookingservice;

import com.fasterxml.jackson.annotation.JsonView;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;
import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * User: pako1113
 * Date: 07.07.15
 */
@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Service
public class BookingResource {

    final static Logger logger = Logger.getLogger(BookingResource.class);

    private final BookingProcessor bookingProcessor;

    @Inject
    public BookingResource(BookingProcessor bookingProcessor) {
        this.bookingProcessor = bookingProcessor;
    }

    @POST
    @Path("/process")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(BookingItem.Views.Public.class)
    public List<GroupByDayBookingItem> doProcessOfBatchBookingRequest(BookingBatch bookingBatch) {
        logger.info("doProcessOfBatchBookingRequest");
        for(BookingItem bookingItem : bookingBatch.getBookingItems()) {
            logger.info(bookingItem.toString());
        }
        List<GroupByDayBookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);
        for(GroupByDayBookingItem bookingItem : bookingItems) {
            logger.info(bookingItem.toString());
        }

        return bookingItems;
    }


    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatusService() {
        logger.info("getStatusService");
        return "OK";
    }

}
