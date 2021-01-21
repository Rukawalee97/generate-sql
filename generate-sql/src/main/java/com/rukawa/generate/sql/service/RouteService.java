package com.rukawa.generate.sql.service;

import com.rukawa.common.util.BeanUtil;
import com.rukawa.common.util.CollectionUtil;
import com.rukawa.generate.sql.domain.Route;
import com.rukawa.generate.sql.mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteMapper routeMapper;

    public List<Route> queryRoutesByRole(Integer roleId) {
        List<Route> routes = routeMapper.selectRouteListByRole(roleId);
        Map<Integer, List<Route>> routeMap = routes.stream()
                .collect(Collectors.groupingBy(Route::getRouteParent));
        return buildRoutes(routeMap.get(0), routeMap);
    }

    public List<Route> buildRoutes(List<Route> routes, Map<Integer, List<Route>> routeMap) {
        List<Route> routesBuilder = new ArrayList<>();
        if (CollectionUtil.isEmpty(routes)) {
            return routesBuilder;
        }
        // 获取根节点
        for (Route route : routes) {
            // 获取根节点Id
            Integer routeId = route.getRouteId();
            List<Route> routeSons = routeMap.get(routeId);
            // 递归添加子节点
            route.setChildren(buildRoutes(routeSons, routeMap));
            routesBuilder.add(route);
        }
        return routesBuilder;
    }

}
