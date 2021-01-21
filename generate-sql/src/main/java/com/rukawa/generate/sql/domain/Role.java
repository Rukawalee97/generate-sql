package com.rukawa.generate.sql.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private Integer roleId;

    private String roleName;

    private LocalDateTime createTime;

}
