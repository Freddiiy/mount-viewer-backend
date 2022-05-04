package repository;

import utils.types.Mount;

import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

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

    public static Media getCharacterMediaByNameAndServer(String name, String server, String region)
    {
        Map<String, String> map = new HashMap<>();
        Media media = null;

        map.put("namespace", "static-"+region);
        map.put("locale", "en_US");

        try{
            media = api.getDataFromApi(region, String.format("/data/wow/character/%s/%s/character-media",server,name), map, Media.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return media;
    }
}
