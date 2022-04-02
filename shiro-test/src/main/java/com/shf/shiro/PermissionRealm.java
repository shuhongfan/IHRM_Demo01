package com.shf.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;

/***
 * 自定义realms对象
 *      继承realms对象
 *          继承AuthorizingRealm
 *          重写方法
 *              doGetAuthorizationInfo：授权，获取到用户的授权数据（用户的权限数据）
 *              doGetAuthenticationInfo：认证，根据用户名密码登录，将用户数据保存（安全数据）
 */
public class PermissionRealm extends AuthorizingRealm {
    /**
     * 自定义Realm名称
     * @param name
     */
    @Override
    public void setName(String name) {
        super.setName(name);
    }

//    授权：授权的主要目的就是根据认证获取到的用户权限信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        1.获取安全数据 username用户id
        String username = (String) principalCollection.getPrimaryPrincipal();
//        2.根据id或者名称查询用户
//        3.查询用户的角色和权限信息
        ArrayList<String> perms = new ArrayList<>();
        perms.add("user:save");
        perms.add("user:update");

        ArrayList<String> roles = new ArrayList<>();
        roles.add("role1");
        roles.add("role2");
//        4.构造返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        设置权限集合
        info.addStringPermissions(perms);
//        设置角色集合
        info.addRoles(roles);
        return info;
    }

//    认证：认证的主要目的，比较用户名和密码是否与数据库中的一致
//    将安全数据存到shiro进行保管
//    参数：authenticationToken登录构造的usernamepasswordtoken
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        1.构造uptoken
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
//        2.获取输入的用户名和密码
        String username = upToken.getUsername();
        String password = String.valueOf(upToken.getPassword());
//        3.根据用户名查询数据库
        if ("123456".equals(password)){
//        4.比较密码和数据库中的密码是否一致(密码可能需要加密)
//        5.如果成功,向shiro存入安全数据  SimpleAuthenticationInfo(安全数据，密码，当前realm域名称)
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());
            return info;
        } else {
//        6.失败,抛出异常或返回null
            throw new RuntimeException("用户名或密码错误");
        }
    }
}
