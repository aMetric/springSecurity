package com.coderwhs.service;

import com.coderwhs.entity.User;
import com.coderwhs.utils.ResponseResult;

/**
 * @Author whs
 * @Date 2024/5/24 8:15
 * @Description
 */
public interface LoginService {

  ResponseResult login(User user);

  ResponseResult logout();
}
