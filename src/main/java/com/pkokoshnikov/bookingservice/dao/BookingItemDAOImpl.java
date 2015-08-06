package com.pkokoshnikov.bookingservice.dao;

import com.pkokoshnikov.bookingservice.model.BookingItem;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

/**
 * User: pako1113
 * Date: 24.07.15
 */
public class BookingItemDAOImpl implements BookingItemDAO {
    final static Logger logger = Logger.getLogger(BookingItemDAOImpl.class);
    private final EntityManager entityManager;

    @Inject
    public BookingItemDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addBookingItem(BookingItem bookingItem) {
        entityManager.getTransaction().begin();
        entityManager.persist(bookingItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void addBookingItems(List<BookingItem> bookingItem) {
        entityManager.getTransaction().begin();
        bookingItem.stream().forEach(item -> entityManager.persist(item));
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void deleteBookingItem(BookingItem bookingItem) {
        entityManager.getTransaction().begin();
        entityManager.remove(bookingItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public void updateBookingItem(BookingItem bookingItem) {
        entityManager.getTransaction().begin();
        entityManager.merge(bookingItem);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public BookingItem findBookingItem(long id){
        entityManager.getTransaction().begin();
        BookingItem bookingItem = entityManager.find(BookingItem.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();

        return bookingItem;
    }

    @Override
    public List<BookingItem> findBookingItemsByDate(Date date) {
        return null;
    }

    @Override
    public List<BookingItem> findBookingItemsByUserId(String userId) {
        entityManager.getTransaction().begin();
        List<BookingItem> bookingItems = entityManager.createNamedQuery("BookingItem.findByUserId", BookingItem.class)
                .setParameter("userId", userId).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

        return bookingItems;
    }
}
