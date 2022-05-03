package auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ApiConfig;
import config.EnvConfig;
import org.junit.Assert;
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


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class OAuthTest {

    @Mock
    private ApiConfig apiConfig;

    @Mock
    private EnvConfig envConfig;

    @Mock
    private final Gson gson = new GsonBuilder().create();

    @InjectMocks
    private OAuth oAuth;

    @Test
    void getAccessToken() throws IOException {
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

        String clientId = "someFakeClientId";
        String clientSecret = "someFakeClientSecret";
        Charset encodeFormat = StandardCharsets.UTF_8;
        int responseCode = 200;

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                String.format("{'access_token':'%s','token_type': 'bearer', 'expires_in':'1'}", token).getBytes(ApiConfig.getEncoding())
        );

        Mockito.doReturn(byteArrayInputStream).when(mockUrlConnection).getInputStream();
        Mockito.doReturn(outputStream).when(mockUrlConnection).getOutputStream();

        FieldSetter.setField(oAuth, oAuth.getClass().getDeclaredField("urlStreamHandler"), urlStreamHandler);

        Mockito.doReturn(clientId).when(envConfig).get_clientId();
        Mockito.doReturn(clientSecret).when(envConfig).get_clientSecret();
        Mockito.doReturn(encodeFormat).when(apiConfig).get_encoding();
        Mockito.doReturn(new URL("https://www.google.com/")).when(apiConfig).get_tolenURLAsURL();
        Mockito.doReturn(mockTokenResponse).when(gson).fromJson(Mockito.anyString(), Mockito.eq(TokenResponse.class));

        Mockito.doReturn(token).when(mockTokenResponse).getAccess_token();
        Mockito.doReturn(responseCode).when(mockUrlConnection).getResponseCode();

        Assert.assertEquals(token, oAuth.getAccessToken());
    }

    @Test
    void isTokenInvalid() {
    }
}