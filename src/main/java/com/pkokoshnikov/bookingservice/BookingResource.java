package com.pkokoshnikov.bookingservice;

import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingNode;
import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import org.apache.log4j.Logger;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
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
    public List<BookingNode> doProcessOfBatchBookingRequest(BookingBatch bookingBatch) {
        logger.info("doProcessOfBatchBookingRequest");
        for(BookingNode bookingNode : bookingBatch.getBookingNodes()) {
            logger.info(bookingNode.toString());
        }

        return bookingProcessor.processBatch(bookingBatch);
    }


    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatusService() {
        logger.info("getStatusService");
        return "OK";
    }

}
