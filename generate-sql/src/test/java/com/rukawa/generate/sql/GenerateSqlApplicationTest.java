package com.rukawa.generate.sql;

import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class GenerateSqlApplicationTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testUserMapper() {
        User user = new User();
        user.setUsername("rukawa2");
        user.setPassword("123456");
        user.setRoleId(1);
        user.setCreateTime(LocalDateTime.now());
        System.out.println(userMapper.insertUserSelective(user));
    }
}