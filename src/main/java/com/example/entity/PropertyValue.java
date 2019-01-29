package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "property_values")
public class PropertyValue implements Value {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "key_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PropertyKey key;

    @Column(nullable = false)
    private String value;

    public PropertyValue(Key key, String value) {
        this.key = (PropertyKey) key;
        this.value = value;
    }

    public PropertyValue() {

    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public PropertyKey getKey() {
        return key;
    }

    @Override
    public void setKey(Key key) {
        this.key = (PropertyKey) key;
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