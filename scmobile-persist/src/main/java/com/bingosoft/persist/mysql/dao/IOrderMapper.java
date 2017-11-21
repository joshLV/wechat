package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;

@Mapper
public interface IOrderMapper {

	@Select("select goods_id,goods_name,goods_image,goods_count,goods_price,goods_desc,total_amount,pay_fee,order_status,create_time from order_items where order_id = #{orderId}")
	public OrderDetailOutputDto getOrderDetail(long orderId);

	@Insert("insert into order_info(order_id,user_id,user_name,phone_no,total_amount,pay_id,pay_fee,pay_note,order_desc,order_status,channel_id,create_time,part_id)\r\n"
			+ "values(#{order_id},#{user_id},#{user_name},#{phone_no},#{total_amount},#{pay_id},#{pay_fee},#{pay_note},#{order_desc},#{order_status},#{channel_id},unix_timestamp(now()),#{part_id})")
	public void addOrder(@Param("order_id") long order_id,
			@Param("user_id") String user_id,
			@Param("user_name") String user_name,
			@Param("phone_no") String phone_no,
			@Param("total_amount") int total_amount,
			@Param("pay_id") int pay_id,
			@Param("pay_fee") double pay_fee,
			@Param("pay_note") String pay_note,
			@Param("order_desc") String order_desc,
			@Param("order_status") int order_status,
			@Param("channel_id") int channel_id,
			@Param("part_id") int part_id
			);

	@Insert("insert into order_items(order_id,goods_id,goods_name,goods_image,goods_count,goods_price,goods_desc,total_amount,pay_fee,order_status,create_time,part_id,item_id,eff_time,exp_time)\r\n"
			+ "values(#{order_id},#{goods_id},#{goods_name},#{goods_image},#{goods_count},#{goods_price},#{goods_desc},#{total_amount},#{pay_fee},#{order_status},unix_timestamp(now()),#{part_id},#{item_id},#{effTime},#{expTime})")
	public void addOrderItem(@Param("order_id") long order_id,
			@Param("goods_id") long goods_id,
			@Param("goods_name") String goods_name,
			@Param("goods_image") String goods_image,
			@Param("goods_count") int goods_count,
			@Param("goods_price") double goods_price,
			@Param("goods_desc") String goods_desc,
			@Param("total_amount") int total_amount,
			@Param("pay_fee") double pay_fee,
			@Param("order_status") int order_status,@Param("part_id") int part_id,@Param("item_id") long item_id,@Param("effTime")String effTime,@Param("expTime")String expTime);

	@Select("SELECT item.order_id orderId,item.item_id itemId,item.goods_image goodsImage,item.goods_count goodsCount,\r\n"
			+ "item.goods_id goodsId,item.goods_name goodsName,item.goods_price goodsPrice,item.order_status orderStatus,\r\n"
			+ "item.pay_fee payFee,item.total_amount totalAmount,from_unixtime(item.create_time) createTime,eff_time effTime,exp_time expTime FROM order_items  item left join order_info info on item.order_id=info.order_id\r\n"
			+ " where  info.user_id=#{userId} order by item.create_time desc LIMIT #{pageStart},#{pageEnd}")
	public List<OrderItemListOutputDto> getOrderItemList(@Param("userId") String userId,
			@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd);

	@Select("SELECT item.item_id itemId,item.goods_image goodsImage,item.goods_count goodsCount,\r\n"
			+ "item.goods_name goodsName,item.goods_price goodsPrice,item.order_status orderStatus,FROM_UNIXTIME(item.create_time) createTime,\r\n"
			+ "item.pay_fee payFee,item.total_amount totalAmount FROM order_items  item left join order_info info on item.order_id=info.order_id\r\n"
			+ " where  info.user_id=#{userId}  order by item.create_time desc LIMIT #{pageStart},#{pageEnd}")
	public List<OrderNewListOutputDto> getOrderNewsList(@Param("userId") String userId,
			@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd);

	@Select("SELECT goods_name goodsName,pay_fee payFee,order_status orderStatus,FROM_UNIXTIME(create_time) orderTime,goods_desc goodsDesc,'话费抵扣' payMethod  FROM order_items where item_id=#{itemId}")
	public OrderNewItemOutputDto getNewsItem(long itemId);
}
