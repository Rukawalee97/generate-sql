package com.rukawa.generate.sql.interceptor;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.service.TokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenUpdateInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenCacheService tokenCacheService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("Access-Token");
        if (!BeanUtil.isEmpty(accessToken)) {
            tokenCacheService.updateTokenExpireTime(accessToken);
        }
        return true;
    }

}
