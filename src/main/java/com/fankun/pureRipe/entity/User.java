package com.fankun.pureRipe.entity;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

/**
 * Created by fankun on 2017/1/19.
 */

@GroupSequence({User.First.class, User.Second.class, User.class})
public class User {

    interface First{

    }

    interface Second{

    }

    private Long userId;

    @NotNull(message = "用户名不能为空",groups = {First.class})
    @Length(min = 3,max = 20,message = "用户名长度必须在{min}到{max}之间",groups = {First.class})
    @Pattern(regexp = "[a-zA-Z0-9]", message = "用户名[${validatedValue}]必须为英文和数字",groups = {First.class})
    private String userName;

    @NotNull(message = "密码不能为空",groups = {Second.class})
    @Length(min = 3,max = 20,message = "密码长度必须在{min}到{max}之间",groups = {Second.class})
    private String password;

    @Email
    private String email;

    private Long addTime;

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
