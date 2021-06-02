package com.dwbook.phonebook.dao.mappers;

import com.dwbook.phonebook.representations.Contact;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactMapper implements RowMapper<Contact> {

    @Override
    public Contact map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Contact(
                rs.getInt("id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("phone")
        );
    }
}
