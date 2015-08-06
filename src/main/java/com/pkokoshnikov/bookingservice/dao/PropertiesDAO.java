package com.pkokoshnikov.bookingservice.dao;

import com.pkokoshnikov.bookingservice.model.Property;

/**
 * User: pako1113
 * Date: 06.08.15
 */
public interface PropertiesDAO {
    Property findPropertyByName(String name);
}
