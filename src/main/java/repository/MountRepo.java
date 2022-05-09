package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.AssetsDTO;
import dtos.MountDTO;
import dtos.ResponseBodyDTO;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Mount;

import jakarta.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


public class MountRepo implements IMountRepo {

    private static EntityManagerFactory emf;
    private static MountRepo instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

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

    /***
     * @return Set of mounts with only name and Id.
     *
     * If we query the info of all the mounts in this method, then we end up
     * with a ratelimit and a very slow api. It is intended that we just query the mounts information when
     * it is clicked on in the frontend application.
     */

    @Override
    public Set<MountDTO> getAllMounts() {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<MountDTO> mountSet = new HashSet<>();

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

        try {
            JsonObject jsonObject = api.getDataFromApi("eu", "/data/wow/mount/index", map, JsonObject.class);

            for (JsonElement mounts : jsonObject.getAsJsonArray("mounts")) {
                Mount mount = gson.fromJson(mounts, Mount.class);
                mountSet.add(new MountDTO(mount));
            }
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return mountSet;
    }

    @Override
    public MountDTO getMountByMountId(int id){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        MountDTO mount = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");
        try{
             mount = api.getDataFromApi("eu", "/data/wow/mount/"+id, map, MountDTO.class);
        }

        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        assert mount != null;
        return mount;
    }

    @Override
    public MountDTO getMountByName(String name){
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        MountDTO mount = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");
        map.put("name.en_US", name);

        try{
            mount = api.getDataFromApi("eu", "/data/wow/search/mount", map, MountDTO.class);
        }
        catch (URISyntaxException | IOException e){
            e.printStackTrace();
        }

        return mount;
    }

    //TODO: Make when database is made :)
    @Override
    public MountDTO getMountByItemId(int itemId) {
        return null;
    }

    @Override
    public AssetsDTO getCreatureMediaByMountId(int id) {
        return null;
    }

    @Override
    public AssetsDTO getCreatureMediaByCreatureId(int id) {
        return null;
    }

    @Override
    public AssetsDTO getItemMediaByItemId(int id) {
        return null;
    }

    @Override
    public AssetsDTO getItemMediaByMountId(int id) {
        return null;
    }


    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = EMF_Creator.createEntityManagerFactory();
        MountRepo mountRepo = MountRepo.getMountRepo(entityManagerFactory);

        MountDTO mountDTO = mountRepo.getMountByMountId(6);

        System.out.println(mountDTO.getName());

    }
}











