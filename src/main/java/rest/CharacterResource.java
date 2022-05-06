package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CharacterDTO;
import dtos.MountDTO;
import dtos.MountElementDTO;
import repository.CharacterRepo;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("character")
public class CharacterResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final CharacterRepo characterRepo = CharacterRepo.getCharacterRepo(EMF);

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("test")
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @GET
    @Path("getAllMounts/{region}/{slug}/{charName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getByTitle(@PathParam("region") String region,@PathParam("slug") String Slug,@PathParam("charName") String charName) throws EntityNotFoundException{
        //Not finished, needed some return type refactoring?
//        Set<MountElementDTO> m = characterRepo.getAllMountsOfCharacter(region, Slug, charName);
//        return Response.ok().entity(GSON.toJson(m)).build();

        return null;
    }


}
