package fr.kalioz.eseo.icwebserver.servlets;

import fr.kalioz.eseo.icwebserver.apifetch.GouvCommunes;
import fr.kalioz.eseo.icwebserver.models.Ville;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/communes")
public class CommunesServlet {

    // This method is called if TEXT_PLAIN is request
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }

    // This method is called if XML is request
    @GET
    @Consumes(MediaType.TEXT_XML)
    public String sayXMLHello() {
        return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
    }

    @GET
    @Consumes(MediaType.TEXT_HTML)
    public String sayHtmlHello() {
        return "<html> " + "<title>" + "Hello Jersey" + "</title>"
                + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
    }

    /*@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommunes(@DefaultValue("10") @QueryParam("limit") Integer limit, @DefaultValue("") @QueryParam("name") String name){
        List<Ville> villes = Ville.getByName(name, limit);
        JSONArray output = new JSONArray();
        for (Ville v: villes){
            output.put(v.toJson());
        }

        return Response.status(200).entity(output.toString()).build();
    }*/

    @POST
    @Path("/update")
    public Response updateDatabase() {
        Ville.cleanTable();
        GouvCommunes.fetchAndSaveAllDepartements();
        return Response.status(200).build();
    }
}
