package auth;

public class TokenResponse {
    /***
     * These parameters are written like these so it can be
     * serilized with json
     */
    private String access_token;
    private String token_type;
    private Long expires_in;

    public TokenResponse() {
    }

    public TokenResponse(String acessToken, String tokenType, Long expiresIn) {
        this.access_token = acessToken;
        this.token_type = tokenType;
        this.expires_in = expiresIn;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }
}
