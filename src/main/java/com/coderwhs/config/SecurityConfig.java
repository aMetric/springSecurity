package com.coderwhs.config;

import com.coderwhs.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author wuhs
 * @Date 2024/5/24 7:56
 * @Description SpringSecurity配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  @Autowired
  private AuthenticationEntryPoint authenticationEntryPoint;

  @Autowired
  private AccessDeniedHandler accessDeniedHandler;

  @Autowired
  private AuthenticationSuccessHandler successHandler;

  @Autowired
  private AuthenticationFailureHandler failureHandler;

  //创建BCryptPasswordEncoder注入容器
  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http    //关闭csrf
            .csrf().disable()
            //不通过Session获取SecurityContext
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            //permitAll无论有没有登入，都可以访问
            .antMatchers("/hello").permitAll()
            //anonymous允许匿名用户访问，不允许已经登入的用户访问
            .antMatchers("/user/login").anonymous();
            //基于配置的权限控制
            // .antMatchers("/test/Cors").hasAuthority("sysytem:dept:list")
            //除上面外的所有请求全部需要鉴权认证

    //把token过滤器调教到过滤器链之中
    http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

    //配置异常处理器
    http.exceptionHandling()
            //认证失败的处理器
            .authenticationEntryPoint(authenticationEntryPoint)
            //授权失败的处理器
            .accessDeniedHandler(accessDeniedHandler);

    //让springSecurity允许跨域
    http.cors();

    http.formLogin()
             //配置认证成功处理器
            .successHandler(successHandler)
            //配置认证失败处理器
            .failureHandler(failureHandler);

    http.authorizeRequests().anyRequest().authenticated();
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }


}
