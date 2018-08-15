package com.example.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

/**
 * Created by Steven on 2016-07-08.
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/users/login",
                        "/users/login/oa",
                        "/users/login/refresh",
                        "/users/application",
                        "/users/password/replacement",
                        "/users/verification-code/application",
                        "/users/register",
                        "/agents/filter",
                        "/operation/news",
                        "/operation/news/{id}",
                        "/demands/sources",
                        "/commons/regions",
                        "/commons/regions/block",
                        "/commons/companies",
                        "/alipay/callback",
                        "/v2/api-docs",
                        "/taikang/approval",
                        "/taikang/haiwen/invoices",
                        "/health",
                        "/piaojj/invoice/success-callback", // 票++ 开票成功回调
                        "/piaojj/invoice/fail-callback", // 票++ 开票失败回调
                        "/taikang/epay/notify",
                        "/oss/local/download"
                ).permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/swagger-ui.html").failureUrl("/login?error");
    }

}
