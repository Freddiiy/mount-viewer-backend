package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.AssetsDTO;
import dtos.CreatureDisplayDTO;
import dtos.MountDTO;
import dtos.SourceDTO;
import entities.Mount;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Assets;
import utils.types.CreatureDisplay;
import utils.types.MountElement;
import utils.types.Source;

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

import static java.lang.String.format;


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
    public MountDTO getMountByMountId(Long id) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        MountDTO mountDTO = null;
        Mount mount = null;

        if(!mountExist(id)) {
            //If the mount doesn't exist or have null values in it's row.
            map.put("namespace", "static-eu");
            map.put("locale", "en_US");

            mountDTO = api.getDataFromApi("eu", "/data/wow/mount/"+id, map, MountDTO.class);

            //Lorte kode skal fikses
            String savedAsset = "";
            Set<AssetsDTO> assetsDTOS = getCreatureMediaByMountId(id);
            for(AssetsDTO a : assetsDTOS){
                savedAsset = a.getValue();
            }
            mount = new Mount(mountDTO);
            mount.setDisplay(savedAsset);
            mergeMountData(mount);
            return mountDTO;
        }
        else{
            //If the mount does exist and doesn't have null values in it's row.
            mount = getMountFromDb(id);
            System.out.println("Eksisterer");
        }
        assert mountDTO != null;
        return new MountDTO(mount);
    }

    @Override
    public Set<AssetsDTO> getCreatureMediaByMountId(Long id) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<CreatureDisplayDTO> creatureList = new HashSet<>();
        Long creatureId = null;

        map.put("namespace", "static-us");
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi("us", format("/data/wow/mount/%s", id), map, JsonObject.class);

        for(JsonElement creatures : jsonObject.getAsJsonArray("creature_displays")){
            CreatureDisplay creature = gson.fromJson(creatures, CreatureDisplay.class);

            creatureList.add(new CreatureDisplayDTO(creature));
        }

        for(CreatureDisplayDTO creatureDisplayDTO : creatureList)
        {
           creatureId = creatureDisplayDTO.getID();
        }

        return getCreatureMediaByCreatureId(creatureId);
    }

    @Override
    public Set<AssetsDTO> getCreatureMediaByCreatureId(Long id) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<AssetsDTO> assetList = new HashSet<>();

        map.put("namespace", "static-us");
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi("us", format("/data/wow/media/creature-display/%S",id), map, JsonObject.class);

        for(JsonElement assets : jsonObject.getAsJsonArray("assets"))
        {
            Assets asset = gson.fromJson(assets, Assets.class);

            if(asset.getKey().equals("zoom"))
            {
                assetList.add(new AssetsDTO(asset));
            }
        }

        return assetList;
    }

    @Override
    public Set<AssetsDTO> getItemMediaByItemId(Long id) throws IOException, URISyntaxException {
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
    public Set<AssetsDTO> getItemMediaByMountId(Long id) throws IOException, URISyntaxException {    //metode virker men mangler Mount entity!
        EntityManager em = emf.createEntityManager();
        Mount mount;

        try {
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", id);
            mount = query.getSingleResult();
            if(mount == null)
                throw new EntityNotFoundException("the item media with mountId "+id+" was not found");
        } finally {
            em.close();
        }

        Set<AssetsDTO> assets = new HashSet<>();
        assets = getItemMediaByItemId(mount.getItemId());

       return assets;
    }

    //Might need EntityManager Refactoring:))))
    //Might need some changes
    @Override
    public String getSourceByMountId(Long id) throws IOException, URISyntaxException {
        EntityManager em = emf.createEntityManager();
        Mount mount;
        try
        {
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", id);
            mount = query.getSingleResult();
        } finally
        {
            em.close();
        }
        if(mount.getSource() == null)
        {
            EntityManager em1 = emf.createEntityManager();
            Api api = Api.getInstance();
            Map<String, String> map = new HashMap<>();
            map.put("namespace", "static-us");
            map.put("locale", "en_US");
            JsonObject jsonObject = api.getDataFromApi("us", String.format("/data/wow/media/item/%S", id), map, JsonObject.class);

            for(JsonElement sources : jsonObject.getAsJsonArray("source"))
            {
                Source source = gson.fromJson(sources, Source.class);
                SourceDTO sourceDTO = new SourceDTO(source);
                mount.setSource(sourceDTO.getType());
                try {
                    em1.getTransaction();
                    em1.merge(mount);
                } finally {
                    em1.close();
                }
            }
        }
        return mount.getSource();
    }

    @Override
    public String getDescriptionByMountId(Long id) throws IOException, URISyntaxException {
        EntityManager em = emf.createEntityManager();
        Mount mount;
        try
        {
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", id);
            mount = query.getSingleResult();
        } finally
        {
            em.close();
        }
        if(mount.getDescription() == null)
        {
            EntityManager em1 = emf.createEntityManager();
            Api api = Api.getInstance();
            Map<String, String> map = new HashMap<>();
            map.put("namespace", "static-us");
            map.put("locale", "en_US");
            JsonObject jsonObject = api.getDataFromApi("us", String.format("/data/wow/media/item/%S", id), map, JsonObject.class);

            for(JsonElement descriptions : jsonObject.getAsJsonArray("source"))
            {
                String description = gson.fromJson(descriptions, String.class);
                mount.setDescription(description);
                try {
                    em1.getTransaction();
                    em1.merge(mount);
                } finally {
                    em1.close();
                }
            }
        }
        else
        return mount.getDescription();

        return null;
    }

    public Mount getMountFromDb(Long mountId){
        EntityManager em = emf.createEntityManager();
        Mount mount;

        try{
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", mountId);
            mount = query.getSingleResult();
        } finally {
            em.close();
        }
        return mount;
    }

    public void mergeMountData(Mount mount){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            TypedQuery<Mount> query = em.createQuery("UPDATE Mount m SET m.description = :description, m.source = :source, m.display = :display WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("description", mount.getDescription());
            query.setParameter("source", mount.getSource());
            query.setParameter("display", mount.getDisplay());
            query.setParameter("mountId", mount.getMountId());
            query.executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private boolean mountExist(Long mountId){
        EntityManager em = emf.createEntityManager();

        try{
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", mountId);
            Mount mount = query.getSingleResult();
            return mount.isFieldsNotNull();
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        EntityManagerFactory _emf   = EMF_Creator.createEntityManagerFactory();
        MountRepo mountRepo = MountRepo.getMountRepo(_emf);
        MountDTO habibi = mountRepo.getMountByMountId(12L);

        System.out.println(habibi.getName());



    }
}











