package com.rukawa.generate.sql.controller;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.common.util.StringUtil;
import com.rukawa.generate.sql.domain.Role;
import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.dto.UserDto;
import com.rukawa.generate.sql.service.RoleService;
import com.rukawa.generate.sql.service.TokenCacheService;
import com.rukawa.generate.sql.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        if (!userOptional.isPresent()) {
            result = Result.fail(false);
            result.setMessage("当前用户未登录！");
            return result;
        }
        User userCache = userOptional.get();
        if (!StringUtil.isEmpty(user.getOldPassword()) && !userCache.getPassword().equals(user.getOldPassword())) {
            result = Result.fail(false);
            result.setMessage("旧密码不正确！");
            return result;
        }
        user.setUserId(userCache.getUserId());
        int update = userService.update(user);
        if (update > 0) {
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
            result = Result.success(BeanUtil.removeAttributes(user, "password", "userId", "createTime"));
            return result;
        }
        result = Result.fail(false);
        return result;
    }

    @GetMapping("/manages")
    public Result manageUserRole() {
        List<Role> roles = roleService.queryRoles();
        List<User> users = userService.queryUsers();
        List<UserDto> userDtoList = users.parallelStream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    Optional<Role> roleOptional = roles.parallelStream()
                            .filter(role -> !BeanUtil.isEmpty(role.getRoleId()) && role.getRoleId().equals(user.getRoleId()))
                            .findFirst();
                    if (roleOptional.isPresent()) {
                        Role role = roleOptional.get();
                        userDto.setRoleName(role.getRoleName());
                        userDto.setRoleId(role.getRoleId());
                    }
                    userDto.setUsername(user.getUsername());
                    userDto.setUserId(user.getUserId());
                    return userDto;
                }).collect(Collectors.toList());
        return Result.success(userDtoList);
    }

    @DeleteMapping("/{userId}")
    public Result delUser(@PathVariable Integer userId, @RequestHeader("Access-Token") String accessToken) {
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if(user.getUserId().equals(userId)) {
                Result result = Result.fail(false);
                result.setMessage("不能对当前账号进行操作！");
                return result;
            }
        }
        if (userService.delete(userId) > 0 && tokenCacheService.deleteToken(userId)) {
            return Result.success(true);
        }
        return Result.fail(false);
    }
}
