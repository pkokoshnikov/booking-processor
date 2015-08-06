package integration.com.pkokoshnikov.bookingservice;

import com.pkokoshnikov.bookingservice.injection.ApplicationBinder;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseDayBookingItems;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseBookingItem;
import com.pkokoshnikov.bookingservice.resource.BookingResource;
import com.pkokoshnikov.bookingservice.util.date.formatter.DateConstants;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.TestProperties;
import org.junit.*;
import org.glassfish.jersey.test.JerseyTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * User: pako1113
 * Date: 13.07.15
 */

public class BookingResourceTest extends JerseyTest {

    private final SimpleDateFormat requestTimeSubmissionFormatter = new SimpleDateFormat(DateConstants.REQUEST_SUBMISSION_FORMAT);
    private final SimpleDateFormat meetingStartDateFormatter = new SimpleDateFormat(DateConstants.MEETING_START_FORMAT);

    @Test
    public void statusTest() {
        Response response = target("/booking/status").request().get();
        System.out.println(response);
    }

    @Test
    public void processTest() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        BookingItem bookingItem2 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:18:06"), meetingStartDateFormatter.parse("2011-03-21 11:00"),
                "EMP002", 2);
        BookingItem bookingItem3 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:19:06"), meetingStartDateFormatter.parse("2011-03-22 14:00"),
                "EMP003", 3);
        BookingBatch bookingBatch = new BookingBatch("0900", "1700", new BookingItem[]{bookingItem1, bookingItem2, bookingItem3});
        Entity<BookingBatch> entity = Entity.entity(bookingBatch, MediaType.APPLICATION_JSON_TYPE);

        Response response = target("/booking/process").request(MediaType.APPLICATION_JSON_TYPE).post(entity);
        List<ResponseDayBookingItems> responseBody = response.readEntity(new GenericType<List<ResponseDayBookingItems>>(){});

        assertEquals(responseBody.size(),2);
        assertEquals(responseBody.get(0).getResponseBookingItems().get(0), new ResponseBookingItem(bookingItem1));
        assertEquals(responseBody.get(0).getResponseBookingItems().get(1), new ResponseBookingItem(bookingItem2));
        assertEquals(responseBody.get(1).getResponseBookingItems().get(0), new ResponseBookingItem(bookingItem3));
    }

    @Ignore
    @Test
    public void addAndGetBookingItemTest() throws ParseException {
        BookingItem bookingItem1 = new BookingItem(requestTimeSubmissionFormatter.parse("2011-03-17 10:17:06"), meetingStartDateFormatter.parse("2011-03-21 09:00"),
                "EMP001", 2);
        Entity<BookingItem> entity = Entity.entity(bookingItem1, MediaType.APPLICATION_JSON_TYPE);
        Response response = target("/booking/add").request(MediaType.APPLICATION_JSON_TYPE).post(entity);

        Long responseId = response.readEntity(new GenericType<Long>() { });

        assertEquals(response.getStatus(), 200);
        assertEquals(responseId, Long.valueOf(1));

        response = target("/booking/find").queryParam("itemId", responseId).request(MediaType.APPLICATION_JSON_TYPE).get();
        BookingItem bookingItem = response.readEntity(new GenericType<BookingItem>(){});
        assertEquals(bookingItem1, bookingItem);
    }

    @Override
    protected Application configure() {
        return new ResourceConfig() {
            {
                register(BookingResource.class);
                register(new ApplicationBinder());
                enable(TestProperties.LOG_TRAFFIC);
                enable(TestProperties.DUMP_ENTITY);
            }
        };
    }
}

