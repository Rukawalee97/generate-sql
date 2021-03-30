package com.rukawa.generate.sql.controller;

import com.rukawa.generate.sql.domain.Collect;
import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.CollectService;
import com.rukawa.generate.sql.service.TokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @Autowired
    private TokenCacheService tokenCacheService;

    @GetMapping("/collects")
    public Result getCollects(@RequestHeader("Access-Token") String accessToken) {
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        Collect collect = new Collect();
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            collect.setUserId(user.getUserId());
        }
        return Result.success(collectService.queryCollects(collect));
    }

    @PostMapping("/collect")
    public Result updateCollect(@RequestBody Collect collect, @RequestHeader("Access-Token") String accessToken) {
        Result result;
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            collect.setUserId(user.getUserId());
        }
        collect.setCollectTime(LocalDateTime.now());
        int affectRows = collectService.updateCollect(collect);
        if (affectRows == 0) {
            result = Result.fail(false);
            result.setMessage("收藏失败！");
        } else {
            result = Result.success(true);
        }
        return result;
    }

    @DeleteMapping("/collect/{collectId}")
    public Result deleteCollect(@PathVariable Integer collectId) {
        int affectRows = collectService.deleteCollect(collectId);
        Result result;
        if (affectRows == 0) {
            result = Result.fail(false);
            result.setMessage("");
        } else {
            result = Result.success(true);
        }
        return result;
    }

}
