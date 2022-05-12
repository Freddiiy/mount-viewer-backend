package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.AssetsDTO;
import dtos.MountDTO;
import entities.Mount;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Assets;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


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
    public Set<MountDTO> getAllMounts() throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<MountDTO> mountSet = new HashSet<>();

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

            JsonObject jsonObject = api.getDataFromApi("eu", "/data/wow/mount/index", map, JsonObject.class);

            for (JsonElement mounts : jsonObject.getAsJsonArray("mounts")) {
                Mount mount = gson.fromJson(mounts, Mount.class);
                mountSet.add(new MountDTO(mount));
            }

        return mountSet;
    }

    @Override
    public MountDTO getMountByMountId(int id) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        MountDTO mount = null;

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

             mount = api.getDataFromApi("eu", "/data/wow/mount/"+id, map, MountDTO.class);

        assert mount != null;
        return mount;
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
    public Set<AssetsDTO> getItemMediaByItemId(int id) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<AssetsDTO> assestList = new HashSet<>();

        map.put("namespace", "static-us");
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi("us", String.format("/data/wow/media/item/%S", id), map, JsonObject.class);

        for(JsonElement assets : jsonObject.getAsJsonArray("assets"))
        {
            Assets asset = gson.fromJson(assets, Assets.class);

            if(asset.getKey().equals("icon"))
            {
                assestList.add(new AssetsDTO(asset));
            }
        }

        return assestList;
    }


    @Override
    public Set<AssetsDTO> getItemMediaByMountId(int id) throws IOException, URISyntaxException {    //metode virker men mangler Mount entity!
        EntityManager em = emf.createEntityManager();
        Mount mount;

        try {
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", id);
            mount = query.getSingleResult();
            if(mount == null)
                throw new EntityNotFoundException("the item media with mountId "+id+" was not found");

            System.out.println(mount.getItemId());
        } finally {
            em.close();
        }

        Set<AssetsDTO> assets = new HashSet<>();
        assets = getItemMediaByItemId(mount.getItemId());

       return assets;
    }


    public static void main(String[] args) throws IOException, URISyntaxException {
        MountRepo mountRepo = MountRepo.getMountRepo(emf);
        Set<AssetsDTO> set = mountRepo.getItemMediaByMountId(6);

        for(AssetsDTO m : set)
        {
            System.out.println(m.getValue());
        }

    }
}











