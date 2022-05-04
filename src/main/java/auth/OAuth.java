package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;
import config.EnvConfig;
import org.codehaus.classworlds.uberjar.protocol.jar.Handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.time.Instant;
import java.util.Base64;
import java.util.Scanner;

public class OAuth implements IOAuth{
    private ApiConfig apiConfig;

    private String token = null;
    private Instant tokenExpiry = null;
    private EnvConfig envConfig;
    private final Object tokenLock = new Object();

    private final Gson gson = new GsonBuilder().create();

    //Just for mock-testing the URL connection.
    private URLStreamHandler urlStreamHandler = new Handler();

    @Override
    public String getAccessToken() {
        if (isTokenInvalid()) {

            String encodedCredentials = Base64.getEncoder()
                    .encodeToString(String.format("%s:%s", envConfig.getClientId(), envConfig.getClientSecret())
                            .getBytes(apiConfig.getEncoding()));

            HttpURLConnection con = null;
            String response = "";

            try {
                URL url = new URL(apiConfig.getTokenURL(), "", urlStreamHandler);
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
        System.out.println(oAuth.getAccessToken());
        System.out.println(oAuth.isTokenInvalid());
    }
}
