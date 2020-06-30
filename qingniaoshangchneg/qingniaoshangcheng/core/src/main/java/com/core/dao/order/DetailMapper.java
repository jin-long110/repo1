package com.core.dao.order;

import java.util.List;

import com.core.pojo.order.Detail;
import com.core.pojo.order.DetailCriteria;
import org.apache.ibatis.annotations.Param;

public interface DetailMapper {
    int countByExample(DetailCriteria example);

    int deleteByExample(DetailCriteria example);

    int deleteByPrimaryKey(Long id);

    int insert(Detail record);

    int insertSelective(Detail record);

    List<Detail> selectByExample(DetailCriteria example);

    Detail selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Detail record, @Param("example") DetailCriteria example);

    int updateByExample(@Param("record") Detail record, @Param("example") DetailCriteria example);

    int updateByPrimaryKeySelective(Detail record);

    int updateByPrimaryKey(Detail record);
}