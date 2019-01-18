package com.example.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "word_dictionary")
public class WordProperty implements Property {
    private WordProperty() {}

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Pattern(regexp = "[a-zA-Z]{4}")
    @Column(name = "key", nullable = false, unique = true)
    private String key;

    @Column(name = "value")
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