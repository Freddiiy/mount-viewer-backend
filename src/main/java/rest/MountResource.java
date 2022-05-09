package rest;

import auth.OAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MountDTO;
import dtos.ResponseBodyDTO;
import entities.User;
import java.util.List;
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
import repository.MountRepo;
import utils.EMF_Creator;
import utils.types.Assets;

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
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to User: " + thisuser + "\"}";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    //Not touching Gallars code just in case - O

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