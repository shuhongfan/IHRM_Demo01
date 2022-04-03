package com.shf.shiro.controller;

import com.shf.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.Enumeration;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

//    使用shiro注解鉴权
//    @RequiresPermissions()  访问方法必须具备的权限
//    @RequiresRoles()  访问方法必须具备的角色

    /**
     * 1.过滤器，如果权限信息不匹配setUnauthorizedUrl地址
     * 2.注解：如果权限信息不匹配，抛出异常
     * @return
     */
    @RequiresPermissions("user-home")
    @RequestMapping(value = "/user/home")
    public String home(){
        return "访问个人主页成功";
    }

//    登录成功之后,打印所有session内容
    @RequestMapping(value = "/show")
    public String show(HttpSession session){
//        获取session中所有的键值
        Enumeration<String> enumeration = session.getAttributeNames();
//        遍历键值取出session中的值
        while (enumeration.hasMoreElements()) {
//            获取session的所有的键值
            String name = enumeration.nextElement().toString();
//            根据键值取session的值
            Object value = session.getAttribute(name);
//            打印结果
            System.out.println(name+"======="+value);
        }
        return "查询session成功";
    }



    //添加
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public String add() {
        return "添加用户成功";
    }
	
    //查询
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public String find() {
        return "查询用户成功";
    }
	
    //更新
    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String update(String id) {
        return "更新用户成功";
    }
	
    //删除
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE)
    public String delete() {
        return "删除用户成功";
    }
	
	//用户登录
	@RequestMapping(value="/login")
    public String login(String username,String password) {
		System.out.println("用户登录");

        try {
            /**
             * 密码加密：
             *      shiro提供的md5加密
             *      Md5Hash：
             *          参数一：加密的内容
             *                  1111111
             *          参数二：盐（加密的混淆字符串）（用户登录的用户名）
             *          参数三：加密次数
             */
//            password = new Md5Hash(password,username,3).toString();

//        构造登录令牌
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
//        1.获取subject
            Subject subject = SecurityUtils.getSubject();
//            ==========================
//            获取session
            String sid = (String) subject.getSession().getId();
//            =============================
//        2.调用subject进行登录
            subject.login(upToken);
            return "登录成功"+sid;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "用户名或密码错误";
        }
    }

    @RequestMapping(value = "/autherror")
    public String autherror(int code){
        return code==1?"未登录":"未授权";
    }
}
