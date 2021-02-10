package com.rukawa.generate.sql.service;

import com.rukawa.generate.sql.domain.Role;
import com.rukawa.generate.sql.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> queryRoles() {
        return roleMapper.selectRoles(null);
    }

    public int updateRole(Role role) {
        return roleMapper.updateRoleByPrimaryKey(role);
    }

}
