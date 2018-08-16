package com.example.user.config;

import com.example.user.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;


/**
 * securityConfiguration
 */
@Configuration
/**
 * @EnableGlobalMethodSecurity这个注解，整个项目只可以有一个，在MethodSecurityConfig中有一个
 */
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private PasswordAuthenticationProvider passwordAuthenticationProvider;

    @Autowired
    private VerificationCodeAuthenticationProvider verificationCodeAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .authenticationProvider(verificationCodeAuthenticationProvider).userDetailsService(customUserDetailsService)
//                .and()
                .authenticationProvider(passwordAuthenticationProvider).userDetailsService(customUserDetailsService).passwordEncoder(passwordencoder());
    }

    /**
     * 配置autheticationManagerBean
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 这个配置和resourceServerConfiguration配置有什么关系，目前没去了解
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and().authorizeRequests().anyRequest().permitAll()
                .and()
                .httpBasic();
    }



    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }


}
