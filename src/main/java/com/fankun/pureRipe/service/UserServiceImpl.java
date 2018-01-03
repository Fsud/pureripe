package com.fankun.pureRipe.service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;

import com.fankun.pureRipe.dao.UserMapper;
import com.fankun.pureRipe.entity.SIGN_UP_ERROR_ENUM;
import com.fankun.pureRipe.entity.User;
import com.fankun.pureRipe.security.MyUserDetailsService;
import com.fankun.pureRipe.security.SimpleAuthenticationManager;
import com.fankun.pureRipe.service.interf.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

/**
 * Created by fankun on 2017/1/19.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    SimpleAuthenticationManager simpleAuthenticationManager;

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    private LoadingCache<String,String> testCache;

    private ExecutorService executorService  = Executors.newFixedThreadPool(5);

    @PostConstruct
    @Override
    public void initCache(){
        testCache = CacheBuilder.newBuilder().refreshAfterWrite(10, TimeUnit.SECONDS).build(
                new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return loadTime();
                    }

                    @Override
                    public ListenableFuture<String> reload(final String key,String value){
                        ListenableFutureTask<String> task = ListenableFutureTask.create(new Callable<String>() {
                            @Override
                            public String call() throws Exception {
                                return loadTime();
                            }
                        });
                        executorService.execute(task);
                        return task;
                    }
                }
        );
    }

    private String loadTime() throws InterruptedException {
        Thread.sleep(5000);
        return String.valueOf(System.currentTimeMillis());
    }

    @Override
    public String getTime(){
        try {
            return testCache.get("1");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> listRecentSignUp() {
        return userMapper.listRecentSignUp();
    }

    @Override
    public String checkUser(User user) {
        if(getUserByUserName(user.getUserName())!= null){
            return SIGN_UP_ERROR_ENUM.HAS_USER_NAME.getMessage();
        }
        if(getUserByEmail(user.getEmail())!= null){
            return SIGN_UP_ERROR_ENUM.HAS_EMAIL.getMessage();
        }
        return null;
    }

    @Override
    public User getUserByUserName(String userName) {
        if(StringUtils.isBlank(userName)){
            return null;
        }
        return userMapper.getUserByUserName(userName);
    }

    @Override
    public User getUserByEmail(String email) {
        if(StringUtils.isBlank(email)){
            return null;
        }
        return userMapper.getUserByEmail(email);
    }

    @Override
    public void join(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void springSecuritySetSession(HttpServletRequest request,User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                myUserDetailsService.loadUserByUsername(user.getUserName()),user.getPassword());
        try{
             token.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = simpleAuthenticationManager
                    .authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        }
        catch( AuthenticationException e ){
            System.out.println("Authentication failed: " + e.getMessage());
        }
    }

}
