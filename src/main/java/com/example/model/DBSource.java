package com.example.model;

import org.springframework.jdbc.core.JdbcTemplate;

public class DBSource implements Source<JdbcTemplate> {
    private JdbcTemplate source;

    public DBSource(JdbcTemplate jdbcTemplate, String tableName, int keyLength) {
        source = jdbcTemplate;

        tableName = tableName
                .toLowerCase()
                .replace(' ', '_');

        jdbcTemplate.execute("create table if not exists " + tableName + "(" +
                "id serial, property_key varchar(" + keyLength + "), property_value varchar ");
    }

    @Override
    public JdbcTemplate getSource() {
        return source;
    }
}
