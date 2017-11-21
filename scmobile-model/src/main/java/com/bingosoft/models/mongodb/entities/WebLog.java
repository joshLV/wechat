package com.bingosoft.models.mongodb.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.Data;

@Document(collection = "t_web_log")
@Data
public class WebLog
{
	public WebLog()
	{
		this.viewTime=new Date();
	}
	
//	public ShareLog(ObjectId _id,String apiKey,String appname,List<Object> activities)
//	{
//		this.id=_id;
//		this.apiKey=apiKey;
//		this.appname=appname;
//		this.activities=activities;
//	}
    @Id
    private ObjectId id;
    @Field("openId")
    private String openId;
    private String userName;
    private Date viewTime;
    private int viewDate;
    private String viewIp;
    private String url;
    private String args;
    private String phoneNo;
    
}
