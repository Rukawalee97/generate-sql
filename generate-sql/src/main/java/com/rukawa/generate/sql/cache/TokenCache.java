package com.rukawa.generate.sql.cache;

import com.rukawa.generate.sql.domain.Token;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class TokenCache {

    private Map<String, Token> tokenMap;

    private TokenCache() {
        tokenMap = new ConcurrentHashMap<String, Token>();
    }

}
