package com.rukawa.generate.sql.controller;

import com.rukawa.generate.sql.domain.Role;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/roles")
    public Result getRoles() {
        List<Role> roles = roleService.queryRoles();
        return Result.success(roles);
    }

    @PostMapping("/role")
    public Result updateRole(@RequestBody Role role) {
        int resultCount = roleService.updateRole(role);
        Result result = null;
        if(resultCount > 0) {
            result = Result.success(true);
        } else {
            result = Result.fail(false);
        }
        return result;
    }

}
