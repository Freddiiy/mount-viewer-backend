package auth;

import com.google.gson.JsonObject;
import com.nimbusds.jose.util.IOUtils;

import javax.json.Json;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Scanner;

public class OAuth {
    private final String clientId = "bee7aab86d8a4bfcb7f0b854505eade5";
    private final String clientSecret = "IzLMAKZbTseihrY7tHTwJRNSqzYbOSMz";
    private String accessToken;

    public String generateAccessToken() {
        String encodedCredentials = Base64.getEncoder().encodeToString(String.format("%s:%s", clientId, clientSecret).getBytes(StandardCharsets.UTF_8));

        HttpURLConnection con = null;
        String response = "";

        try {
            URL url = new URL("https://us.battle.net/oauth/token");
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

        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void main(String[] args) {
        OAuth oAuth = new OAuth();
        System.out.println(oAuth.generateAccessToken());
    }
}
