package com.fankun.pureRipe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import com.fankun.pureRipe.security.MyUser;

/**
 * Created by fankun on 2017/6/9.
 */
public class BaseController {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    public MyUser getMyUser() {
        MyUser micUser = null;
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal != null) {
                if (principal instanceof UserDetails) {
                    micUser = (MyUser) ((UserDetails) principal);
                }
                else {
                    micUser = null;
                }
            }
            return micUser;
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public void addMyUser(Model model){
        model.addAttribute("myUser",getMyUser());
    }
}
