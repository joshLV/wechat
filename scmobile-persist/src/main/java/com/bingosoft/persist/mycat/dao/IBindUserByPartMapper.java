package com.bingosoft.persist.mycat.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCountOutputDto;

public interface IBindUserByPartMapper {

	@Insert("insert into bind_user (openId,phoneNo,sharePhoneNo,bindTime,part_id,module_id,subscribeTime) value(#{openId},#{phoneNo},#{sharePhoneNo},now(),#{partId},#{moduleId},#{subscribeTime})")
	public void BindUser(@Param("openId")String openId,@Param("phoneNo")String phoneNo,@Param("sharePhoneNo")String sharePhoneNo,@Param("partId")int partId,@Param("moduleId")int moduleId,@Param("subscribeTime")String subscribeTime);
	
	@Select("select count(1) as row from bind_user where phoneNo=#{phone} and part_id=#{partId}")
	public GoodsCountOutputDto isExistsUser(@Param("phone")String phone,@Param("partId")int partId);
}
