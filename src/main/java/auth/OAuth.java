package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Scanner;

public class OAuth implements IOAuth{
    private final String clientId = "bee7aab86d8a4bfcb7f0b854505eade5";
    private final String clientSecret = "IzLMAKZbTseihrY7tHTwJRNSqzYbOSMz";
    private String token = null;
    private Instant tokenExpiry = null;
    private final Object tokenLock = new Object();

    private final Gson gson = new GsonBuilder().create();

    @Override
    public String getAccessToken() {
        if (isTokenInvalid()) {

            String encodedCredentials = Base64.getEncoder().encodeToString(String.format("%s:%s", clientId, clientSecret).getBytes(StandardCharsets.UTF_8));

            HttpURLConnection con = null;
            String response = "";

            try {
                URL url = new URL(ApiConfig.getTokenURl());
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization", String.format("Basic %s", encodedCredentials));
                con.setDoOutput(true);
                con.getOutputStream().write("grant_type=client_credentials".getBytes(StandardCharsets.UTF_8));

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
