package com.coderwhs.controller;

import com.coderwhs.entity.User;
import com.coderwhs.service.LoginService;
import com.coderwhs.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginServcie;

    /**
     * 用户登入
     * @param user
     * @return
     */
    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginServcie.login(user);
    }


    /**
     * 退出登入
     * @return
     */
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginServcie.logout();
    }
}
