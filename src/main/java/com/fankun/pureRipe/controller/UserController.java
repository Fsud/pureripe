package com.fankun.pureRipe.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fankun.pureRipe.entity.User;
import com.fankun.pureRipe.security.MyUser;
import com.fankun.pureRipe.service.interf.UserService;

/**
 * Created by fankun on 2017/6/12.
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping(value="/login",method=RequestMethod.GET)
    public String login(HttpServletRequest request,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth instanceof AnonymousAuthenticationToken){
            AuthenticationException exception =
                    (AuthenticationException) request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
            if (exception != null) {
                if (exception instanceof BadCredentialsException) {
                    model.addAttribute("loginError", "Incorrect Member ID/Email or Password (case-sensitive).");
                }
                else if (exception instanceof LockedException) {
                    model.addAttribute("loginError", "This account is temporarily closed by Made-in-China.com.");
                }
                else {
                    if (exception.getMessage().equals("Please input correct Verification Code.")) {
                        model.addAttribute("loginError", exception.getMessage());
                    }
                    else {
                        model.addAttribute("loginError", "Incorrect Member ID/Email or Password (case-sensitive).");
                    }
                }
            }
            return "/user/login";
        }else{
            MyUser myUser = (MyUser)((UserDetails)auth.getPrincipal());
            return "redirect:/hello";
        }
    }

    @RequestMapping(value="/login",method= RequestMethod.POST)
    public String loginP() {
        return "success";
    }

    @RequestMapping(value="/success",method=RequestMethod.GET)
    public String success() {
        return "success";
    }

    @RequestMapping(value="/join",method=RequestMethod.GET)
    public String join() {
        return "user/join";
    }

    @RequestMapping(value="/join",method=RequestMethod.POST)
    public String joinP(Model model, User user, HttpServletRequest request) {
        String message = userService.checkUser(user);
        if(StringUtils.isNotBlank(message)){
            model.addAttribute("message",message);
            return "user/joinFail";
        }
        user.setPassword(new Md5PasswordEncoder().encodePassword(user.getPassword(),null));
        userService.join(user);
        userService.springSecuritySetSession(request,user);
        return "user/joinSuccess";
    }


    @RequestMapping(value="/join/emailCheck",method=RequestMethod.GET)
    @ResponseBody
    public boolean emailCheck(String email){
        return userService.getUserByEmail(email) == null;
    }

    @RequestMapping(value="/join/userNameCheck",method=RequestMethod.GET)
    @ResponseBody
    public boolean userNameCheck(String userName){
        return userService.getUserByUserName(userName) == null;
    }
}
