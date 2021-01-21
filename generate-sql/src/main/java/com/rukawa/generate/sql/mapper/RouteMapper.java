package com.rukawa.generate.sql.mapper;

import com.rukawa.generate.sql.domain.Route;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RouteMapper {

    Route selectRouteByPrimary(Integer routeId);

    List<Route> selectRouteListByRole(Integer roleId);

    int insertRouteSelective(Route route);

    int updateRouteByPrimary(Route route);
}
