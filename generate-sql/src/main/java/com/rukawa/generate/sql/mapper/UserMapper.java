package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    User selectUserByPrimary(Integer userId);

    User selectUserByUsernameWithPassword(String username, String password);

    List<User> selectUsers(User user);

    int countByUsername(String username);

    int insertUserSelective(User user);

    int updateUserByPrimary(User user);

    int deleteUserByPrimary(Integer userId);
}
