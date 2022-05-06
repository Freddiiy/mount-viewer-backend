package repository;

import utils.Api;
import utils.EMF_Creator;
import utils.types.Assets;
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

    //Changed return Datatype from Media to String since we only need the href from media.
    //Also, where the fuck is the Media class????
    public String getMediaByCreatureId(int id){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        String media = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

        try{
            media = api.getDataFromApi("us", "/data/wow/media/creature/"+id, map, String.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return media;
    }

    //Same with this one.
    public Assets getMountMediaByItemId(int id){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Assets media = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

        try{
            media = api.getDataFromApi("us", "/data/wow/media/item/"+id, map, Assets.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return media;
    }


    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = EMF_Creator.createEntityManagerFactory();
        MountRepo mountRepo = getMountRepo(entityManagerFactory);

    }
}










