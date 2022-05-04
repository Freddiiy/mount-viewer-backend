package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;
import utils.HttpUtils;
import utils.types.Character;
import utils.types.Mount;
import utils.types.MountElement;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;

public class Api implements IApi {
    private final Gson gson = new GsonBuilder().create();
    private ApiConfig apiConfig;

    @Override
    public <T> T getDataFromApi(String region, String path, Map<String, String> param, Class<T> theClass) throws IOException, URISyntaxException {
        return getDataFromApi(region, path, param, theClass, false);
    }

    @Override
    public <T> T getDataFromApi(String region, String path, Map<String, String> params, Class<T> theClass, boolean sendInHeader) throws IOException, URISyntaxException {
        OAuth oAuth = new OAuth();
        String token = oAuth.getAccessToken();

        UriBuilder uriBuilder = UriBuilder
                .fromUri(new URL(apiConfig.getApiURL(region)).toURI())
                .path(path);

        params.forEach(uriBuilder::queryParam);
        if (!sendInHeader) {
            uriBuilder.queryParam("access_token", token);
        }

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder(uriBuilder.build());
        if (sendInHeader) {
            requestBuilder.setHeader("Authorization", String.format("Bearer %s", token));
        }

        URL url = requestBuilder.build().uri().toURL();
        System.out.println(url);
        String response = HttpUtils.fetchData(url.toString());

        return gson.fromJson(response, theClass);
    }

    public static void main(String[] args) {
        Api api = new Api();
        Map<String, String> map = new HashMap<>();

        map.put("namespace", "static-eu");
        map.put("locale", "en_US");
        try {

        Mount mount = api.getDataFromApi("eu", "/data/wow/mount/6", map, Mount.class);
        Mount mount2 = api.getDataFromApi("eu", "/data/wow/mount/76", map, Mount.class);

        Map<String, String> profileMap = new HashMap<>();
        profileMap.put("namespace", "profile-eu");
        profileMap.put("locale", "en_US");

        Character character = api.getDataFromApi("eu", "/profile/wow/character/tarren-mill/chasie/collections/mounts", profileMap, Character.class);

        System.out.println("Mount id " + mount.getID() + " " + mount.getName() + "desc: " + mount.getDescription());
        System.out.println("Mount2 id " + mount2.getID() + " " + mount2.getName() + "desc: " + mount2.getDescription());

        for (MountElement mountElement : character.getMounts()) {
            System.out.println(mountElement.getMount().getName() + " " + mountElement.getMount().getID() + " " + mountElement.getIsFavorite() + " " + mountElement.getMount().getKey().getHref());
        }

        Mount mount3 = api.getDataFromApi("eu", "/data/wow/mount/1569", map, Mount.class);
        System.out.println("Mount id " + mount3.getID() + " " + mount3.getName() + "desc: " + mount3.getDescription());

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }
}
