package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;
import config.EnvConfig;
import org.codehaus.classworlds.uberjar.protocol.jar.Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.time.Instant;
import java.util.Base64;
import java.util.Scanner;

public class OAuth implements IOAuth{
    private ApiConfig apiConfig = new ApiConfig();

    private String token = null;
    private Instant tokenExpiry = null;
    private EnvConfig envConfig = new EnvConfig();
    private final Object tokenLock = new Object();

    private final Gson gson = new GsonBuilder().create();

    //Just for mock-testing the URL connection.
    private final URLStreamHandler urlStreamHandler = new Handler();

    public OAuth() {
    }

    @Override
    public String getAccessToken(boolean testing) {
        if (isTokenInvalid()) {
            String encodedCredentials = Base64.getEncoder()
                    .encodeToString(String.format("%s:%s", envConfig.getClientId(), envConfig.getClientSecret())
                            .getBytes(apiConfig.getEncoding()));

            HttpURLConnection con = null;
            String response;
            URL url;

            try {
                if (testing) {
                    url = new URL(apiConfig.getTokenURL(), "", urlStreamHandler);
                } else {
                    url = new URL(apiConfig.getTokenURL(), "");
                }
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", String.format("Basic %s", encodedCredentials));
                con.setDoOutput(true);
                con.getOutputStream().write("grant_type=client_credentials".getBytes(apiConfig.getEncoding()));

                int responseCode = con.getResponseCode();

                Scanner scan = new Scanner(con.getInputStream());
                StringBuilder jsonStr = new StringBuilder();
                while (scan.hasNext()) {
                    jsonStr.append(scan.nextLine());
                }
                scan.close();

                response = String.valueOf(jsonStr);

                TokenResponse tokenResponse = gson.fromJson(response, TokenResponse.class);

                synchronized (tokenLock) {
                    tokenExpiry = Instant.now().plusSeconds(tokenResponse.getExpires_in());
                    token = tokenResponse.getAccess_token();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
        }

        synchronized (tokenLock) {
            return token;
        }
    }

    public String getAccessToken() {
        return getAccessToken(false);
    }

    @Override
    public boolean isTokenInvalid() {
        synchronized (tokenLock) {
            if (token == null) {
                return true;
            }
            if (tokenExpiry == null) {
                return true;
            }
            return Instant.now().isAfter(tokenExpiry);
        }

    }

    public static void main(String[] args) {
        OAuth oAuth = new OAuth();
        oAuth.getAccessToken();
        System.out.println(oAuth.getAccessToken());
    }
}
