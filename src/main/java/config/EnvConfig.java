package config;

import org.junit.Assert;

import javax.annotation.PostConstruct;


public class EnvConfig {
    private static final String clientIdEnv = "bee7aab86d8a4bfcb7f0b854505eade5";
    private static final String clientSecretEnv = "IzLMAKZbTseihrY7tHTwJRNSqzYbOSMz";
    private String clientId;
    private String clientSecret;

    public EnvConfig() {
        this.clientId = clientIdEnv;
        this.clientSecret = clientSecretEnv;
    }

    @PostConstruct
    public void init() {
        this.clientId = clientIdEnv;
        this.clientSecret = clientSecretEnv;
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
