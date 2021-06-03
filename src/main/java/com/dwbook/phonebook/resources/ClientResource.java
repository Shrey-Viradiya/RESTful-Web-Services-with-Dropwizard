package com.dwbook.phonebook.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dwbook.phonebook.representations.Contact;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;

@Path("/client/")
@Produces(MediaType.TEXT_PLAIN)
public class ClientResource {
    private final Client client;
    public ClientResource(Client client){
        this.client = client;
    }

    @GET
    @Path("showContact")
    public String showContact(@QueryParam("id") int id){
        WebTarget target = client.target("http://localhost:8080/contact/");
        WebTarget resourceWebTarget = target.path(""+id);
        Invocation.Builder invocationBuilder;
        invocationBuilder = resourceWebTarget.request(
                MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        Contact C = response.readEntity(Contact.class);

        return "ID: " + id
                + "\nFirst Name: " + C.getFirstName()
                + "\nLast Name: " + C.getLastName()
                + "\nPhone: " + C.getPhone();
    }

    @GET
    @Path("newContact")
    public Response newContact(@QueryParam("firstName") String fName,
                               @QueryParam("lastName") String lName,
                               @QueryParam("phone") String phone){
        WebTarget target = client.target("http://localhost:8080/contact/");
        Invocation.Builder invocationBuilder;
        invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.json(new Contact(0, fName, lName, phone)));

        if (response.getStatus() == 201){
            // created
            System.out.println(response.getHeaders());
            return Response
                    .status(302)
                    .entity("The contact was created successfully! New contact can be found at " + response.getHeaders().getFirst("Location"))
                    .build();
        }
        else{
            // other status code
            return Response
                    .status(422)
                    .entity(response.readEntity(String.class))
                    .build();
        }
    }

    @GET
    @Path("updateContact")
    public Response updateContact(@QueryParam("id") int id, @QueryParam("firstName") String firstName, @QueryParam("lastName") String lastName, @QueryParam("phone") String phone) {
        WebTarget target = client.target("http://localhost:8080/contact/" + id);
        Invocation.Builder invocationBuilder;
        invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.put(Entity.json(new Contact(id, firstName, lastName, phone)));

        if (response.getStatus() == 200) {
            // Created
            return Response.status(302).entity("The contact was updated successfully!").build();
        }
        else {
            // Other Status code, indicates an error
            return Response.status(422).entity(response.readEntity(String.class)).build();
        }
    }

    @GET
    @Path("deleteContact")
    public Response deleteContact(@QueryParam("id") int id) {
        WebTarget target = client.target("http://localhost:8080/contact/" + id);
        Response response = target.request().delete(Response.class);
        return Response.noContent().entity("Contact was deleted!").build();
    }
}
