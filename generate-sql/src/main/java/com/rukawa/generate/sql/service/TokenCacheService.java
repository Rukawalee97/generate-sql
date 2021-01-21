package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.generate.sql.cache.TokenCache;
import com.rukawa.generate.sql.domain.Token;
import com.rukawa.generate.sql.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class TokenCacheService {

    @Autowired
    private TokenCache tokenCache;

    public boolean updateToken(String token, User user, Integer expireTime) {
        Map<String, Token> tokenMap = tokenCache.getTokenMap();
        Token tokenObj = tokenMap.get(token);
        tokenObj = BeanUtil.isEmpty(tokenObj) ? new Token() : tokenObj;
        tokenObj.setTokenTime(LocalDateTime.now());
        tokenObj.setExpireTime(expireTime);
        // 查询缓存中的用户信息
        Optional<User> userOptional = getUserByToken(token);
        if (userOptional.isPresent()) {
            User tokenUser = userOptional.get();
            tokenObj.setUser(BeanUtil.updateObject(tokenUser, user));
        } else {
            tokenObj.setUser(user);
        }
        tokenMap.put(token, tokenObj);
        return true;
    }

    public void updateTokenExpireTime(String token) {
        Map<String, Token> tokenMap = tokenCache.getTokenMap();
        Token tokenObj = tokenMap.get(token);
        if (!BeanUtil.isEmpty(tokenObj)) {
            tokenObj.setTokenTime(LocalDateTime.now());
        }
    }

    public boolean deleteToken(String token) {
        Map<String, Token> tokenMap = tokenCache.getTokenMap();
        tokenMap.remove(token);
        return true;
    }

    public Optional<User> getUserByToken(String token) {
        Map<String, Token> tokenMap = tokenCache.getTokenMap();
        Token tokenObj = tokenMap.get(token);
        if (!BeanUtil.isEmpty(tokenObj)) {
            return Optional.ofNullable(tokenObj.getUser());
        }
        return Optional.empty();
    }

}
