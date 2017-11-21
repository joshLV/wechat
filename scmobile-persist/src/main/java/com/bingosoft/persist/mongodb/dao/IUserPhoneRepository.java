package com.bingosoft.persist.mongodb.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.bingosoft.models.mongodb.entities.UserPhone;



public interface IUserPhoneRepository extends MongoRepository<UserPhone, String>{
	@Query("{'phoneNo':?0}")
	UserPhone findByPhoneNo(String phone);
}
