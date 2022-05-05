package repository;

import utils.Api;
import utils.EMF_Creator;
import utils.types.Character;
import utils.types.Mount;
import utils.types.MountElement;

import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CharacterRepo {
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

    public Set<MountElement> getAllMountsOfCharacter(String region, String realmSlug, String characterName) {
        Map<String, String> map = new HashMap<>();

        map.put("namespace", "profile-"+region);
        map.put("locale", "en_US");

        try{
            Api api = Api.getInstance();
            Character character =
                    api.getDataFromApi("eu",
                            String.format("/profile/wow/character/%s/%s/collections/mounts", realmSlug, characterName),
                            map,
                            Character.class
                    );

            return new HashSet<>(character.getMounts());
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        CharacterRepo characterRepo = getCharacterRepo(EMF_Creator.createEntityManagerFactory());

        Set<MountElement> mountElementSet = characterRepo.getAllMountsOfCharacter("eu", "tarren-mill", "chasie");

        for (MountElement mountElement : mountElementSet) {
            System.out.println(mountElement.getMount().getName());
        }
    }
}
