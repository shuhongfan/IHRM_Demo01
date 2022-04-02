package com.shf.shiro.realm;

import com.shf.shiro.domain.Permission;
import com.shf.shiro.domain.Role;
import com.shf.shiro.domain.User;
import com.shf.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.util.HashSet;

import static java.awt.SystemColor.info;

/**
 * 自定义的realm
 */
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    @Override
    public void setName(String name) {
        super.setName("CustomRealm");
    }

    /**
     * 授权方法
     *      操作的时候，判断用户是否具有响应的权限
     *             先认证---安全数据
     *             再授权---根据安全数据获取用户具有的所有操作权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        1.获取已认证的用户数据  得到唯一的安全数据
        User user = (User) principalCollection.getPrimaryPrincipal();
//        2.根据用户数据获取用户的权限信息(所有角色,所有权限)
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

//        所有角色
        HashSet<String> roles = new HashSet<>();
//        所有权限
        HashSet<String> perms = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(role.getName());
            for (Permission permission : role.getPermissions()) {
                perms.add(permission.getCode());
            }
            info.setStringPermissions(perms);
            info.setRoles(roles);
        }
        return info;
    }

    /**
     * 认证方法
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        1.获取登录的用户名密码（token）
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());

//        2.根据用户名查询数据库
        User user = userService.findByName(username);
//        3.判断用户是否存在或者密码是否一致
        if (user!=null && user.getPassword().equals(password)){
//        4.如果一致返回安全数据
//            构造方法，安全数据,密码,realm域名
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
            return info;
        }
//        5.不一致,返回null
        return null;
    }
}
