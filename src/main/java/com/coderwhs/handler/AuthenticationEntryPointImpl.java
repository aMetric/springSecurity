package com.coderwhs.handler;

import com.alibaba.fastjson.JSON;
import com.coderwhs.utils.ResponseResult;
import com.coderwhs.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author wuhs
 * @Date 2024/6/2 10:14
 * @Description 自定义认证异常失败处理
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    ResponseResult result = new ResponseResult(HttpStatus.UNAUTHORIZED.value(), "用户认证失败，请重新登入");

    String jsonString = JSON.toJSONString(result);

    //处理异常
    WebUtils.renderString(response,jsonString);
  }
}
