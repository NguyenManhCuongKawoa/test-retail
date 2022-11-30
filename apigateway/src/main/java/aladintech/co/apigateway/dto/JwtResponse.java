package aladintech.co.apigateway.dto;


import java.util.Date;

public class JwtResponse {
	 
    private String accessToken;


    private String tokenType;


    public JwtResponse() {}
    
    public JwtResponse(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer ";
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
