package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssetsDTO;
import dtos.MountDTO;
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


/**
 * @author lam@cphbusiness.dk
 */


@Path("mount")
public class MountResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final MountRepo mountRepo = repository.MountRepo.getMountRepo(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    @GET
    @Produces(MediaType.APPLICATION_JSON)       //get all mounts
    @Path("/")
    public Response getAllMounts() throws API_Exception {
        Set<MountDTO> mounts = new HashSet<>();
        try {
            mounts = mountRepo.getAllMounts();
        }
        catch (IOException | URISyntaxException e) {
            throw new API_Exception("Character Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(mounts))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)       //get mount by id
    @Path("/{id}")
    public Response getMountById(@PathParam("id") int id) throws EntityNotFoundException, API_Exception {
        MountDTO mountDTO;
        try {
            mountDTO = mountRepo.getMountByMountId(id);
        }
        catch (IOException | URISyntaxException e) {
            throw new API_Exception("Character Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(mountDTO))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("media/item/{id}")
    public Response getMountByItemId(@PathParam("id") int id) throws EntityNotFoundException, API_Exception {
        Set<AssetsDTO> assetList = new HashSet<>();
        try {
            assetList = mountRepo.getItemMediaByItemId(id);
        }
        catch (IOException |URISyntaxException e){
            throw new API_Exception("Item Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(assetList))
                .build();
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