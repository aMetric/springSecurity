package com.coderwhs.service.impl;

import com.coderwhs.entity.LoginUser;
import com.coderwhs.entity.User;
import com.coderwhs.service.LoginService;
import com.coderwhs.utils.JwtUtil;
import com.coderwhs.utils.RedisCache;
import com.coderwhs.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author whs
 * @Date 2024/5/24 8:15
 * @Description
 */
@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private RedisCache redisCache;

  /**
   * 在接口中我们通过AuthenticationManager的authenticate方法来进行用户认证,
   * 所以需要在SecurityConfig中配置把AuthenticationManager注入容器。
   * @param user
   * @return
   */
  @Override
  public ResponseResult login(User user) {
    //通过AuthenticationManager的authenticate方法来进行用户认证
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
    Authentication authenticate = authenticationManager.authenticate(token);

    //如果没通过，给出对应的提示
    if(Objects.isNull(authenticate)){
      throw new RuntimeException("用户名或密码错误");
    }

    //如果通过了，使用userId生成一个jwt，存入ResponseResult返回
    LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
    String id = loginUser.getUser().getId().toString();

    //根据用户id生成token
    String jwt = JwtUtil.createJWT(id);

    //把完整的用户信息存入redis，使用userId作为key
    redisCache.setCacheObject("login:"+id,loginUser);

    ResponseResult responseResult = new ResponseResult();
    responseResult.setCode(200);
    responseResult.setMsg("登入成功");
    Map<String,String> map = new HashMap<>();
    map.put("token",jwt);
    responseResult.setData(map);

    return responseResult;
  }

  @Override
  public ResponseResult logout() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    LoginUser loginUser = (LoginUser) authentication.getPrincipal();
    Long userid = loginUser.getUser().getId();
    redisCache.deleteObject("login:"+userid);
    return new ResponseResult(200,"退出成功");
  }

}
