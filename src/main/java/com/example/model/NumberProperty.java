package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "number_dictionary")
public class NumberProperty implements Property {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 5, max = 5, message = "Key length must be strictly 5 symbols!")
    @Pattern(regexp = "[0-9]{5}", message = "Key must contain only numbers!")
    @Column(name = "property_key", nullable = false)
    private String key;

    @Column(name = "property_value")
    private String value;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}

