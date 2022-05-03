package config;

import javax.annotation.PostConstruct;

public class EnvConfig {
    private static final String clientId = "bee7aab86d8a4bfcb7f0b854505eade5";
    private static final String clientSecret = "IzLMAKZbTseihrY7tHTwJRNSqzYbOSMz";
    private String _clientId;
    private String _clientSecret;

    @PostConstruct
    public void init() {
        _clientId = clientId;
        _clientSecret = clientSecret;
    }
    public static String getClientId() {
        return clientId;
    }

    public static String getClientSecret() {
        return clientSecret;
    }

    public String get_clientId() {
        return _clientId;
    }

    public String get_clientSecret() {
        return _clientSecret;
    }
}
