package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MountDTO;
import entities.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import errorhandling.API_Exception;
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



    @GET
    @Produces(MediaType.APPLICATION_JSON)       //get mount by id
    @Path("getById/{id}")
    public Response getMountById(@PathParam("id") int id) throws EntityNotFoundException {
        MountDTO m = mountRepo.getMountByMountId(id);
        return Response.ok().entity(GSON.toJson(m)).build();
    }

   @GET
   @Produces(MediaType.APPLICATION_JSON)        //get mount by name
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