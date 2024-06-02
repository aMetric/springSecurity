package com.coderwhs.handler;

import com.alibaba.fastjson.JSON;
import com.coderwhs.utils.ResponseResult;
import com.coderwhs.utils.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author wuhs
 * @Date 2024/6/2 10:20
 * @Description
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
  /**
   * Handles an access denied failure.
   *
   * @param request               that resulted in an <code>AccessDeniedException</code>
   * @param response              so that the user agent can be advised of the failure
   * @param accessDeniedException that caused the invocation
   * @throws IOException      in the event of an IOException
   * @throws ServletException in the event of a ServletException
   */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
    ResponseResult result = new ResponseResult(HttpStatus.FORBIDDEN.value(), "您的权限不足");

    String jsonString = JSON.toJSONString(result);

    //处理异常
    WebUtils.renderString(response,jsonString);
  }
}
