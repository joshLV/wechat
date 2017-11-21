package com.bingosoft.persist.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bingosoft.models.mongodb.entities.UserInfo;

@Repository
public interface IUserInfoRepository extends MongoRepository<UserInfo,String>{

}
