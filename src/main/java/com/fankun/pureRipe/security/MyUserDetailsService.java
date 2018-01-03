package com.fankun.pureRipe.security;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fankun.pureRipe.entity.User;
import com.fankun.pureRipe.service.interf.UserService;
import com.google.common.collect.Lists;

/**
 * Created by fankun on 2017/1/25.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userService.getUserByUserName(username);
        if (StringUtils.isBlank(username)||user==null) {
            throw new UsernameNotFoundException("用户名为空");
        }
        return new MyUser(user.getUserId(),username,user.getPassword(),getAuthorities());
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = Lists.newArrayList();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authList;
    }

//    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        for (String role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role));
//        }
//        return authorities;
//    }
}
