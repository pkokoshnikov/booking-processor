package com.pkokoshnikov.bookingservice.dao;

import com.pkokoshnikov.bookingservice.model.request.BookingItem;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class BookingItemDAO {
    final static Logger logger = Logger.getLogger(BookingItemDAO.class);
    private final EntityManager entityManager;

    @Inject
    public BookingItemDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void addBookingItem(BookingItem bookingItem) {
        entityManager.getTransaction().begin();
        entityManager.persist(bookingItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void deleteBookingItem(BookingItem bookingItem) {
        entityManager.getTransaction().begin();
        entityManager.remove(bookingItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void updateBookingItem(BookingItem bookingItem) {
        entityManager.getTransaction().begin();
        entityManager.merge(bookingItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public BookingItem findBookingItem(long id){
        entityManager.getTransaction().begin();
        BookingItem bookingItem = entityManager.find(BookingItem.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return bookingItem;
    }

    public List<BookingItem> findBookingItemsByUserId(String userId) {
        entityManager.getTransaction().begin();
        List<BookingItem> bookingItems = entityManager.createNamedQuery("BookingItem.findByUserId", BookingItem.class)
                .setParameter("userId", userId).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return bookingItems;
    }
}
