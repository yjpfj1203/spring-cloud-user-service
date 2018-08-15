package com.example.user.security;

import com.example.user.entity.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by frank on 07/07/2016.
 */
public class CustomUserDetails extends User implements org.springframework.security.core.userdetails.UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(new String[]{});
    }

    public CustomUserDetails(User user) {
        super(user);
    }

    @Override
    public String getUsername() {
        if (StringUtils.isNotBlank(getEmail())) {
            return getEmail();
        } else if (StringUtils.isNotBlank(getTel())) {
            return getTel();
        } else {
            return String.valueOf(getId());
        }
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return super.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return super.isActive();
    }

}
