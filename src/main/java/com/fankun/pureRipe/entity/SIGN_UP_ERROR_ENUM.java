package com.fankun.pureRipe.entity;

/**
 * Created by fankun on 2017/6/12.
 */
public enum SIGN_UP_ERROR_ENUM {
    HAS_USER_NAME("0","repeat user name"),HAS_EMAIL("1","repeat email");
    private String code;
    private String message;
    private SIGN_UP_ERROR_ENUM(String code,String message){
        this.code = code;
        this.message = message;
    }
    public String getMessage(){
        return this.message;
    }
}
