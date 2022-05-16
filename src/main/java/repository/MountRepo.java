package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.*;
import entities.Mount;
import entities.MountItemID;
import utils.Api;
import utils.EMF_Creator;
import utils.types.Assets;
import utils.types.CreatureDisplay;
import utils.types.Source;

import javax.persistence.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

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
    public List<ExtendedMountDTO> getAllMounts() throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        List<ExtendedMountDTO> mountList = new ArrayList<>();

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi("eu", "/data/wow/mount/index", map, JsonObject.class);

        mountList = getAllMountsFromDb();

        /*
            for (JsonElement mounts : jsonObject.getAsJsonArray("mounts")) {
                BasicMountDTO basicMount = gson.fromJson(mounts, BasicMountDTO.class);
                MountDTO mountDTO = getMountByMountId(basicMount.getId());
                mountList.add(mountDTO);
            }

         */
        return mountList;
    }

    @Override
    public ExtendedMountDTO getMountByMountId(Long id) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        MountDTO mountDTO;
        Mount mount;

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
            insertMount(mount);

            return new ExtendedMountDTO(mount, getItemId_ByMountId(mount.getMountId()), getIconDisplay_ByMountId(mount.getMountId()));
        }
        else{
            //If the mount does exist and doesn't have null values in it's row.
            mount = getMountFromDb(id);
            return new ExtendedMountDTO(mount, getItemId_ByMountId(mount.getMountId()), getIconDisplay_ByMountId(mount.getMountId()));
        }
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
        Set<AssetsDTO> assetList = new HashSet<>();

        map.put("namespace", "static-us");
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi("us", String.format("/data/wow/media/item/%S", id), map, JsonObject.class);

        for(JsonElement assets : jsonObject.getAsJsonArray("assets"))
        {
            Assets asset = gson.fromJson(assets, Assets.class);

            if(asset.getKey().equals("icon"))
            {
                assetList.add(new AssetsDTO(asset));
            }
        }

        return assetList;
    }

    @Override
    public void fillItemDisplay() throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        List<AssetsDTO> assetList = new ArrayList<>();

        map.put("namespace", "static-us");
        map.put("locale", "en_US");

        EntityManager em = emf.createEntityManager();
        EntityManager em1 = emf.createEntityManager();

        try {
            //Get list of mountItemIDs
            TypedQuery<MountItemID> query = em.createQuery("SELECT m FROM MountItemID m WHERE m.itemId is not null", MountItemID.class);
            List<MountItemID> mountItemIDS = query.getResultList();
            //Iter List of ItemIDs
            for (int i = 0; i < mountItemIDS.size() ; i++)
            {
                //Fetch individual itemIcon
                JsonObject jsonObject = api.getDataFromApi("us", String.format("/data/wow/media/item/%S", mountItemIDS.get(i).getItemId()), map, JsonObject.class);
                for(JsonElement assets : jsonObject.getAsJsonArray("assets"))
                {
                    Assets asset = gson.fromJson(assets, Assets.class);

                    if(asset.getKey().equals("icon"))
                    {
                        assetList.add(new AssetsDTO(asset));
                    }
                }
                System.out.println(assetList.get(i).getValue());
                try{
                    em1.getTransaction().begin();
                    TypedQuery<MountItemID> updateQuery = em1.createQuery("UPDATE MountItemID m SET m.iconDisplay = :asset WHERE m.itemId = :itemId", MountItemID.class);
                    updateQuery.setParameter("asset", assetList.get(i).getValue());
                    updateQuery.setParameter("itemId", mountItemIDS.get(i).getItemId());
                    int rowsUpdated = updateQuery.executeUpdate();
                    System.out.println("Entities updated: "+ rowsUpdated);
                    em1.getTransaction().commit();
                } catch (IndexOutOfBoundsException exception){
                System.out.println("ass");
                }
            }
        } finally {
            em.close();
            em1.close();
        }

    }

    @Override
    public Set<AssetsDTO> getItemMediaByMountId(Long id) throws IOException, URISyntaxException {    //metode virker men mangler Mount entity!
        EntityManager em = emf.createEntityManager();
        MountItemID mount;

        try {
            TypedQuery<MountItemID> query = em.createQuery("SELECT m FROM MountItemID m WHERE m.mountId = :mountId", MountItemID.class);
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
                SourceDTO sourceDTO = new SourceDTO(source.getType(),source.getName());
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
        return mount.getDescription();
    }

    //Slave Method that is used for another method
    public String getIconDisplay_ByMountId(Long id){
        EntityManager em = emf.createEntityManager();
        String returnValue;
        try {
            TypedQuery<String> query = em.createQuery("SELECT m.iconDisplay FROM MountItemID m WHERE m.itemId is not null and m.mountId = :mountId", String.class);
            query.setParameter("mountId", id);
            returnValue = query.getSingleResult();
        } catch(NoResultException e){
            returnValue = "";
            return returnValue;
        }
            finally {
            em.close();
        }

        return returnValue;
    }

    //Slave Method that is used for another method
    public Long getItemId_ByMountId(Long id){
        EntityManager em = emf.createEntityManager();
        Long returnValue;

        try{
            TypedQuery<Long> query = em.createQuery("SELECT m.itemId FROM MountItemID m WHERE m.itemId is not null and m.mountId = :mountId", Long.class);
            query.setParameter("mountId", id);
            returnValue = query.getSingleResult();
        } catch(NoResultException e) {
            returnValue = 0L;
            return returnValue;
        }
        finally {
            em.close();
        }
        return returnValue;
    }

    public List<ExtendedMountDTO> getAllMountsFromDb() {
        EntityManager em = emf.createEntityManager();

        List<Mount> mountList;
        try {
            TypedQuery<Mount> query = em.createQuery("select m from Mount m", Mount.class);
            mountList = query.getResultList();

            List<ExtendedMountDTO> mountDTOList = new ArrayList<>();
            for (Mount m : mountList) {
                if(getItemId_ByMountId(m.getMountId()) == 0) continue;
                if(getIconDisplay_ByMountId(m.getMountId()).equals("")) continue;

                ExtendedMountDTO extendedMountDTO = new ExtendedMountDTO(m, getItemId_ByMountId(m.getMountId()), getIconDisplay_ByMountId(m.getMountId()));
                mountDTOList.add(extendedMountDTO);
            }
            return mountDTOList;
        } finally {
            em.close();
        }
    }

//    public void deleteDuplicates() {
//        List<ExtendedMountDTO> extendedMountDTOS = getAllMountsFromDb();
//        for (int i = 0; i <= extendedMountDTOS.size(); i++) {
//            if(extendedMountDTOS.get(i).getItemId() == extendedMountDTOS.get(i+1).getItemId()){
//                extendedMountDTOS.remove
//            }
//        }
//    }

    public Mount getMountFromDb(Long mountId){
        EntityManager em = emf.createEntityManager();
        Mount mount = null;

        try{
            TypedQuery<Mount> query = em.createQuery("SELECT m FROM Mount m WHERE m.mountId = :mountId", Mount.class);
            query.setParameter("mountId", mountId);
            mount = query.getSingleResult();
        } catch (NoResultException ignored) {

        } finally {
            em.close();
        }
        return mount;
    }

    public void insertMount(Mount mount) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(mount);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
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
            TypedQuery<Long> query = em.createQuery("select count(m) from Mount m where m.mountId = :mountId", Long.class);
            query.setParameter("mountId", mountId);

            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        EntityManagerFactory _emf   = EMF_Creator.createEntityManagerFactory();
        MountRepo mountRepo = MountRepo.getMountRepo(_emf);

        mountRepo.getAllMounts();
        //This line fills MountItemID table with iconDisplay values
        //mountRepo.fillItemDisplay();

    }
}












