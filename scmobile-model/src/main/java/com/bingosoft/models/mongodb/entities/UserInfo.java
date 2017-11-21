package com.bingosoft.models.mongodb.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "t_user")
@Data
public class UserInfo {

}
