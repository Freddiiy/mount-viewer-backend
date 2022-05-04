package config;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ApiConfig {
    private URL tokenURL;
    private String scheme;
    private String region;
    private Charset encoding;
    private String baseURL;
    private String apiURL;
    private String mediaURL;

    public ApiConfig() {
    }

    @PostConstruct
    public void init() throws MalformedURLException {
        this.tokenURL = new URL("https://eu.battle.net/oauth/token");
        this.scheme = "https://";
        this.apiURL = ".api.blizzard.com";
        this.baseURL = "https://eu.api.blizzard.com";
        this.encoding = StandardCharsets.UTF_8;
    }

    public URL getTokenURL() {
        return tokenURL;
    }

    public String getTokenURLAsString() {
        return tokenURL.toString();
    }

    public String getScheme() {
        return scheme;
    }

    public String getRegion() {
        return region;
    }

    public Charset getEncoding() {
        return encoding;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getApiURL(String region) {
        return apiURL;
    }

    public String getMediaURL(String region) {
        return mediaURL;
    }
}
