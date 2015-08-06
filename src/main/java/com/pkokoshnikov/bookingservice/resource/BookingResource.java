package com.pkokoshnikov.bookingservice.resource;

import com.pkokoshnikov.bookingservice.dao.BookingItemDAO;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseDayBookingItems;
import com.pkokoshnikov.bookingservice.process.BookingProcessor;
import com.pkokoshnikov.bookingservice.process.response.ResponsePacker;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;


/**
 * User: pako1113
 * Date: 07.07.15
 */
@Path("/booking")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    final static Logger logger = Logger.getLogger(BookingResource.class);

    private final BookingProcessor bookingService;
    private final BookingItemDAO bookingItemDAO;
    private final ResponsePacker responsePacker;


    @Inject
    public BookingResource(BookingProcessor bookingProcessor, BookingItemDAO bookingItemDAO, ResponsePacker responsePacker) {
        this.bookingService = bookingProcessor;
        this.bookingItemDAO = bookingItemDAO;
        this.responsePacker = responsePacker;
    }

    @POST
    @Path("/process")
    public List<ResponseDayBookingItems> processBatch(BookingBatch bookingBatch) {
        logger.info("processBatch");
        logger.debug(bookingBatch);

        List<BookingItem> bookingItems = bookingService.processBatch(bookingBatch);
        bookingItemDAO.addBookingItems(bookingItems);
        List<ResponseDayBookingItems> responseDayItems = responsePacker.packResponse(bookingItems);

        logger.debug(responseDayItems);

        return responseDayItems;
    }

    @POST
    @Path("/addItem")
    public Long processBookingItem(BookingItem bookingItem) {
        logger.info("processBookingItem");
        logger.debug(bookingItem);
        bookingItemDAO.addBookingItem(bookingItem);

        return bookingItem.getId();
    }
/*
    @GET
    @Path("/findByDay")
    public BookingItem getBookingItemsByDay(@QueryParam("day")Date date) {
        logger.info("getBookingItem");
        BookingItem foundItem = bookingItemDAO.findBookingItem(itemId);

        return foundItem;
    }*/

    @GET
    @Path("/findByUser")
    public BookingItem getBookingItemsByUser(@QueryParam("userId")Long itemId) {
        logger.info("getBookingItem");
        BookingItem foundItem = bookingItemDAO.findBookingItem(itemId);

        return foundItem;
    }


    @GET
    @Path("/status")
    public Response getStatusService() {
        logger.info("getStatusService");
        return Response.ok().entity("Ok").status(Response.Status.OK).build();
    }

}
