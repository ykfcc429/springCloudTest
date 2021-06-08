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

    @Select("SELECT count(1) FROM user WHERE account = #{account} AND PASSWORD = #{password}")
    Integer checkLoginAccount(@Param("account") String account,@Param("password") String password);
}
