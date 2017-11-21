package com.bingosoft.models.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderInfo {
   private long orderId;
   private String userId;
   private String userName;
   private String phoneNo;
   private int totalAmout;
   private int payId;
   private double payFee;
   private String payNote;
   private String orderDesc;
   private int orderStatus;
   private int channelId;
   private Date createTime;
   private int partId;
   private List<OrderItem> orderItem;
}
