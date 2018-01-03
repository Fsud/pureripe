package com.fankun.pureRipe.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fankun.pureRipe.entity.User;

/**
 * Created by fankun on 2017/1/19.
 */
@Mapper
public interface UserMapper {
//    @Results({
//            @Result(property = "userId", column = "user_id"),
//            @Result(property = "userName", column = "user_name")
//    })
//    @Select("select * from user_main where user_id = #{userId}")
    User getUserById(@Param("userId") Long userId);

    User getUserByUserName(String userName);

    List<String> listRecentSignUp();

    User getUserByEmail(String email);

    void insertUser(User user);

    User getUserByIdAndName(Long userId, String userName);
}
