package com.bingosoft.core.mongodb.service;

import com.bingosoft.models.mongodb.entities.UserInfo;

public interface IUserInfoService {
   public void writeUserInfo(UserInfo user);
   public UserInfo getUserInfo(String userId);
}
