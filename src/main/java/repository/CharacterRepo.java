package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.AssetsDTO;
import dtos.CharacterDTO;
import dtos.MountElementDTO;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Assets;
import utils.types.Mount;
import utils.types.MountElement;

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
    public Set<Mount> getCharacterMountsByCharacterId(int id) {
        return null;
    }

    @Override
    public AssetsDTO getMediaByCharacterId(int id) {
        return null;
    }


    @Override
    public Set<MountElementDTO> getCharacterMounts(String region, String slug, String charName) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<MountElementDTO> mountSet = new HashSet<>();

        map.put("namespace", "profile-"+region);
        map.put("locale", "en_US");

            JsonObject jsonObject = api.getDataFromApi(region, String.format("/profile/wow/character/%s/%s/collections/mounts", slug,charName), map, JsonObject.class);

            for (JsonElement mounts : jsonObject.getAsJsonArray("mounts")) {
                    MountElement mount = gson.fromJson(mounts, MountElement.class);
                    mountSet.add(new MountElementDTO(mount));
            }

        return mountSet;
    }

    public Set<AssetsDTO> getCharacterMedia(String name, String region, String realm) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<AssetsDTO> assetsSet = new HashSet<>();

        map.put("namespace", "profile-"+region);
        map.put("locale", "en_US");

             JsonObject jsonObject = api.getDataFromApi(region, String.format("profile/wow/character/%s/%s/character-media", realm, name), map, JsonObject.class);

            for (JsonElement assets : jsonObject.getAsJsonArray("assets")) {
                Assets asset = gson.fromJson(assets, Assets.class);

                if(asset.getKey().equals("avatar"))
                {
                    assetsSet.add(new AssetsDTO(asset));
                }
            }

       return assetsSet;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        CharacterRepo characterRepo = CharacterRepo.getCharacterRepo(EMF_Creator.createEntityManagerFactory());

        Set<AssetsDTO> assetsDTO = characterRepo.getCharacterMedia("chasie","eu","tarren-mill");

        CharacterDTO characterDTO = characterRepo.getCharacterInfo("eu", "tarren-mill", "chasie");

        System.out.println(characterDTO.getRealm().getSlug());

    }
}
