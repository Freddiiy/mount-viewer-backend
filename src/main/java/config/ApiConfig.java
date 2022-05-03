package config;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class ApiConfig {
    private static final String tokenURL = "https://eu.battle.net/oauth/token";
    private static final String scheme = "https://";
    private String region;
    private final Charset encoding = StandardCharsets.UTF_8;
    private static final String baseURL = "https://eu.api.blizzard.com";
    private static final String apiURL = ".api.blizzard.com";
    private static final String mediaURL = "/data/wow/media";

    public ApiConfig() throws MalformedURLException {
    }

    public static URL getApiUrl(String region) throws MalformedURLException {
        return new URL(scheme + region + apiURL);
    }

    public static String getApiUrlAsString(String region) throws MalformedURLException {
        return getApiUrl(region).toString();
    }

    public static URL getMediaUrl(String region) throws MalformedURLException {
        return new URL(scheme + region + apiURL);
    }

    public static String getMediaUrlAsString(String region) throws MalformedURLException {
        return getApiUrl(region).toString();
    }

    public static String getTokenURl() {
        return tokenURL;
    }
}
