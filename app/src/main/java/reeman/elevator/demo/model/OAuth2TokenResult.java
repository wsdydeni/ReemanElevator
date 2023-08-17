package reeman.elevator.demo.model;

import com.google.gson.annotations.SerializedName;


public final class OAuth2TokenResult {

    @SerializedName("access_token")
    private final String accessToken;
    @SerializedName("expires_in")
    private final int expiresIn;
    @SerializedName("scope")
    private final String scope;
    @SerializedName("token_type")
    private final String tokenType;

    public String getAccessToken() {
        return this.accessToken;
    }

    public int getExpiresIn() {
        return this.expiresIn;
    }

    public String getScope() {
        return this.scope;
    }

    public String getTokenType() {
        return this.tokenType;
    }

    public OAuth2TokenResult(String accessToken, int expiresIn, String scope, String tokenType) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.tokenType = tokenType;
    }
}
