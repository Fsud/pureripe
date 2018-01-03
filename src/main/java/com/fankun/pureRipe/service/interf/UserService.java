package com.fankun.pureRipe.service.interf;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fankun.pureRipe.entity.User;

/**
 * Created by fankun on 2017/1/19.
 */
public interface UserService {
    User getUserById(Long userId);

    User getUserByUserName(String userName);

    void initCache();

    String getTime();

    List<String> listRecentSignUp();

    String checkUser(User user);

    User getUserByEmail(String email);

    void join(User user);

    void springSecuritySetSession(HttpServletRequest request, User user);
}
