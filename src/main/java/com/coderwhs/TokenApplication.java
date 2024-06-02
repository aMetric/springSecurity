package com.coderwhs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @Author wuhs
 * @Date 2024/5/23 7:32
 * @Description
 */
@SpringBootApplication
@MapperScan("com.coderwhs.mapper")
public class TokenApplication {
  public static void main(String[] args) {
   SpringApplication.run(TokenApplication.class, args);
  }
}
