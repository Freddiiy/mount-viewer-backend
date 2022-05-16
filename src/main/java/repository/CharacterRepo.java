package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.*;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Assets;
import entities.Mount;
import utils.types.MountElement;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
    public ExtendedCharacterDTO getCharacterInfo(String region, String realmSlug, String characterName) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();

        map.put("namespace", "profile-"+region);
        map.put("locale", "en_US");
        CharacterDTO characterDTO = api.getDataFromApi(region, String.format("/profile/wow/character/%s/%s", realmSlug, characterName), map, CharacterDTO.class);

        ExtendedCharacterDTO extendedCharacterDTO = new ExtendedCharacterDTO(characterDTO, region, getCharacterMedia(region, realmSlug, characterName));

        System.out.println(extendedCharacterDTO.getAssets());
        return extendedCharacterDTO;
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
        System.out.println(jsonObject);
            for (JsonElement mounts : jsonObject.getAsJsonArray("mounts")) {
                    MountElementDTO mountElementDTO = gson.fromJson(mounts, MountElementDTO.class);
                    mountSet.add(mountElementDTO);
            }
        return mountSet;
    }

    public List<AssetsDTO> getCharacterMedia(String region, String realmSlug, String name) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        List<AssetsDTO> assetsSet = new ArrayList<>();

        map.put("namespace", "profile-"+region);
        map.put("locale", "en_US");

             JsonObject jsonObject = api.getDataFromApi(region, String.format("profile/wow/character/%s/%s/character-media", realmSlug, name), map, JsonObject.class);

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

        CharacterDTO characterDTO = characterRepo.getCharacterInfo("eu", "tarren-mill", "chasie");

        System.out.println(characterDTO.getName());

       // Set<AssetsDTO> assetsDTO = characterRepo.getCharacterMedia("chasie","eu","tarren-mill");
    }
}
