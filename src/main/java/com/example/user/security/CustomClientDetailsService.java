package com.example.user.security;

import com.example.user.dao.user.Oauth2ClientDao;
import com.example.user.entity.user.Oauth2Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * clientDetailsService
 */
@Service
public class CustomClientDetailsService implements ClientDetailsService {

    @Autowired
    private Oauth2ClientDao oauth2ClientDao;

    @Override
    public ClientDetails loadClientByClientId(String clientId) {
        //oauth2 client 存在数据库中是name
        Oauth2Client oauth2Clients = oauth2ClientDao.findByClientId(clientId);
        return new CustomClientDetails(oauth2Clients);
    }
}
