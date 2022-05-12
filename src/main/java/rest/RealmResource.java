package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import repository.MountRepo;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("realm")
public class RealmResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final MountRepo mountRepo = repository.MountRepo.getMountRepo(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Consumes
    @Produces
    public Response getAllRealm() {
        return null;
    }


}
