package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.*;

@Entity
@Table(name = "property_keys")
public class PropertyKey implements Key {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @Column(nullable = false, unique = true)
    private String key;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "key", orphanRemoval = true)
    @JsonIgnore
    private List<PropertyValue> values;

    @Column(nullable = false)
    @JsonIgnore
    private int type = -1;

    public PropertyKey(String key, int type) {
        this.key = key;
        this.values = new ArrayList<>();
        this.type = type;
    }

    public PropertyKey() {

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
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public List<Value> getValues() {
        return new ArrayList<>(values);
    }

    @Override
    public void setValues(List<Value> values) {
        this.values = new ArrayList<>();

        for (Value value : values)
            this.values.add((PropertyValue) value);
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int type) {
        this.type = type;
    }
}
