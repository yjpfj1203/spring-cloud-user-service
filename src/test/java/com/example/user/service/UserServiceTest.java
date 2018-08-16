package com.example.user.service; 

import com.example.user.controller.user.request.UserRequest;
import com.example.user.dao.user.UserDao;
import com.example.user.entity.user.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.After; 
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static io.github.benas.randombeans.api.EnhancedRandom.random;
import static io.github.benas.randombeans.api.EnhancedRandom.randomListOf;
import static io.github.benas.randombeans.api.EnhancedRandom.randomStreamOf;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/** 
 * UserService Tester. 
 * 此为自动生成的文件，引下类即可
 * @author liuhan 
 * @since Aug 16, 2018
 * @version 1.0 
 */ 
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest { 
    @InjectMocks
    @Spy   //如果待测试的方法调用的本类其他方法，则需要添加此注解
    private UserService userService;
    @Mock
    private UserDao userDao;
    @Mock
    private PasswordEncoder passwordEncoder;

    /**
     *  方法执行前执行
     * @throws Exception
     */
    @Before
    public void before() throws Exception {
    }

    /**
     * 方法结束后执行
     * @throws Exception
     */
    @After
    public void after() throws Exception {
    }
    
    /** 
     * @author liuhan 
     * @since Aug 16, 2018
     * Method: saveUser(UserRequest userRequest) 
     */ 
    @Test
    public void testSaveUser() {
        UserRequest userRequest = random(UserRequest.class);
        /**
         * 如果调用了passwordEncoder.encode(userRequest.getPassword())
         * 则，返回一个随机字符串，而不会返回真实的加密结果。
         * 因为是单元测试，我们不会对加密过程和结果进行验证。并且默认这个方法是对的，可以返回正确结果。
         */
        String encodedPwd = random(String.class);
        when(passwordEncoder.encode(userRequest.getPassword())).thenReturn(encodedPwd);
        User user = User.DoFromRequestForm(userRequest, encodedPwd);
        //这个是返回值为void的写法，user也可写成any()，意为保存任意用户
        doNothing().when(userDao).insertUser(user);
        //调用要测试的方法
        userService.saveUser(userRequest); //这个返回值无法获取到，因为dao会将userId封装到user中

    }

    /** 
     * @author liuhan 
     * @since Aug 16, 2018
     * Method: findUserByEmailOrTel(String username) 
     */ 
    @Test
    public void testFindUserByEmailOrTel() { 
        
    } 
    
    /** 
     * @author liuhan 
     * @since Aug 16, 2018
     * Method: findById(Long id) 
     */ 
    @Test
    public void testFindById() { 
        
    } 
    
    /** 
     * @author liuhan 
     * @since Aug 16, 2018
     * Method: updateUser(Long id, UserUpdateRequest userUpdateRequest) 
     */ 
    @Test
    public void testUpdateUser() { 
        
    } 
    
        
} 
