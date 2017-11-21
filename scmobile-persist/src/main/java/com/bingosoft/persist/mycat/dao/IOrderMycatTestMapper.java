package com.bingosoft.persist.mycat.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.OrderMycatOutputDto;

@Mapper
public interface IOrderMycatTestMapper {
  @Select("select phone_no phoneNo from order_info where part_id=#{part}")
  public OrderMycatOutputDto getMycatTest(int part);
}
