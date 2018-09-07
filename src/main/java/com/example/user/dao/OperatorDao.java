package com.example.user.dao;

import com.example.user.entity.user.Operator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * 操作人，当前登录人
 */
@Mapper
@Component
public interface OperatorDao {
    /**
     * 保存optor
     * @param optor
     */
    void save(Operator optor);
    /**
     * 查询optor
     */
    Operator findById(Long id);
}
