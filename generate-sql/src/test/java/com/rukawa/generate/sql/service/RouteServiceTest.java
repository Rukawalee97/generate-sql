package com.rukawa.generate.sql.service;

import com.rukawa.generate.sql.domain.Route;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @Test
    void queryRoutes() {
        List<Route> routes = routeService.queryRoutesByRole(1);
        System.out.println(routes);
    }
}