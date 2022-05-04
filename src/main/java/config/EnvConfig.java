package config;

import javax.annotation.PostConstruct;

public class EnvConfig {
    private static final String clientIdEnv = "bee7aab86d8a4bfcb7f0b854505eade5";
    private static final String clientSecretEnv = "IzLMAKZbTseihrY7tHTwJRNSqzYbOSMz";
    private String clientId;
    private String clientSecret;

    @PostConstruct
    public void init() {
        clientId = clientIdEnv;
        clientSecret = clientSecretEnv;
    }
    public static String getClientIdEnv() {
        return clientIdEnv;
    }

    public static String getClientSecretEnv() {
        return clientSecretEnv;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
