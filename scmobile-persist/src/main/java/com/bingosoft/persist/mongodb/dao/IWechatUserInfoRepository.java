package com.bingosoft.persist.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bingosoft.models.mongodb.entities.WechatUserInfo;



public interface IWechatUserInfoRepository extends MongoRepository<WechatUserInfo, String>{

}
