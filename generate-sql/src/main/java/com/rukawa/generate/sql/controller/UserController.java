package com.rukawa.generate.sql.controller;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.common.util.StringUtil;
import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.TokenCacheService;
import com.rukawa.generate.sql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenCacheService tokenCacheService;

    @Value("${system.expire}")
    private Integer expireTime;

    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpServletRequest request) {
        User loginUser = userService.login(user);
        Result result = null;
        if (!BeanUtil.isEmpty(loginUser)) {
            String token = StringUtil.generateToken();
            tokenCacheService.updateToken(token, loginUser, expireTime);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("accessToken", token);
            resultMap.put("username", user.getUsername());
            result = Result.success(resultMap);
            result.setMessage("恭喜您，[" + user.getUsername() + "] 登录成功！");
            return result;
        }
        result = Result.fail(false);
        result.setMessage("非常抱歉，用户名或密码错误！");
        return result;
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        int register = userService.register(user);
        Result result = null;
        if (register > 0) {
            result = Result.success(true);
            result.setMessage("恭喜您，注册[" + user.getUsername() + "]成功！");
            return result;
        }
        result = Result.fail(false);
        result.setMessage("非常遗憾，注册[" + user.getUsername() + "]失败！");
        return result;
    }

    @PostMapping("/logout")
    public Result logout(@RequestHeader("Access-Token") String accessToken) {
        boolean logout = tokenCacheService.deleteToken(accessToken);
        Result result = Result.success(logout);
        result.setMessage("恭喜您，注销成功！");
        return result;
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user, @RequestHeader("Access-Token") String accessToken) {
        Result result = null;
        int update = userService.update(user);
        if(update > 0) {
            tokenCacheService.updateToken(accessToken, user, expireTime);
            result = Result.success(update > 0);
            result.setMessage("更新用户信息成功！");
            return result;
        }
        result = Result.fail(false);
        result.setMessage("更新用户信息失败！");
        return result;
    }

    @GetMapping("/info")
    public Result getUserInfo(@RequestHeader("Access-Token") String accessToken) {
        Result result = null;
        if (BeanUtil.isEmpty(accessToken)) {
            result = Result.fail(false);
            return result;
        }
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            result = Result.success(BeanUtil.removeAttributes(user, "password", "userId", "createTime", "roleId"));
            return result;
        }
        result = Result.fail(false);
        return result;
    }
}
