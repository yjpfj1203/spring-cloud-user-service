package com.example.user.config;

import com.example.user.controller.model.CurrentUser;
import com.example.user.dao.OperatorDao;
import com.example.user.entity.user.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by Steven on 2017/7/18.
 */
@Configuration
public class UserAuditorBean implements AuditorAware<Operator> {
    @Autowired
    private OperatorDao operatorDao;

    @Override
    @Transactional
    public Optional<Operator> getCurrentAuditor() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx == null
                || ctx.getAuthentication() == null
                || ctx.getAuthentication().getPrincipal() == null) {
            return Optional.empty();
        }
        Object principal = ctx.getAuthentication().getPrincipal();
        if (principal.getClass().isAssignableFrom(CurrentUser.class)) {
            CurrentUser currentUser = (CurrentUser) principal;
            Operator operator = operatorDao.findById(currentUser.getId());
            if (operator == null){
                Operator optor = new Operator();
                optor.setId(currentUser.getId());
                optor.setName(currentUser.getName());
                operatorDao.save(optor);
            }
            return Optional.of(operatorDao.findById(currentUser.getId()));
        } else {
            return Optional.empty();
        }
    }
}
