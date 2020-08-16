package com.aFeng.mapper;

import com.aFeng.pojo.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("goodsMapper")
public interface GoodsMapper {

    @Insert("insert into goods (name,stock,price,db_origin) values(#{name},#{stock},#{price},DATABASE())")
    boolean add(Goods goods);

    @Select("select * from goods where id = #{id}")
    Goods findById(Long id);

    @Select("select * from goods")
    List<Goods> list();

    @Update("delete from goods where id = #{id}")
    boolean delete(Long id);
}
