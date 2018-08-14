# spring-cloud-user-service
spring cloud user service

知识点：<br>
一：jwt+oauth2配置(参考项目中使用代码完成)<br>
二：redis缓存存储 参考：https://www.cnblogs.com/yanduanduan/p/6552861.html(这个内容挺全的)<br>
三：自定义权限注释 参考见：https://www.baeldung.com/spring-security-create-new-custom-security-expression<br>
四：其他知识点<br>
   flyway维护表结构更新(这个有问题，flyway的表结构更新在mybatis验证表结构之后)<br>
   lombok精简实体类结构<br>
```Java
public static void main(String[] args){}
```
这个用于之后搭建spring cloud的用户系统，目前刚开始。还没想太清楚写个什么demo项目，会将自己了解到的相关知识点慢慢整合进这个项目中。<br>

先了解下这个结构，<br>
* config：配置文件所在目录<br>
  * 1)：CustomPermissionEvaluator：重载PermissionEvaluator，这里也可以自定义hasPermission方法；<br> 
    2)：CustomMethodSecurityExpressionHandler：装载自定义的权限注解;<br>
    3)：CustomMethodSecurityExpressionRoot：权限注解主体，重载spring自带的权限注解；<br>
    4)：MethodSecurityConfig：配置文件，主要配置permissionEvaluator和expressionHandler。之前有个配置写错，搞了差不多一个星期，@EnableGlobalMethodSecurity(prePostEnabled = true)这个注解全文只可以有一个，如果有两个，会导致其中一个配置文件失效；<br>
    注：这四个是配置权限注解(@PreAuthorize())，详见：https://www.baeldung.com/spring-security-create-new-custom-security-expression<br>
  * InitRolePermissionBean：初始化redis中的角色与权限的关系，载入系统所有的角色及权限，用于CustomMethodSecurityExpressionRoot在获取用户角色时，判断用户所拥有的权限。
constant：<br>
controller：<br>
dao：<br>
entity：<br>
security：<br>
util：<br>

大标题
====

中标题
-------


#一级标题
##二级标题
###三级标题
####四级标题
#####五级标题
######六级标题

>缩进一
>>缩进二
>>>缩进三
>>>>缩进四
>>>>>缩进五

* 列表一
    * 列表二
        * 列表三
        
