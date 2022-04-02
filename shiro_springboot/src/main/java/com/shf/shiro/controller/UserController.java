package com.shf.shiro.controller;

import com.shf.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

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
//        2.调用subject进行登录
            subject.login(upToken);
            return "登录成功";
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
