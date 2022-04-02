package com.shf.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

public class ShiroTest03 {
    /**
     * 测试用户认证
     *      认证：用户登录
     *      1.根据配置文件创建SecurityManagerFactory
     *      2.通过工厂获取SecurityManager
     *      3.将SecurityManager绑定到当前运行环境
     *      4.从当前运行环境中构造subject
     *      5.构造shiro登录的数据
     *      6.主体登录
     */

    private SecurityManager securityManager;

    @Before
    public void init(){
//        1.根据配置文件创建SecurityManagerFactory
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-test-3.ini");
//        2.通过工厂获取SecurityManager
        SecurityManager securityManager = factory.getInstance();
//        3.将SecurityManager绑定到当前运行环境
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void testLogin(){
//        4.从当前运行环境中构造subject
        Subject subject = SecurityUtils.getSubject();
//        5.构造shiro登录的数据
        String username="zhangsan";
        String password="123456";
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
//       执行login--》realm域中的认证方法
        subject.login(token);
//        登录成功之后,完成授权
//        授权：检验当前登录用户是否具有操作权限,是否具有某个角色
        System.out.println(subject.hasRole("role1"));
        System.out.println(subject.isPermitted("user:save"));
    }
}
