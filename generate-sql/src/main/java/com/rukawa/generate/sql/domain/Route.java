package com.rukawa.generate.sql.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Route {

    private Integer routeId;

    private String path;

    private String title;

    private String key;

    private String icon;

    private String name;

    private String component;

    private String redirect;

    private Integer routeParent;

    private Integer roleId;

    private List<Route> children;

}
