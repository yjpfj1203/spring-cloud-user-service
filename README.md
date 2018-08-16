# spring-cloud-user-service
spring cloud user service

user-service用户系统
====

这个用于之后搭建spring cloud的用户系统，目前刚开始。还没想太清楚写个什么demo项目，会将相关知识点慢慢整合进这个项目中。<br>

要点：<br>
一：jwt+oauth2配置(参考项目中使用代码完成)<br>
二：redis缓存存储 参考：https://www.cnblogs.com/yanduanduan/p/6552861.html(这个内容挺全的)<br>
三：自定义权限注解(custom security expression) 参考见：https://www.baeldung.com/spring-security-create-new-custom-security-expression<br>

先了解下这个结构，<br>
* config：配置文件所在目录<br>
  * 1)：CustomPermissionEvaluator：重载PermissionEvaluator，这里也可以自定义hasPermission方法；<br> 
    2)：CustomMethodSecurityExpressionHandler：装载自定义的权限注解;<br>
    3)：CustomMethodSecurityExpressionRoot：权限注解主体，重载spring自带的权限注解；<br>
    4)：MethodSecurityConfig：配置文件，主要配置permissionEvaluator和expressionHandler。之前有个配置写错，搞了差不多一个星期，@EnableGlobalMethodSecurity(prePostEnabled = true)这个注解全文只可以有一个，如果有两个，会导致其中一个配置文件失效；<br>
    注：这四个是配置权限注解(@PreAuthorize())，详见：https://www.baeldung.com/spring-security-create-new-custom-security-expression<br>
  * InitRolePermissionBean：初始化redis中的角色与权限的关系，载入系统所有的角色及权限，用于CustomMethodSecurityExpressionRoot在获取用户角色时，判断用户所拥有的权限。<br>
  * 其他配置就不缀述了，有很多资料可查，且在代码中也有明确的注释
* security：clientId与grantType判断，用户登录，token封装与解析等配置
  * ClientDetails(Service)，重写spring自带的clientDetails(Service)，查询的就是entity下的Oauth2Client及grantType<br>
  * userDetails(Service)，自定义user登录验证
  * tokenConverter，从token中获取权限列表及用户数据
  * tokenEnhancer，将用户数据及用户权限封装入token中
* 配置文件目前写在bootstrap.yml中<br>

代码结构至此大致介绍结束，其他的东西大家都能秒懂。<br>
<br>
项目运行准备：<br>
1：创建数据库，但别忘记改数据库配置<br>
2：将db.migration下面文件中的sql拷贝到mysql中执行一次，初始化数据。执行完成后项目启动会报flyway的错误，这时把数据库上schema_version中的字段success设置为true即可。<br>
3：安装redis服务器。<br>
<br>
项目启动<br>
 下面是几个请求及应该出现的结果(postman测试，没有前端页面)<br>
 1: spring登录接口：post http://localhost:8888/oauth/token?client_id=web-demo&password=master12345&username=master@web-demo.com&grant_type=password<br>
   返回结果：json字符串，比较长，不在此处显示<br>

 2: 自定义登录接口：post http://localhost:8888/user/login requestBody为<br>
  ```Java
  {
	  "username": "master@web-demo.com",
	  "password": "master12345"
  }
  ```
返回结果:
```java
{
    "accessToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MzQzODc3NTUsInVzZXIiOnsiaWQiOjEsImVtYWlsIjoibWFzdGVyQHdlYi1kZW1vLmNvbSIsIm5hbWUiOiJhZG1pbmlzdHJhdG9yIiwidGVsIjpudWxsLCJ3ZWNoYXROdW1iZXIiOm51bGx9LCJ1c2VyX25hbWUiOiJtYXN0ZXJAd2ViLWRlbW8uY29tIiwianRpIjoiMTJiMzg2ZWYtYmU0Mi00NGFlLTgxZjctZGMyYjZiMTM2OTg0IiwiY2xpZW50X2lkIjoid2ViLWRlbW8iLCJzY29wZSI6WyJDcmVhbXMtV2hhdEZ1Y2tDb2RlQ2FuSUdpdmVIZXJlPyJdfQ.hrE_YHzagkiyL-lfXFfZeQicdqD0o26pkm5dTwV-ij8gqp1-AuohuPaS5kRKuAdGnTz87xPK_JYNyo-MP1jPGGVirdgvfEo8GXCDH8ylCROdCyNMIVHdlz15wPVyy9m303YaFvxmlLZmB9gaNBMi1J7OmSdTnDQlVA_fM3WNvMTzCofWL-rds2hiHSSESv4Oz8MeaH5i1ZmcNZEGwFN8ph0ymsSpgeXly1znfTjonGombJVFcWU_O_0mHIo1beWNg-8ptTU0gvanAnPL8pRRWOefIwH5cSTmEbjy2t9GjhS9pP2UsZwgMFnO2rjaaLsynpidTvBLna8mKw5ZMWgk_g",
    "refreshToken": "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJtYXN0ZXJAd2ViLWRlbW8uY29tIiwic2NvcGUiOlsiQ3JlYW1zLVdoYXRGdWNrQ29kZUNhbklHaXZlSGVyZT8iXSwiYXRpIjoiMTJiMzg2ZWYtYmU0Mi00NGFlLTgxZjctZGMyYjZiMTM2OTg0IiwiZXhwIjoxNTM0MzkxMzU1LCJ1c2VyIjp7ImlkIjoxLCJlbWFpbCI6Im1hc3RlckB3ZWItZGVtby5jb20iLCJuYW1lIjoiYWRtaW5pc3RyYXRvciIsInRlbCI6bnVsbCwid2VjaGF0TnVtYmVyIjpudWxsfSwianRpIjoiNGNhMTUxOTctYjg0Yi00YjRhLTg0NDgtZTI0YWU2YWUwOTI5IiwiY2xpZW50X2lkIjoid2ViLWRlbW8ifQ.N95LEtyuwJRocAos32fzDrZFf-JfzQxvrcPyB0ErOQ6fGXmEzZYuwTX2u1wVuMrhVNeEs_PhBg1ZWj_FuLCMdjrG_jFyLKNE4wNDQ5KBXsR4dNmFw2fmfOdjnpQEQB-n5IVWn7QK2-XYWpcC9Y82ZMTTL9jz7-PD8P5I1Up0GdBHwMUk2WvlIOKefuw1rp5ZyaHaCSlBe1v7Z90egW1hK57FgB8O_qPAYcGQIH7t5BgfF7PZs-PEYgJweS1s-bS4pxbNtn0QD6kDu6j_mudD-bgYHx2NA2uvlE2JriiyTiY40sYnPB_hH6HxsylwXXHd1HsWqj3aC86Y90YmkCwAlA"
}
```

3：用户查询权限验证，带上token，header添加"Authorization：Bearer 生成的accessToken"，请求连接、http://localhost:8888/users<br>
返回结果：用户列表<br>


4：部门查询权限验证，token同上，请求连接、http://localhost:8888/departments<br>
返回结果：access_denied<br>
5：无token验证，不带token，请求连接、http://localhost:8888/users/test<br>
返回结果：无token验证OK<br>
6：自定义isSelf权限验证<br>
连接1：http://localhost:8888/users/self/1   返回结果：你是当前登录者。<br>
连接2：http://localhost:8888/users/self/1   返回结果：access_denied


        
