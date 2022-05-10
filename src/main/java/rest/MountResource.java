package rest;

import auth.OAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MountDTO;
import dtos.ResponseBodyDTO;
import entities.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

import errorhandling.API_Exception;
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

    @GET
    @Path("test")
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET                                    //get all mounts in the game
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMounts() throws API_Exception {
        Set<MountDTO> mountDTOSet = new HashSet<>();
       try{
           mountDTOSet = mountRepo.getAllMounts();
       } catch (Exception e) {
           e.printStackTrace();
       }

       return Response
               .ok()
               .entity(GSON.toJson(mountDTOSet))
               .build();

    }


    @GET
    @Produces({MediaType.APPLICATION_JSON})     //get mount item media
    @Path("media/{itemId}")
    public Response getMountMedia(@PathParam("itemId") int itemId){
        return null;
    }


    //Not touching Gallars code just in case - O

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