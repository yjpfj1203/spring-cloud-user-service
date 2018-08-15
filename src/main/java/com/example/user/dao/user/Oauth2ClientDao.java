package com.example.user.dao.user;

import com.example.user.entity.user.Oauth2Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface Oauth2ClientDao {
    Oauth2Client findByClientId(@Param(value = "clientId") String clientId);
}
