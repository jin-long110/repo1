package com.core.dao.product;

import java.util.List;

import com.core.pojo.product.Color;
import com.core.pojo.product.ColorCriteria;
import org.apache.ibatis.annotations.Param;

public interface ColorMapper {
    int countByExample(ColorCriteria example);

    int deleteByExample(ColorCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Color record);

    int insertSelective(Color record);

    List<Color> selectByExample(ColorCriteria example);

    Color selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Color record, @Param("example") ColorCriteria example);

    int updateByExample(@Param("record") Color record, @Param("example") ColorCriteria example);

    int updateByPrimaryKeySelective(Color record);

    int updateByPrimaryKey(Color record);
}