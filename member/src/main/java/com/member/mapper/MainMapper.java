package com.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author yankaifeng
 * 创建日期 2021/6/8
 */
@Mapper
public interface MainMapper {

    @Select("SELECT COUNT(1) FROM user WHERE ACCOUNT = #{account} AND PASSWORD = #{password} limit 1")
    Integer checkLoginAccount(@Param("account") String account,@Param("password") String password);
}
