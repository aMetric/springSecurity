package com.coderwhs.filter;

import com.coderwhs.entity.LoginUser;
import com.coderwhs.utils.JwtUtil;
import com.coderwhs.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @Author wuhs
 * @Date 2024/5/24 8:45
 * @Description 认证过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired
  private RedisCache redisCache;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //获取token
    String token = request.getHeader("token");
    if(!StringUtils.hasText(token)){
      //放行
      filterChain.doFilter(request,response);
      return;
    }

    //解析token
    String userId;
    try {
      Claims claims = JwtUtil.parseJWT(token);
      userId= claims.getSubject();
    } catch (Exception e) {
      throw new RuntimeException("token非法");
    }

    //从redis中获取用户信息
    String redisKey = "login:"+userId;
    LoginUser loginUser = redisCache.getCacheObject(redisKey);
    if(Objects.isNull(loginUser)){
      throw new RuntimeException("用户未登入");
    }

    //存入SecurityContextHolder
    //获取权限信息，封装到Authentication中
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    //放行
    filterChain.doFilter(request,response);

  }
}
