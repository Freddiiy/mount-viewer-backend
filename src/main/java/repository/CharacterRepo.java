package repository;

import utils.Api;
import utils.EMF_Creator;
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
    public Character getCharacterInfo(String region, String serverSlug, String name) {
        return null;
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
    public Media getMediaByCharacterId(int id) {
        return null;
    }

    public static void main(String[] args) {
    }

}
