package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role selectRoleByPrimaryKey(Integer roleId);

    List<Role> selectRoles(Role role);

    int updateRoleByPrimaryKey(Role role);

}
