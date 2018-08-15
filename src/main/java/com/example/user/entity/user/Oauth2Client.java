package com.example.user.entity.user;

import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Steven on 2016-07-19.
 */
@NoArgsConstructor
public class Oauth2Client {

    private Long id;

    private String clientId;

    private Integer accessTokenValiditySeconds;

    private Integer refreshTokenValiditySeconds;

    private Set<String> grantTypes;

    public Oauth2Client(Oauth2Client oauth2Client) {
        this.clientId = oauth2Client.getClientId();
        this.accessTokenValiditySeconds = oauth2Client.getAccessTokenValiditySeconds();
        this.refreshTokenValiditySeconds = oauth2Client.getRefreshTokenValiditySeconds();
        this.grantTypes = oauth2Client.getGrantTypes();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public Set<String> getGrantTypes() {
        if (null == grantTypes) {
            this.grantTypes = new HashSet<>();
        }
        return grantTypes;
    }

    public void setGrantTypes(Set<String> grantTypes) {
        this.grantTypes = grantTypes;
    }
}
