package com.dwbook.phonebook.representations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.dropwizard.validation.ValidationMethod;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class Contact {
    private final int id;

    @NotBlank
    @Length(min=2, max=255)
    private final String firstName;

    @NotBlank
    @Length(min=2, max=255)
    private final String lastName;

    @NotBlank
    @Length(min=2, max=30)
    private final String phone;

    public Contact(){
        this.id = 0;
        this.firstName = null;
        this.lastName = null;
        this.phone = null;
    }

    public  Contact(int id, String firstName, String lastName, String phone){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    @ValidationMethod(message = "John Doe is not a valid person!")
    @JsonIgnore
    public  boolean isValidPerson(){
        return !Objects.equals(firstName, "John") || !Objects.equals(lastName, "Doe");
    }
}
