package fr.kalioz.eseo.icwebserver.servlets;

import fr.kalioz.eseo.icwebserver.apifetch.GouvCommunes;
import fr.kalioz.eseo.icwebserver.models.Ville;
import org.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/communes")
public class CommunesServlet {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCommunes(@DefaultValue("10") @QueryParam("limit") Integer limit, @DefaultValue("") @QueryParam("name") String name){
        List<Ville> villes = Ville.getByName(name, limit);
        JSONArray output = new JSONArray();
        for (Ville v: villes){
            output.put(v.toJson());
        }

        return Response.status(200).entity(output.toString()).build();
    }

    @POST
    @Path("/update")
    public Response updateDatabase() {
        Ville.cleanTable();
        GouvCommunes.fetchAndSaveAllDepartements();
        return Response.status(200).entity("done !").build();
    }
}
