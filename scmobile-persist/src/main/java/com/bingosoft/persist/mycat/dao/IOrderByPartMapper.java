package com.bingosoft.persist.mycat.dao;

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
public interface IOrderByPartMapper {
	@Select("select goods_id as goodsId,goods_name as goodsName,goods_image as goodsImage,goods_count as goodsCount,goods_price as goodsPrice,goods_desc as goodsDesc,total_amount as totalAmount,pay_fee payFee,order_status orderStatus,create_time createTime from order_items where order_id = #{orderId} and part_id=#{partId}")
	public OrderDetailOutputDto getOrderDetail(@Param("orderId") long orderId, @Param("partId") int partId);

	@Select("SELECT goods_name goodsName,pay_fee payFee,order_status orderStatus,FROM_UNIXTIME(create_time) orderTime,goods_desc goodsDesc,'话费抵扣' payMethod  FROM order_items where item_id=#{itemId} and part_id=#{partId}")
	public OrderNewItemOutputDto getNewsItem(@Param("itemId") long itemId, @Param("partId") int partId);

	@Select("SELECT item.order_id as orderId,item.item_id as itemId,item.goods_image as goodsImage,item.goods_count as goodsCount,\r\n"
			+ "item.goods_id as goodsId,item.goods_name as goodsName,item.goods_price as goodsPrice,item.order_status as orderStatus,\r\n"
			+ "item.pay_fee as payFee,item.total_amount as totalAmount,item.create_time as createTime,item.eff_time as effTime,item.exp_time as expTime,case   WHEN  eff_time>CURRENT_TIME() THEN '未生效'  WHEN exp_time>=CURRENT_TIME() and eff_time<=CURRENT_TIME() THEN '已生效'\r\n"
			+ "ELSE '已失效' end prodStatus,item.category_name cateName,item.category_image cateImg FROM v_order_list  item \r\n"
			+ " where item.part_id=#{partId} and item.user_id=#{userId} and channel_id=1 LIMIT #{pageStart},#{pageEnd}")
	public List<OrderItemListOutputDto> getOrderItemList(@Param("userId") String userId,
			@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd, @Param("partId") int partId);

	@Select("SELECT item.order_id as orderId,item.item_id as itemId,item.goods_image as goodsImage,item.goods_count as goodsCount,\r\n"
			+ "item.goods_name as goodsName,item.goods_price as goodsPrice,item.order_status as orderStatus,FROM_UNIXTIME(item.create_time) as createTime,\r\n"
			+ "item.pay_fee as payFee,item.total_amount as totalAmount,item.eff_time as effTime,item.exp_time as expTime,item.effective_time effectiveTime FROM v_news_list  item \r\n"
			+ " where item.part_id=#{partId} and item.user_id=#{userId} and channel_id=1 LIMIT #{pageStart},#{pageEnd}")
	public List<OrderNewListOutputDto> getOrderNewsList(@Param("userId") String userId,
			@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd, @Param("partId") int partId);

	@Insert("insert into order_info(order_id,user_id,user_name,phone_no,total_amount,pay_id,pay_fee,pay_note,order_desc,order_status,channel_id,create_time,part_id)\r\n"
			+ "values(#{order_id},#{user_id},#{user_name},#{phone_no},#{total_amount},#{pay_id},#{pay_fee},#{pay_note},#{order_desc},#{order_status},#{channel_id},unix_timestamp(now()),#{part_id})")
	public void addOrder(@Param("order_id") long order_id, @Param("user_id") String user_id,
			@Param("user_name") String user_name, @Param("phone_no") String phone_no,
			@Param("total_amount") int total_amount, @Param("pay_id") int pay_id, @Param("pay_fee") double pay_fee,
			@Param("pay_note") String pay_note, @Param("order_desc") String order_desc,
			@Param("order_status") int order_status, @Param("channel_id") int channel_id,
			@Param("part_id") int part_id);

