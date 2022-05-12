package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.RealmDTO;
import errorhandling.API_Exception;
import repository.MountRepo;
import repository.RealmRepo;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

@Path("realm")
public class RealmResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final RealmRepo realmRepo = repository.RealmRepo.getInstance(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response getAllRealm() throws API_Exception {
        Set<RealmDTO> realmList = new HashSet<>();
        try {
            realmList = realmRepo.getAllRealms();
        }
        catch (IOException | URISyntaxException e)
        {
            throw new API_Exception("Realms Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(realmList))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{region}")
    public Response getAllRealmByRegion(@PathParam("region") String region) throws API_Exception {
        Set<RealmDTO> realmList = new HashSet<>();
        try{
            realmList = realmRepo.getAllRealmsOfRegion(region);
        }
        catch (IOException | URISyntaxException e)
        {
            throw new API_Exception("Realms Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(realmList))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{region}/{id}")
    public Response getRealm(@PathParam("region") String region,@PathParam("id") int id) throws API_Exception {
        RealmDTO realm;
        try{
            realm = realmRepo.getRealm(region,id);
        }
        catch (IOException | URISyntaxException e)
        {
            throw new API_Exception("Realm Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(realm))
                .build();
    }

}
