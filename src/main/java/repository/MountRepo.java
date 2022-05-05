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
import java.util.*;

public class MountRepo {

    private static EntityManagerFactory emf;
    private static MountRepo instance;

    private MountRepo() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this repository.
     */


    public static MountRepo getMountRepo(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MountRepo();
        }
        return instance;
    }

    public Mount getMountByMountId(int id){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Mount mount = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

        try{
             mount = api.getDataFromApi("eu", "/data/wow/mount/"+id, map, Mount.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return mount;
    }

    public Mount getMountByName(String name){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Mount mount = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");
        map.put("name.en_US", name);

        try{
            mount = api.getDataFromApi("eu", "/data/wow/search/mount", map, Mount.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return mount;
    }

    public Media getMediaByCreatureId(int id){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Media media = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

        try{
            media = api.getDataFromApi("eu", "/data/wow/media/creature/"+id, map, Media.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return media;
    }

    public Media getMountMediaByItemId(int id, String region){
        return null;
    }


    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = EMF_Creator.createEntityManagerFactory();
        MountRepo mountRepo = getMountRepo(entityManagerFactory);

        Mount mount = mountRepo.getMountByMountId(6);
        Mount mount1 = mountRepo.getMountByMountId(76);

        System.out.println(mount.getName());
        System.out.println(mount1.getName());

    }
}










