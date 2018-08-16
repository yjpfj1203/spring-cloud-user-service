package com.example.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by zhouguoyong on 22/06/2017.
 */
@Setter
@Getter
@NoArgsConstructor
public class Region {
    private Long id;

    private Long provinceId;

    private String provinceName;

    private Long cityId;

    private String cityName;

    private Long areaId;

    private String areaName;

    private Long streetId;

    private String streetName;

    private boolean deleted;

    private Date lastModifiedDate;

    private Date createdDate;

}
