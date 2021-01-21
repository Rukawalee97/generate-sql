package com.rukawa.generate.sql.service;

import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.exception.DuplicateUsernameException;
import com.rukawa.generate.sql.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${user.userHead}")
    private String userHead;

    @Value("${user.roleId}")
    private Integer roleId;

    public User login(User user) {
        return userMapper.selectUserByUsernameWithPassword(user.getUsername(), user.getPassword());
    }

    public int register(User user) {
        try {
            user.setRoleId(roleId);
            user.setUserHead(userHead);
            return userMapper.insertUserSelective(user);
        } catch (DuplicateKeyException e) {
            log.error("#register(user) user: {}, occur: {}", user, e);
            throw new DuplicateUsernameException("[" + user.getUsername() + "] 用户名重复, 请重新输入！");
        }
    }

    public int update(User user) {
        return userMapper.updateUserByPrimary(user);
    }

    public int countByUsername(String username) {
        return userMapper.countByUsername(username);
    }

}
