package auth;

import config.ApiConfig;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.Api;
import utils.HttpUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;

@ExtendWith(MockitoExtension.class)
public class ApiTest {
    @Mock
    HttpUtils httpUtils;

    @Mock
    private OAuth oAuth;

    @Mock
    private ApiConfig apiConfig;

    @Spy
    @InjectMocks
    private Api api;

    private String fakeRegion;
    private String fakeToken;
    private String relativePath;
    private String baseURl;

    @BeforeEach
    public void setUp() throws Exception {
        fakeRegion = "eu";
        fakeToken = "fakeToken";
        relativePath = "relativePath";
        baseURl = "http://www.google.com/";

        //Mockito.doReturn(fakeToken).when(oAuth).getAccessToken();
        //Mockito.doReturn(baseURl).when(apiConfig).getBaseURL();
    }

    @Test
    public void getDataFromPath() throws IOException, URISyntaxException {
        TokenResponse mockTokenResponse = Mockito.mock(TokenResponse.class);
        Mockito.doReturn(mockTokenResponse)
                .when(api)
                .getDataFromApi(Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyMap(), Mockito.eq(TokenResponse.class), Mockito.eq(Boolean.FALSE));

        Assertions.assertEquals(mockTokenResponse,
                api.getDataFromApi(fakeRegion, relativePath, new HashMap<>(), TokenResponse.class));

        Mockito.verify(api, Mockito.times(1))
                .getDataFromApi(Mockito.eq(fakeRegion), Mockito.eq(relativePath),
                        Mockito.anyMap(), Mockito.eq(TokenResponse.class), Mockito.eq(Boolean.FALSE));
    }
}
