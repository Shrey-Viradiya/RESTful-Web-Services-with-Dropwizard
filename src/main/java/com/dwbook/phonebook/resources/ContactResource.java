package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.dao.ContactDAO;
import com.dwbook.phonebook.representations.Contact;
import org.jdbi.v3.core.Jdbi;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Set;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id){
        // retrieve information about the contact with the provided id
        Contact contact = contactDAO.getContactById(id);
        return Response
                .ok(contact)
                .build();
    }

    @POST
    public Response createContact(@Valid Contact contact) throws URISyntaxException
    {
        // Validate the contact's data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

        // check for constrains violations
        if (violations.size() > 0){
            // Validation errors occurred
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<Contact> violation :
                    violations) {
                validationMessages.add((violation.getPropertyPath().toString() + ": " + violation.getMessage()));
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        }

        // else no problem constraints violation
        // store new contact
        int newContactId = contactDAO.createContact(contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response.created(new URI(String.valueOf(newContactId))).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id){
        // delete the contact with the provided id
        contactDAO.deleteContact(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(
            @PathParam("id") int id,
            Contact contact) {
        // Validate the contact's data
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);

        // check for constrains violations
        if (violations.size() > 0){
            // Validation errors occurred
            ArrayList<String> validationMessages = new ArrayList<>();
            for (ConstraintViolation<Contact> violation :
                    violations) {
                validationMessages.add((violation.getPropertyPath().toString() + ": " + violation.getMessage()));
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        }

        // else no problem constraints violation
        // update the contact with the provided ID
        contactDAO.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());
        return Response
                .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
                .build();
    }

    private final ContactDAO contactDAO;
    private final Validator validator;
    public ContactResource(Jdbi jdbi, Validator validator){
        this.contactDAO = jdbi.onDemand(ContactDAO.class);
        this.validator = validator;
    }
}
