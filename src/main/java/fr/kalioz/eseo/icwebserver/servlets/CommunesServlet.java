package fr.kalioz.eseo.icwebserver.servlets;

import fr.kalioz.eseo.icwebserver.apifetch.GouvCommunes;
import fr.kalioz.eseo.icwebserver.models.Ville;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

//@WebServlet( loadOnStartup=1)
@Path("/communes")
public class CommunesServlet {

    @GET
    public String getMsg() {
        return "Hello WOrld";
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
