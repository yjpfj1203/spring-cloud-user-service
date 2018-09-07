package com.example.user.config;

import com.example.user.constants.RedisPrefixConstant;
import com.example.user.dao.user.UserDao;
import com.example.user.entity.user.User;
import com.example.user.util.RedisService;
import com.example.user.entity.user.Role;
import com.example.user.entity.user.RolePermission;
import com.example.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 * 初始化redis中的角色与权限的关系，载入系统所有的角色及权限，用于CustomMethodSecurityExpressionRoot在获取用户角色时，判断用户所拥有的权限
 */
@Component
public class InitRolePermissionBean {

    @Autowired
    private RedisService redisService;
    @Autowired
    private PermissionService permissionService;

    /**
     * PostContruct，指定项目启动后执行的方法
     * 启动时执行，方式一，方法
     */
    @PostConstruct
    public void init() {
        System.out.println("init system's all roles to redis!");
        List<Role> roleList = permissionService.findAllRole();
        List<RolePermission> rolePermissionList = permissionService.findRolePermissionByRoleIds(roleList.stream().map(Role::getId).distinct().collect(Collectors.toList()));
        Map<String, Set<String>> rpMap = rolePermissionList
                .stream()
                .collect(Collectors.groupingBy(rp -> rp.getId().toString(),
                        Collectors.mapping(rpt -> rpt.getPermission().name(), Collectors.toSet())));
        Map<String, Object> map = new HashMap<>();
        rpMap.entrySet().forEach(e -> {
            map.put(e.getKey(), e.getValue());
        });

        redisService.mapPut(RedisPrefixConstant.ROLE_START_INIT, map);
    }

    /**
     * 启动时执行，方式二，类
     */
    @Component
    public static class MyCommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... var1) throws Exception{
            System.out.println("This will be execute when the project was started!");
        }
    }

    /**
     * 启动时执行，方式三，类
     * ApplicationRunner可以添加注解@order，用于指定执行顺序
     */
    @Component
    @Order(2)
    public static class MyApplicationRunner implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments var1) throws Exception{
            System.out.println("MyApplicationRunner class will be execute when the project was started!");
        }

    }

    /**
     * 启动时执行，方式四，方法
     * @param userDao
     * @return
     */
    @Bean
    CommandLineRunner commandLineRunner(UserDao userDao){

        return args -> {
            User user = userDao.findByEmailOrTel("master@web-demo.com", "");
            System.out.println("test start to execute! userId is " + user.getId());
        };
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                userDao.findByEmailOrTel("master@web-demo.com", "");
//            }
//        };
    }

}
