package com.coderwhs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.coderwhs.entity.LoginUser;
import com.coderwhs.entity.User;
import com.coderwhs.mapper.MenuMapper;
import com.coderwhs.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @Author whs
 * @Date 2024/5/24 7:18
 * @Description 重写loadUserByUsername方法
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MenuMapper menuMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    //查询用户信息
    LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
    lambdaQueryWrapper.eq(User::getUserName,username);
    User user = userMapper.selectOne(lambdaQueryWrapper);

    //如果没有查询到用户就抛出异常
    if (Objects.isNull(user)){
      throw new RuntimeException("用户名或密码错误");
    }

    //查询对应的权限信息
    List<String> list = menuMapper.selectPermsByUserId(user.getId());

    //把数据封装成UserDetails返回
    return new LoginUser(user,list);
  }
}
