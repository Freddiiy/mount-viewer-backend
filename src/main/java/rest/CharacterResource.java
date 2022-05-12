package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.AssetsDTO;
import dtos.CharacterDTO;
import dtos.MountDTO;
import dtos.MountElementDTO;
import errorhandling.API_Exception;
import javax.ws.rs.*;
import repository.CharacterRepo;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
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
    @Path("{region}/{slug}/{charName}")     //get character information
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCharacter(@PathParam("region") String region, @PathParam("slug") String slug, @PathParam("charName") String charName) throws API_Exception {
        CharacterDTO characterDTO;
        try {
            characterDTO = characterRepo.getCharacterInfo(region, slug, charName);
        } catch (IOException | URISyntaxException e) {
            throw new API_Exception("Character Not Found", 404, e);
        }

        return Response
                .ok()
                .entity(GSON.toJson(characterDTO))
                .build();
    }

    @GET
    @Path("mounts/{region}/{slug}/{charName}")      //get all character mounts
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCharacterMounts(@PathParam("region") String region,@PathParam("slug") String Slug,@PathParam("charName") String charName) throws API_Exception {
      Set<MountElementDTO> mountElementSet = new HashSet<>();
      try {
          mountElementSet = characterRepo.getCharacterMounts(region, Slug, charName);
      }
      catch (IOException | URISyntaxException e) {
          throw new API_Exception("Character Not Found", 404, e);
      }

      return Response
              .ok()
              .entity(GSON.toJson(mountElementSet))
              .build();

    }

    @GET
    @Path("media/{region}/{slug}/{charName}")     //get character media
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCharacterMedia(@PathParam("region") String region, @PathParam("slug") String slug, @PathParam("charName") String charName) throws API_Exception {
        Set<AssetsDTO> assetList = new HashSet<>();
               try{
                   assetList = characterRepo.getCharacterMedia(charName,region,slug);
               }
               catch (IOException | URISyntaxException e) {
                   throw new API_Exception("Character Not Found", 404, e);
               }

        return Response
                .ok()
                .entity(GSON.toJson(assetList))
                .build();
    }
}
