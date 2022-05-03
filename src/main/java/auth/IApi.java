package auth;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public interface IApi {
    <T> T getDataFromApi(String region, String path, Map<String, String> param, Class<T> theClass) throws IOException, URISyntaxException;

    <T> T getDataFromApi(String region, String path, Map<String, String> param, Class<T> theClass, boolean sendHeader) throws IOException, URISyntaxException;
}
