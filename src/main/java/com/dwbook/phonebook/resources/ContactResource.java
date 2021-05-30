package com.dwbook.phonebook.resources;

import com.dwbook.phonebook.representations.Contact;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactResource {
    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") int id){
        // retrieve information about the contact with the provided id
        return Response
                .ok(new Contact(id, "Shrey", "Viradiya", "+911234567890"))
                .build();
    }

    @POST
    public Response createContact(Contact contact)
    {
        // store new contact
        return Response.created(null).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteContact(@PathParam("id") int id){
        // delete the contact with the provided id
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response updateContact(
            @PathParam("id") int id,
            Contact contact) {
        // update the contact with the provided ID
        // ...
        return Response
                .ok(new Contact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone()))
                .build();
    }
}
