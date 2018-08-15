package com.example.user.config;

import com.example.user.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yjpfj1203
 * oauth2配置中心
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomClientDetailsService customClientDetailsService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private CustomUserTokenEnhancer jwtTokenEnhancer;


    /**
     * 配置clientId的获取服务
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customClientDetailsService);
    }

    /**
     * 配置endPoints
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())                             //配置tokenStore，jwt
                .tokenEnhancer(jwtTokenEnhancer)                      //配置tokenEnhancer，封装token内部数据
                .reuseRefreshTokens(false)                            //token是否可刷新
                .tokenGranter(customTokenGranter(endpoints))          //
                .exceptionTranslator(customExceptionTranslator())     //自定义异常处理
                .authenticationManager(authenticationManager)         //定义authenticationManager
                .userDetailsService(customUserDetailsService)         //配置登录登录服务
                .setClientDetailsService(customClientDetailsService)  //定义配置clientId的获取服务
        ;
    }

    /**
     * 设置token的存储方式
     * @return
     */
    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer);
    }

    /**
     * 设置加密工具
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .passwordEncoder(passwordEncoder);
    }

    /**
     * 设置jwt的公钥，生成jwt.jks放在resource下，因为是userService对token的加密服务，所以不需要放公钥
     * 如果仅仅是解密，无需加密，那么需要加载公钥，公钥可放在公共配置里
     * 生成jwt.jks的方式如下
     * keytool -genkeypair -alias liuhan -keyalg RSA  -keypass yjpfj1203  -keystore jwt.jks  -storepass yjpfj1203
     * keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
     * @param jwtFileName
     * @return
     */
    @Bean
    protected CustomUserTokenEnhancer jwtTokenEnhancer(@Value("${web.jwtFileName}") String jwtFileName) {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource(jwtFileName), "yjpfj1203".toCharArray());
        CustomUserTokenEnhancer converter = new CustomUserTokenEnhancer();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
        return converter;
    }

    /**
     * 获取所有OAuthException，拼装Response.
     * @return
     */
    @Bean
    protected WebResponseExceptionTranslator customExceptionTranslator() {
        return (e) -> {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
//            if (e.getCause() instanceof LockedException) {
//                return new ResponseEntity(new ErrorResponse("账户未激活，请联系管理员", e), HttpStatus.UNAUTHORIZED);
//            }
//            if (e instanceof InvalidTokenException) {
//                return new ResponseEntity(new ErrorResponse("invalid_token", e), HttpStatus.UNAUTHORIZED);
//            }
//            return new ResponseEntity(new ErrorResponse("用户名密码或验证码不正确", e), HttpStatus.UNAUTHORIZED);
        };
    }

    /**
     * 他妈的自定义一个TOKENGranter太麻烦了，如果区endpoints里面的Granter,会把自定义的覆盖掉，如果自己光加一个，取client的时候就会出错。
     * @param endpoints
     * @return
     */
    private TokenGranter customTokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>();
        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(customClientDetailsService);
        granters.add(new CustomResourceOwnerPasswordTokenGranter(authenticationManager, endpoints.getTokenServices(), customClientDetailsService, requestFactory));
        //第三方登录的方式进行授权
        granters.add(new AuthorizationCodeTokenGranter(endpoints.getTokenServices(), endpoints.getAuthorizationCodeServices(), customClientDetailsService, requestFactory));
        granters.add(new RefreshTokenGranter(endpoints.getTokenServices(), customClientDetailsService, requestFactory));
        //客户端模式，客户端以自己的名义申请服务
        granters.add(new ClientCredentialsTokenGranter(endpoints.getTokenServices(), customClientDetailsService, requestFactory));
        return new CompositeTokenGranter(granters);
    }
}
