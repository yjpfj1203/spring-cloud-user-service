package com.example.user.security;

import com.example.user.constants.RedisPrefixConstant;
import com.example.user.util.RedisService;
import com.example.user.controller.model.CurrentUser;
import com.example.user.dao.user.UserRoleDao;
import com.example.user.entity.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by frank on 01/08/2016.
 */
public class CustomUserTokenEnhancer extends JwtAccessTokenConverter {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UserRoleDao userRoleDao;

    /**
     * 在token中添加用户信息，生成token的时候调用
     * @param accessToken
     * @param authentication
     * @return
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        CurrentUser currentUser = new CurrentUser();
        currentUser.setEmail(user.getEmail());
        currentUser.setId(user.getId());
        currentUser.setName(user.getName());
        currentUser.setTel(user.getTel());
        currentUser.setWechatNumber(user.getWechatNumber());

        additionalInfo.put("user", currentUser);
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        OAuth2AccessToken accessTokenFinal = super.enhance(accessToken, authentication);

        List<UserRole> userRoleList = userRoleDao.findByUserId(currentUser.getId());
        redisService.del(RedisPrefixConstant.USER_TOKEN_PREMISSIONS + user.getId() + "_ROLE");
        redisService.setPut(RedisPrefixConstant.USER_TOKEN_PREMISSIONS + user.getId() + "_ROLE",
                userRoleList.stream().map(ur -> ur.getRoleId().toString()).collect(Collectors.toList()).toArray());
        return accessTokenFinal;
    }

    public CustomUserTokenEnhancer() {
        DefaultAccessTokenConverter defaultAccessTokenConverter = (DefaultAccessTokenConverter) this.getAccessTokenConverter();
        defaultAccessTokenConverter.setUserTokenConverter(new CustomUserTokenConverter());
    }

}
