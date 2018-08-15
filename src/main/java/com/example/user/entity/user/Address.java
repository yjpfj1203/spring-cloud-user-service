package com.example.user.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class Address {
    private Long id;
    private Long userId;
    private boolean defValue;
    private Long stateId;
    private String stateName;
    private Long provinceId;
    private String provinceName;
    private Long cityId;
    private String cityName;
    private Long areaId;
    private String areaName;
    private Long streetId;
    private String streetName;
    private String address;
    private String zipCode;
}
