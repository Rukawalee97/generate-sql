package com.rukawa.generate.sql.controller;

import com.rukawa.generate.sql.domain.Route;
import com.rukawa.generate.sql.domain.User;
import com.rukawa.generate.sql.dto.Result;
import com.rukawa.generate.sql.service.RouteService;
import com.rukawa.generate.sql.service.TokenCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private TokenCacheService tokenCacheService;

    @GetMapping("/user/nav")
    public Result initRoutes(@RequestHeader("Access-Token") String accessToken) {
        Optional<User> userOptional = tokenCacheService.getUserByToken(accessToken);
        Integer roleId = userOptional.isPresent() ? userOptional.get().getRoleId() : 0;
        List<Route> routes = routeService.queryRoutesByRole(roleId);
        Result result = Result.success(routes);
        result.setMessage("请求路由成功！");
        return result;
    }

}
