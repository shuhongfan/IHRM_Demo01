package com.shf.shiro;

import com.shf.shiro.realm.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfiguration {
//    1.创建realm
    @Bean
    public CustomRealm getRealm() {
        return new CustomRealm();
    }

//    2.创建安全管理器
    @Bean
    public SecurityManager getSecurityManager(CustomRealm customRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(customRealm);
        return securityManager;
    }

//    3.配置shiro的过滤器工厂
    /**
     * 在web程序中，shiro进行权限控制全部是通过一组过滤器集合进行控制
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
//        1.创建过滤器工厂
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
//        2.设置安全管理器
        factoryBean.setSecurityManager(securityManager);
//        3.通用配置(跳转登录页面,为授权跳转的页面)
        factoryBean.setLoginUrl("/autherror?code=1"); // 跳转url地址
        factoryBean.setUnauthorizedUrl("/autherror?code=2");
//        4.设置过滤器集合
        /**
         * 设置所有的过滤器，有顺序map
         *      key=拦截url的地址
         *      value=过滤器类型
         */
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        //        使用过滤器的形式配置请求地址的依赖权限
        filterMap.put("/user/home","perms[user-home]"); //具有某种权限才能访问，跳转到setUnauthorizedUrl


//        filterMap.put("/user/home", "anon"); // 当前请求地址，可以匿名访问
        filterMap.put("/user/**", "authc"); // 当期请求地址必须认证之后才可以访问


        factoryBean.setFilterChainDefinitionMap(filterMap);
        return factoryBean;
    }

    //4.配置shiro注解支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
