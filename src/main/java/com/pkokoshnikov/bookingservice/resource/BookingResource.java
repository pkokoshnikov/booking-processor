package com.pkokoshnikov.bookingservice.resource;

import com.pkokoshnikov.bookingservice.dao.BookingItemDAO;
import com.pkokoshnikov.bookingservice.model.request.BookingBatch;
import com.pkokoshnikov.bookingservice.model.request.BookingItem;
import com.pkokoshnikov.bookingservice.model.response.ResponseDayBookingItems;
import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.ResponsePacker;
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

    private final BookingProcessor bookingProcessor;
    private final BookingItemDAO bookingItemDAO;
    private final ResponsePacker responsePacker;


    @Inject
    public BookingResource(BookingProcessor bookingProcessor, BookingItemDAO bookingItemDAO, ResponsePacker responsePacker) {
        this.bookingProcessor = bookingProcessor;
        this.bookingItemDAO = bookingItemDAO;
        this.responsePacker = responsePacker;
    }

    @POST
    @Path("/process")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ResponseDayBookingItems> doProcessOfBatchBookingRequest(BookingBatch bookingBatch) {
        logger.info("doProcessOfBatchBookingRequest");
        logger.debug(bookingBatch);

        List<BookingItem> bookingItems = bookingProcessor.processBatch(bookingBatch);
        List<ResponseDayBookingItems> responseDayItems = responsePacker.packResponse(bookingItems);

        logger.debug(responseDayItems);

        return responseDayItems;
    }

    @GET
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStatusService() {
        logger.info("getStatusService");
        return Response.ok().entity("Ok").status(Response.Status.OK).build();
    }

}