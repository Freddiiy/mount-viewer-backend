package utils;

import auth.OAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;
import dtos.ResponseBodyDTO;

import jakarta.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.*;
import java.net.http.HttpRequest;
import java.util.Map;

public class Api implements IApi {
    private final Gson gson = new GsonBuilder().create();
    private final ApiConfig apiConfig = new ApiConfig();
    private static Api instance;

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }
        return instance;
    }

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

    public <T> ResponseBodyDTO<T> getResponseBody(String region, String path, Map<String, String> params, Class<T> theClass) throws IOException, URISyntaxException {
        return getResponseBody(region, path, params, theClass, false);
    }

    public <T> ResponseBodyDTO<T> getResponseBody(String region, String path, Map<String, String> params, Class<T> theClass, boolean sendInHeader) throws IOException, URISyntaxException {
        T t = getDataFromApi(region, path, params, theClass, sendInHeader);
        return new ResponseBodyDTO<>(t);
    }


    public static void main(String[] args) {

    }
}
