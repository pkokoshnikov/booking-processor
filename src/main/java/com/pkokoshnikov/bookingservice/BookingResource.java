package com.pkokoshnikov.bookingservice;

import com.fasterxml.jackson.annotation.JsonView;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;
import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.BookingProcessorFactory;
import com.pkokoshnikov.bookingservice.process.ProcessorType;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    private final BookingProcessorFactory bookingProcessorFactory;

    @Inject
    public BookingResource(BookingProcessorFactory bookingProcessorFactory) {
        this.bookingProcessorFactory = bookingProcessorFactory;
    }

    @POST
    @Path("/process")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @JsonView(BookingItem.Views.Public.class)
    public List<GroupByDayBookingItem> doProcessOfBatchBookingRequest(BookingBatch bookingBatch) {
        logger.info("doProcessOfBatchBookingRequest");
        if(logger.isDebugEnabled()) {
            for(BookingItem bookingItem : bookingBatch.getBookingItems()) {
                logger.debug(bookingItem.toString());
            }
        }

        BookingProcessor bookingProcessor = bookingProcessorFactory.create(ProcessorType.BOOKING_PROCESSOR);
        List<GroupByDayBookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);

        if(logger.isDebugEnabled()) {
            for(GroupByDayBookingItem bookingItem : bookingItems) {
                logger.debug(bookingItem.toString());
            }
        }

        return bookingItems;
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatusService() {
        logger.info("getStatusService");
        return Response.ok().entity("Ok").status(Response.Status.OK).build();
    }

}
