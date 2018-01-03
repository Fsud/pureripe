package com.fankun.pureRipe;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.fankun.pureRipe.dao.UserMapper;
import com.fankun.pureRipe.entity.User;

/**
 * Created by fankun on 2017/6/20.
 */
public class UserMapperTest{

    @Test
    public void getUserById() {
        SqlSessionFactory sqlSessionFactory = getSessionFactory();
        UserMapper userMapper = sqlSessionFactory.openSession().getMapper(UserMapper.class);
        //System.out.println(userMapper.getUserById(1L).getEmail());
        //System.out.println(userMapper.getUserByIdAndName(1L,"fankun").getEmail());
       System.out.println(new Date(userMapper.getUserById(1L).getAddTime()));
    }

    private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;
        String resource = "configuration.xml";
        try {
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources
                    .getResourceAsReader(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public User getUserByUserName(String userName) {
        return null;
    }

    public List<String> listRecentSignUp() {
        return null;
    }

    public User getUserByEmail(String email) {
        return null;
    }

    public void insertUser(User user) {

    }
}
