package repository;

import dtos.AssetsDTO;
import dtos.CharacterDTO;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Mount;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharacterRepo implements ICharacterRepo {
    private static EntityManagerFactory emf;
    private static CharacterRepo instance;

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
        System.out.println(characterDTO.getId() + " " + characterDTO.getLevel() + " " + characterDTO.getName());
    }

}
