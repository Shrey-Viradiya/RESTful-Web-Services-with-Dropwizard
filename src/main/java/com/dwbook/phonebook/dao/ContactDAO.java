package com.dwbook.phonebook.dao;

import com.dwbook.phonebook.dao.mappers.ContactMapper;
import com.dwbook.phonebook.representations.Contact;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface ContactDAO {
    @SqlQuery("SELECT * FROM contact WHERE id = :id")
    @RegisterRowMapper(ContactMapper.class)
    Contact getContactById(@Bind("id") int id);

    @GetGeneratedKeys("id")
    @SqlUpdate("INSERT INTO contact (id, firstName, lastName, phone) VALUES (NULL, :firstName, :lastName, :phone)")
    int createContact(@Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("phone") String phone);

    @SqlUpdate("UPDATE contact SET firstName = :firstName, lastName = :lastName, phone = :phone WHERE id = :id")
    void updateContact(@Bind("id") int id, @Bind("firstName") String firstName, @Bind("lastName") String lastName,@Bind("phone") String phone);

    @SqlUpdate("DELETE FROM contact WHERE id = :id")
    void deleteContact(@Bind("id") int id);
}