	@Insert("insert into order_items(order_id,goods_id,goods_name,goods_image,goods_count,goods_price,goods_desc,total_amount,pay_fee,order_status,create_time,part_id,item_id,eff_time,exp_time,effective_time,category_name,category_image)\r\n"
			+ "values(#{order_id},#{goods_id},#{goods_name},#{goods_image},#{goods_count},#{goods_price},#{goods_desc},#{total_amount},#{pay_fee},#{order_status},unix_timestamp(now()),#{part_id},#{item_id},#{effTime},#{expTime},#{effectiveTime},#{cateName},#{cateImg})")
	public void addOrderItem(@Param("order_id") long order_id, @Param("goods_id") long goods_id,
			@Param("goods_name") String goods_name, @Param("goods_image") String goods_image,
			@Param("goods_count") int goods_count, @Param("goods_price") double goods_price,
			@Param("goods_desc") String goods_desc, @Param("total_amount") int total_amount,
			@Param("pay_fee") double pay_fee, @Param("order_status") int order_status, @Param("part_id") int part_id,
			@Param("item_id") long item_id, @Param("effTime") String effTime, @Param("expTime") String expTime,
			@Param("effectiveTime") String effectiveTime,@Param("cateName") String cateName,@Param("cateImg") String cateImg);

	@Insert("insert into fail_order_info(order_id,user_id,user_name,phone_no,total_amount,pay_id,pay_fee,pay_note,order_desc,order_status,channel_id,create_time,part_id)\r\n"
			+ "values(#{order_id},#{user_id},#{user_name},#{phone_no},#{total_amount},#{pay_id},#{pay_fee},#{pay_note},#{order_desc},#{order_status},#{channel_id},unix_timestamp(now()),#{part_id})")
	public void addFailOrder(@Param("order_id") long order_id, @Param("user_id") String user_id,
			@Param("user_name") String user_name, @Param("phone_no") String phone_no,
			@Param("total_amount") int total_amount, @Param("pay_id") int pay_id, @Param("pay_fee") double pay_fee,
			@Param("pay_note") String pay_note, @Param("order_desc") String order_desc,
			@Param("order_status") int order_status, @Param("channel_id") int channel_id,
			@Param("part_id") int part_id);

	@Insert("insert into fail_order_items(order_id,goods_id,goods_name,goods_image,goods_count,goods_price,goods_desc,total_amount,pay_fee,order_status,create_time,part_id,item_id,eff_time,exp_time,effective_time)\r\n"
			+ "values(#{order_id},#{goods_id},#{goods_name},#{goods_image},#{goods_count},#{goods_price},#{goods_desc},#{total_amount},#{pay_fee},#{order_status},unix_timestamp(now()),#{part_id},#{item_id},#{effTime},#{expTime},#{effectiveTime})")
	public void addFailOrderItem(@Param("order_id") long order_id, @Param("goods_id") long goods_id,
			@Param("goods_name") String goods_name, @Param("goods_image") String goods_image,
			@Param("goods_count") int goods_count, @Param("goods_price") double goods_price,
			@Param("goods_desc") String goods_desc, @Param("total_amount") int total_amount,
			@Param("pay_fee") double pay_fee, @Param("order_status") int order_status, @Param("part_id") int part_id,
			@Param("item_id") long item_id, @Param("effTime") String effTime, @Param("expTime") String expTime,
			@Param("effectiveTime") String effectiveTime);

	@Insert("insert into order_seq(order_id,seq_index,part_id) values(#{order_id},#{seqIndex},#{partId})")
	public void addSeqIndex(@Param("order_id") long orderId, @Param("seqIndex") String seqIndex,
			@Param("partId") int partId);
	
	@Select("select IFNULL(sum(pay_fee),0) pay_fee from v_order_sum where part_id=${part_id} and phone_no=#{phone} ")
	public double getOrderSum(@Param("phone")String phone,@Param("part_id")int part_id);
	
}
