package com.example.user.config;

import com.example.user.security.CustomUserDetails;
import com.example.user.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;


/**
 * @author yjpfj1203
 * 密码登录执行的方法，用于配置在SecurityConfiguration中的AuthenticationManagerBuilder
 * 可有多个登录方法，VerificationCodeAuthenticationProvider是另外一个登录方式，手机号验证码登录(未完成)
 * 多个登录方式会依次执行，只到有一个登录方式可以登录为止，如果都没登录成功，则登录失败。
 */
@Component
public class PasswordAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 帐号密码登录
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        //手机号登陆不应该运行此provider
        if (!username.contains("@")){
            return null;
        }
        String password = (String) authentication.getCredentials();
        CustomUserDetails user = (CustomUserDetails) userDetailsService.loadUserByUsername(username);

        if (user == null) {
            throw new BadCredentialsException("Username not found.");
        }


//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        //验证用户登录密码是否正确
//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            return null;
//        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
