package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    User selectUserByPrimary(Integer userId);

    User selectUserByUsernameWithPassword(String username, String password);

    int countByUsername(String username);

    int insertUserSelective(User user);

    int updateUserByPrimary(User user);
}
