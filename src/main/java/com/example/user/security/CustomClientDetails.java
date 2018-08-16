package com.example.user.security;

import com.example.user.entity.user.Oauth2Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * clientDetails
 */
public class CustomClientDetails extends Oauth2Client implements org.springframework.security.oauth2.provider.ClientDetails {

    public CustomClientDetails(Oauth2Client oauth2Client) {
        super(oauth2Client);
    }

    @Override
    public String getClientId() {
        return super.getClientId();
    }

    @Override
    public Set<String> getResourceIds() {
        return null;
    }

    @Override
    public boolean isSecretRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return null;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        Set<String> set = new HashSet<>();
        set.add("Creams-WhatFuckCodeCanIGiveHere?");
        return set;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return getGrantTypes();
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("FOO_READ", "FOO_WRITE");
    }

    @Override
    public boolean isAutoApprove(String scope) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
