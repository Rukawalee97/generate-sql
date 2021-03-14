package com.rukawa.generate.sql.domain;

import lombok.Data;

@Data
public class Page {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

}
