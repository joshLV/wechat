package com.bingosoft.models.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="user_browse")
public class UserBrowse {
  
  @Column(name="user_id")
  private String userId;
  
  @Column(name="goods_id")
  private long goodsId;
  
  @Column(name="update_time")
  private Date updateTime;
}
