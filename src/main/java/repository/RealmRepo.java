package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.RealmDTO;
import rest.RealmResource;
import utils.Api;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class RealmRepo implements IRealmRepo {
    private static EntityManagerFactory emf;
    private static RealmRepo instance;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private RealmRepo() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this repository.
     */

    public static RealmRepo getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new RealmRepo();
        }
        return instance;
    }

    public static RealmRepo getInstance() {
        if (instance == null) {
            instance = new RealmRepo();
        }
        return instance;
    }


    @Override
    public Set<RealmDTO> getAllRealms() throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        List<String> regionsList = new ArrayList<>();
        regionsList.add("eu");
        regionsList.add("us");
        Set<RealmDTO> realmSet = new HashSet<>();

        for (String s : regionsList) {

            Map<String, String> map = new HashMap<>();
            map.put("namespace", "dynamic-" + s);
            map.put("locale", "en_US");
            JsonObject jsonObject = api.getDataFromApi(s, "/data/wow/realm/index",
                    map, JsonObject.class);

            for (JsonElement realms : jsonObject.getAsJsonArray("realms")) {
                RealmDTO realmFromJson = gson.fromJson(realms, RealmDTO.class);
                realmSet.add(realmFromJson);
            }
        }

        for (RealmDTO realmDTO : realmSet) {
            System.out.println(realmDTO.getName() + " " + realmDTO.getSlug());
        }
        return realmSet;
    }

    @Override
    public Set<RealmDTO> getAllRealmsOfRegion(String region) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();
        Set<RealmDTO> realmSet = new HashSet<>();

        map.put("namespace", "dynamic-" + region);
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi(region, "/data/wow/realm/index",
                map, JsonObject.class);

        for (JsonElement realms : jsonObject.getAsJsonArray("realms")) {
            RealmDTO realmFromJson = gson.fromJson(realms, RealmDTO.class);
            realmSet.add(realmFromJson);
        }

        return realmSet;
    }

    @Override
    public RealmDTO getRealm(String region, int realmId) throws IOException, URISyntaxException {
        Api api = Api.getInstance();
        Map<String, String> map = new HashMap<>();

        map.put("namespace", "dynamic-" + region);
        map.put("locale", "en_US");

        JsonObject jsonObject = api.getDataFromApi(region, String.format("/data/wow/connected-realm/%o",
                realmId), map, JsonObject.class);

        return null;
    }

    public static void main(String[] args) {
        RealmRepo realmRepo = RealmRepo.getInstance();
        try {
            realmRepo.getAllRealms();
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
