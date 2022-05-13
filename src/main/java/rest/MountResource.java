package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssetsDTO;
import dtos.BasicMountDTO;
import dtos.MountDTO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.*;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import dtos.MountElementDTO;
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
        List<BasicMountDTO> mounts;
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
    @Path("{id}")
    public Response getMountByMountId(@PathParam("id") Long id) throws EntityNotFoundException, API_Exception {
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
    public Response getItemMediaByMountId(@PathParam("id") Long id) throws EntityNotFoundException, API_Exception{
        Set<AssetsDTO> assetList = new HashSet<>();
        try{
            assetList = mountRepo.getItemMediaByMountId(id);
        }
        catch (IOException | URISyntaxException e) {
            throw new API_Exception("Item Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(assetList))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("media/creature/{id}")
    public Response getCreatureMediaByMountId(@PathParam("id") Long id) throws EntityNotFoundException, API_Exception {
       Set<AssetsDTO> assetList = new HashSet<>();
       try{
           assetList = mountRepo.getCreatureMediaByMountId(id);
       }
       catch (IOException | URISyntaxException e){
           throw new API_Exception("Creature Not Found", 404, e);
       }

       return Response
               .ok()
               .entity(GSON.toJson(assetList))
               .build();
    }
}