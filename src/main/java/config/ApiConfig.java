package config;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ApiConfig {
    private static final String tokenURL = "https://eu.battle.net/oauth/token";
    private static final String scheme = "https://";
    private static String region;
    private static final Charset encoding = StandardCharsets.UTF_8;
    private static final String baseURL = "https://eu.api.blizzard.com";
    private static final String apiURL = ".api.blizzard.com";
    private static final String mediaURL = "/data/wow/media";

    private String _tokenURL;
    private String _scheme;
    private String _region;
    private Charset _encoding;
    private String _baseURL;
    private String _apiURL;
    private String _mediaURL;

    public ApiConfig() {
    }

    @PostConstruct
    public void init() {
        this._tokenURL = tokenURL;
        this._apiURL = apiURL;
        this._baseURL = baseURL;
        this._encoding = encoding;
        this._scheme = scheme;
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

    public static String getTokenURL() {
        return tokenURL;
    }

    public static Charset getEncoding() {
        return encoding;
    }

    /***
     * Getters for init'd variables (basically only for mocking)
     */

    public static String getRegion() {
        return region;
    }

    public String get_tokenURL() {
        return _tokenURL;
    }

    public URL get_tolenURLAsURL() throws MalformedURLException {
        return new URL(_tokenURL);
    }

    public String get_scheme() {
        return _scheme;
    }

    public String get_region() {
        return _region;
    }

    public Charset get_encoding() {
        return _encoding;
    }

    public String get_baseURL() {
        return _baseURL;
    }

    public String get_apiURL() {
        return _apiURL;
    }

    public String get_mediaURL() {
        return _mediaURL;
    }
}
