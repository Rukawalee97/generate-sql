package com.rukawa.generate.sql.interceptor;

import com.rukawa.generate.sql.dto.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionInterceptor {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        Result fail = Result.fail(false);
        fail.setMessage(e.getMessage());
        return fail;
    }

}
