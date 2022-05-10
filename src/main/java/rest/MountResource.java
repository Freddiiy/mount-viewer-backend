package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MountDTO;
import entities.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.*;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import repository.MountRepo;
import utils.EMF_Creator;


/**
 * @author lam@cphbusiness.dk
 */


@Path("mount")
public class MountResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final MountRepo mountRepo = repository.MountRepo.getMountRepo(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getById/{id}")
    public Response getMountById(@PathParam("id") int id) throws EntityNotFoundException {
        MountDTO m = mountRepo.getMountByMountId(id);
        return Response.ok().entity(GSON.toJson(m)).build();
    }

   @GET
   @Produces(MediaType.APPLICATION_JSON)
   @Path("getByName/{name}")
   public Response getMountByName(@PathParam("name") String name) throws EntityNotFoundException{
        MountDTO m = mountRepo.getMountByName(name);
        return Response.ok().entity(GSON.toJson(m)).build();
    }

    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getMediaByCreatureId/{id}")
    public Response getMediaByCreatureId(@PathParam("id") int id) throws EntityNotFoundException{
        Assets href = mountRepo.getCreatureMediaByCreatureId(2);
        return Response.ok().entity(GSON.toJson(href)).build();
    }

     */
}