package com.fankun.pureRipe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fankun.pureRipe.entity.User;
import com.fankun.pureRipe.security.MyUser;
import com.fankun.pureRipe.security.SimpleAuthenticationManager;
import com.fankun.pureRipe.service.interf.UserService;

/**
 * Created by fankun on 2017/1/19.
 */
@Controller
public class HelloSpringBootController extends  BaseController{

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    @Autowired
    SimpleAuthenticationManager simpleAuthenticationManager;

    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public String index(){
        return "welcome";
    }

    @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String sayHello(Model model){
        MyUser myUser = getMyUser();
        boolean loginFlag = false;
        if(myUser != null){
            loginFlag = true;
            model.addAttribute("name",myUser.getUserName());
        }
        model.addAttribute("loginFlag",loginFlag);
        model.addAttribute("recentSignUp",userService.listRecentSignUp());
        return "welcome";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(@PathVariable Long id){
        User user =  userService.getUserById(id);
        return user.getUserName();
    }

    @RequestMapping("/500")
    public String handle500(){
        return "/500";
    }

    @RequestMapping("/404")
    public String handle400(){
        return "/404";
    }

//    public static void main
}
