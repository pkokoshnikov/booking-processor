package com.pkokoshnikov.bookingservice.model;

import javax.persistence.*;

/**
 * User: pako1113
 * Date: 03.08.15
 */
@Entity
@Table(name = "properties")
@NamedQuery(name="Property.findByName", query="SELECT p FROM Property p WHERE p.name = :name")
public class Property {
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    @Id
    @Column(name="name")
    private String name;

    @Column(name="value")
    private String value;

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Property() {
    }
/*

    public Long getId() {
        return id;
    }
*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
