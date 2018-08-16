package com.example.user.security;


import com.example.user.controller.model.CurrentUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 * 解析token用的配置
 */
public class CustomUserTokenConverter implements UserAuthenticationConverter {

    private Collection<? extends GrantedAuthority> defaultAuthorities;

    @SuppressWarnings("unused")
    private UserDetailsService userDetailsService;

    /**
     * Optional {@link UserDetailsService} to use when extracting an {@link Authentication} from the incoming map.
     *
     * @param userDetailsService the userDetailsService to set
     */
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Default value for authorities if an Authentication is being created and the input has no data for authorities.
     * Note that unless this property is set, the default Authentication created by {@link #extractAuthentication(Map)}
     * will be unauthenticated.
     *
     * @param defaultAuthorities the defaultAuthorities to set. Default null.
     */
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                .arrayToCommaDelimitedString(defaultAuthorities));
    }

    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }


    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            CurrentUser user = extract(map);
            return new UsernamePasswordAuthenticationToken(user, "N/A", authorities);
        }
        return null;
    }

    /**
     * 获取token中的authorities
     * 这里面有两个类型，一个是permission，一个是role
     * 1、role：必须在前面添加"ROLE_"，比如，是ADMIN权限，添加到authorities中为"ROLE_ADMIN"，方可用hasAuthorize(hasRole('ADMIN'));
     * 2、permission：可用hasAuthorize(hasAuthority('USER_DELETE') 或是 hasAuthorize(hasAuthority('ROLE_ADD')
     * @param map
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }

    /**
     * 将token转换为currentUser
     * @param map
     * @return
     */
    private CurrentUser extract(Map<String, ?> map){
        HashMap<String, Object> userMap = (HashMap<String, Object>) map.get("user");

        CurrentUser user = new CurrentUser();
        if (userMap.containsKey("id")) {
            user.setId(Long.parseLong(userMap.get("id").toString()));
        }
        if (userMap.containsKey("email")) {
            user.setEmail(String.valueOf(userMap.get("email")));
        }
        if (userMap.containsKey("name")) {
            user.setName(String.valueOf(userMap.get("name")));
        }
        if (userMap.containsKey("tel")) {
            user.setTel(String.valueOf(userMap.get("tel")));
        }
        return user;
    }
}
