package com.example.user.config;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author yjpfj1203
 * 重载默认SecurityExpressionHandler
 */
public class CustomMethodSecurityExpressionHandler extends DefaultMethodSecurityExpressionHandler {

    /**
     * 载入自定义的权限注解
     * @param authentication
     * @param invocation
     * @return
     */
    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            Authentication authentication, MethodInvocation invocation) {
        final CustomMethodSecurityExpressionRoot root =
                new CustomMethodSecurityExpressionRoot(authentication);
        root.setThis(invocation.getThis());
        root.setPermissionEvaluator(this.getPermissionEvaluator());
        root.setTrustResolver(this.getTrustResolver());
        root.setRoleHierarchy(this.getRoleHierarchy());
        root.setDefaultRolePrefix(this.getDefaultRolePrefix());
        return root;

    }
}

