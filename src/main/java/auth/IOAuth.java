package auth;

public interface IOAuth {
    String getAccessToken(boolean testing);
    boolean isTokenInvalid();
}
