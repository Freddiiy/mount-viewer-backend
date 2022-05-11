package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.AssetsDTO;
import dtos.CharacterDTO;
import dtos.MountDTO;
import dtos.MountElementDTO;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Mount;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CharacterRepo implements ICharacterRepo {
    private static EntityManagerFactory emf;
    private static CharacterRepo instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private CharacterRepo() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this repository.
     */


    public static CharacterRepo getCharacterRepo(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CharacterRepo();
        }
        return instance;
    }

    @Override
    public CharacterDTO getCharacterInfo(String region, String realmSlug, String characterName) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();

        map.put("namespace", "profile-"+region);
        map.put("locale", "en_US");

        return api.getDataFromApi(region, String.format("/profile/wow/character/%s/%s", realmSlug, characterName), map, CharacterDTO.class);
    }

    @Override
    public Set<Mount> getCharacterMounts(String region, String serverSlug, String name) {
        return null;
    }

    @Override
    public Set<Mount> getCharacterMountsByCharacterId(int id) {
        return null;
    }

    @Override
    public AssetsDTO getMediaByCharacterId(int id) {
        return null;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        CharacterRepo characterRepo = CharacterRepo.getCharacterRepo(EMF_Creator.createEntityManagerFactory());

        CharacterDTO characterDTO = characterRepo.getCharacterInfo("eu", "tarren-mill", "chasie");
        //Set<MountDTO> m = characterRepo.getAllMountsOfCharacter("eu","tarren-mill","chasie");
        System.out.println(characterDTO.getId() + " " + characterDTO.getLevel() + " " + characterDTO.getName());

    }

    public Set<MountDTO> getAllMountsOfCharacter(String region, String slug, String charName)
    {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<MountDTO> mountSet = new HashSet<>();

        map.put("namespace", "static-"+region);
        map.put("locale", "en_US");

        try {
            JsonObject jsonObject = api.getDataFromApi(region, String.format("/profile/wow/character/%s/%s/collections/mounts", slug,charName), map, JsonObject.class);

            for (JsonElement mounts : jsonObject.getAsJsonArray("mounts")) {
                Mount mount = gson.fromJson(mounts, Mount.class);
                mountSet.add(new MountDTO(mount));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return mountSet;
    }
}
