package com.bingosoft.models.mongodb.entities;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import lombok.Data;

@Document(collection = "t_user_phone")
@Data
public class UserPhone {
	    @Id
	    private ObjectId id;
	    
	    private String phoneNo;
}
