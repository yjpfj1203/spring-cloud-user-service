package com.example.user.dao;

import com.example.user.entity.Region;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RegionDao {
    List<Region> findAll();
}
