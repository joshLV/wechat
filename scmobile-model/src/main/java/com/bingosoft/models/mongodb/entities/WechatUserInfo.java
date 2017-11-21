package com.bingosoft.models.mongodb.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import lombok.Data;

@Document(collection = "t_wechat_user_info")
@Data
public class WechatUserInfo {
	
	
	@Id
    private ObjectId id;
	
    @Field("openId")
	private String openId;
    
    private String userId;
    
    private String phoenNo;
    
    private String headImg;
    
    private String  userName;
}
