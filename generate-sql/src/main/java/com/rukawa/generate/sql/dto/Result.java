package com.rukawa.generate.sql.dto;

import com.rukawa.common.util.StringUtil;
import com.rukawa.generate.sql.enumerates.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.swing.StringUIClientPropertyKey;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer status;

    private Object result;

    private String message;

    private String timestamp;

    public static Result success(Object result) {
        Status status = Status.SUCCESS;
        return new Result(status.getCode(), result, status.getMessage(), StringUtil.getTimestamp());
    }

    public static Result fail(Object result) {
        Status status = Status.SERVER_ERROR;
        return new Result(status.getCode(), result, status.getMessage(), StringUtil.getTimestamp());
    }
}
