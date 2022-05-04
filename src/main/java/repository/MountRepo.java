package repository;

import utils.types.Character;
import utils.types.Mount;
import utils.types.MountElement;

import javax.persistence.EntityManagerFactory;
import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public List<Mount> getAllMounts() //sprøg gallar når hans hovede ikke koger
    {
        Map<String, String> map = new HashMap<>();
        Mount mount = null;

        map.put("namespace", "static-");
        map.put("locale", "en_US");

        try{
            mount = api.getDataFromApi("eu", "/data/wow/mount/", map, Mount.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return new ArrayList<>(mount);
    }


    public Mount getMountByMountId(int id, String region){
        Map<String, String> map = new HashMap<>();
        Mount mount = null;

        map.put("namespace", "static-"+region);
        map.put("locale", "en_US");

        try{
             mount = api.getDataFromApi(region, "/data/wow/mount/"+id, map, Mount.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return mount;
    }

    public Mount getMountByName(String name, String region){
        Map<String, String> map = new HashMap<>();
        Mount mount = null;

        map.put("namespace", "static-"+region);
        map.put("locale", "en_US");
        map.put("name.en_US", name);

        try{
            mount = api.getDataFromApi(region, "/data/wow/search/mount", map, Mount.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return mount;
    }

    public Media getMountMediaByCreatureId(int id, String region){
        Map<String, String> map = new HashMap<>();
        Media media = null;

        map.put("namespace", "static-"+region);
        map.put("locale", "en_US");

        try{
            media = api.getDataFromApi(region, "/data/wow/media/creature/"+id, map, Media.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return media;
    }

    public Media getMountMediaByItemId(int id, String region){
        Map<String, String> map = new HashMap<>();
        Media media = null;

        map.put("namespace", "static-"+region);
        map.put("locale", "en_US");

        try{
            media = api.getDataFromApi(region, "/data/wow/media/item/"+id, map, Media.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return media;
    }

    public List<MountElement> getAllMountsbyCharacter(String name, String server, String region){
        Map<String, String> profileMap = new HashMap<>();
        profileMap.put("namespace", "profile-"+region);
        profileMap.put("locale", "en_US");

        Character character = api.getDataFromApi(region, String.format("/profile/wow/character/%s/%s/collections/mounts",server,name), profileMap, Character.class);

        return new ArrayList<>(character.getMounts());
    }
}










