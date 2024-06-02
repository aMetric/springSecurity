package com.coderwhs.mapper;

import com.coderwhs.entity.Menu;
import com.coderwhs.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MapperTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Test
    public void testSelectPermsByUserId(){
        List<String> users = menuMapper.selectPermsByUserId(4L);
        System.out.println(users);
    }

    @Test
    public void BCryptPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches("1234", "$2a$10$VJIYRb2eXtiWEqeiCYjgaOpjjdaSAhTmaJpm3HgmesXnvtyA0XVAy");
        System.out.println("matches = " + matches);
        // String encode = encoder.encode("1234");
        // String encode1 = encoder.encode("1234");
        // System.out.println("encode1 = " + encode1);
        // System.out.println("encode = " + encode);
    }

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
}
