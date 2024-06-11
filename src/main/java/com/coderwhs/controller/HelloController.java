package com.coderwhs.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author whs
 * @Date 2024/5/22 20:39
 * @Description
 */
@RestController
public class HelloController {

  /**
   * 测试
   * @return
   */
  @RequestMapping("/hello")
  // @PreAuthorize("hasAuthority('sysytem:dept:list')")
  @PreAuthorize("ex.hasAuthority('sysytem:dept:list')")
  public String hello(){
    return "hello";
  }

  /**
   * 测试
   * @return
   */
  @RequestMapping("/test/Cors")
  // @PreAuthorize("hasAuthority('sysytem:dept:list')")
  @PreAuthorize("ex.hasAuthority('sysytem:dept:list')")
  public String cors(){
    return "/test/Cors";
  }
}
