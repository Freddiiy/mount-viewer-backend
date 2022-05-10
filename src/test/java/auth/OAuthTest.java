package auth;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;
import config.EnvConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@ExtendWith(MockitoExtension.class)
public class OAuthTest {

    @Mock
    private ApiConfig apiConfig;

    @Mock
    private EnvConfig envConfig;

    @Mock
    private final Gson gson = new GsonBuilder().create();

    @InjectMocks
    private OAuth oAuth;

    @Disabled
    @Test
    public void testGetAccessToken() throws IOException, NoSuchFieldException {
        final String token = "fakeToken";
        final HttpURLConnection mockUrlConnection = Mockito.mock(HttpURLConnection.class);
        final OutputStream outputStream = Mockito.mock(OutputStream.class);
        final TokenResponse mockTokenResponse = Mockito.mock(TokenResponse.class);

        final URLStreamHandler urlStreamHandler = new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) {
                return mockUrlConnection;
            }
        };

        String clientId = "fakeClientId";
        String clientSecret = "fakeClientSecret";
        Charset encodeFormat = StandardCharsets.UTF_8;
        int responseCode = 200;

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                String.format("{'access_token':'%s', 'expires_in':'1'}", token).getBytes(encodeFormat)
        );

        Mockito.doReturn(clientId).when(envConfig).getClientId();
        Mockito.doReturn(clientSecret).when(envConfig).getClientSecret();
        Mockito.doReturn(encodeFormat).when(apiConfig).getEncoding();
        Mockito.doReturn(new URL("https://www.google.com/")).when(apiConfig).getTokenURL();
       // Mockito.doReturn(mockTokenResponse).when(gson).fromJson(Mockito.anyString(), Mockito.eq(TokenResponse.class));

        FieldSetter fieldSetter = new FieldSetter(oAuth, oAuth.getClass().getDeclaredField("urlStreamHandler"));
        fieldSetter.set(urlStreamHandler);

        Mockito.doReturn(byteArrayInputStream).when(mockUrlConnection).getInputStream();
        Mockito.doReturn(outputStream).when(mockUrlConnection).getOutputStream();
        Mockito.doReturn(responseCode).when(mockUrlConnection).getResponseCode();

        //ockito.doReturn(token).when(mockTokenResponse).getAccess_token();

        Assertions.assertEquals(token, oAuth.getAccessToken(true));
        Mockito.verify(mockUrlConnection, Mockito.times(1)).setRequestMethod("POST");
        Mockito.verify(mockUrlConnection, Mockito.times(1))
                .setRequestProperty("Authorization",
                        String.format("Basic %s",
                                Base64.getEncoder().encodeToString(
                                        String.format("%s:%s", clientId, clientSecret).getBytes(encodeFormat))));

        Mockito.verify(mockUrlConnection, Mockito.times(1)).setDoOutput(true);
        Mockito.verify(outputStream, Mockito.times(1)).write("grant_type=client_credentials".getBytes(encodeFormat));
        Mockito.verify(mockUrlConnection, Mockito.times(1)).getResponseCode();
    }

    @Test
    public void testCachedToken() throws NoSuchFieldException {
        final String token = "cachedToken";


        FieldSetter tokenExpiryReflect = new FieldSetter(oAuth, oAuth.getClass().getDeclaredField("tokenExpiry"));
        tokenExpiryReflect.set(Instant.now().plus(5, ChronoUnit.MINUTES));

        FieldSetter tokenReflect = new FieldSetter(oAuth, oAuth.getClass().getDeclaredField("token"));
        tokenReflect.set(token);

        Assertions.assertEquals(token, oAuth.getAccessToken());
    }

    @Test
    public void testInvalidTokenValidToken() throws NoSuchFieldException {
        FieldSetter tokenExpiryReflect = new FieldSetter(oAuth, oAuth.getClass().getDeclaredField("tokenExpiry"));
        tokenExpiryReflect.set(Instant.now().plus(5, ChronoUnit.MINUTES));

        FieldSetter tokenReflect = new FieldSetter(oAuth, oAuth.getClass().getDeclaredField("token"));
        tokenReflect.set("sampleToken");

        Assertions.assertFalse(oAuth.isTokenInvalid());
    }

    @Disabled
    @Test
    public void testInvalidTokenIfNull() {
    }
}