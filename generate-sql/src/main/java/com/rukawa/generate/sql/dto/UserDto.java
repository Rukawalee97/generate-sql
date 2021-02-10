package com.rukawa.generate.sql.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer userId;

    private Integer roleId;

    private String username;

    private String password;

    private String roleName;

}
