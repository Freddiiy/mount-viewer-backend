package auth;

public interface IOAuth {
    String getAccessToken();
    boolean isTokenInvalid();
}
