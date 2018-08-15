package com.example.user.config;

import com.example.user.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by wu fei.
 * Date : 2017/3/27
 * Time : 下午3:21
 */
@Component
public class VerificationCodeAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

//    @Autowired
//    private VerificationCodeTelRepository verificationCodeTelRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        //Email Authentication Should not use this provider
//        if(username.contains("@")) {
//            return null;
//        }
//
//        String password = (String) authentication.getCredentials();
//        CreamsUserDetails user = (CreamsUserDetails) userDetailsService.loadUserByUsername(username);
//
//        if (user == null) {
//            throw new UsernameNotFoundException("此手机号未注册");
//        }
//
//        VerificationCodeTel code = verificationCodeTelRepository.findTop1ByTelAndCodeAndAction(username, password, VerificationCodeActionEnum.USER_TEL_LOGIN);
//
//        if (code == null || !code.getCode().equals(password)) {
//            //Provider 验证不通过return null可以再试下一个provider, 不能直接throw exception
//            return null;
//        }
//
//        //删除已使用的验证码
//        verificationCodeTelRepository.delete(code);
//
//        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//        return new UsernamePasswordAuthenticationToken(user, password, authorities);
//        return new UsernamePasswordAuthenticationToken("", "");
        return null;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
