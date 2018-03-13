package com.bingosoft.core.mycat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mycat.service.IBindUserByPartService;
import com.bingosoft.models.dto.BindUserInputDto;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.persist.mycat.dao.IBindUserByPartMapper;

@Service
public class BindUserByPartServiceImpl implements IBindUserByPartService{

	@Autowired
	IBindUserByPartMapper bingUserMapper;
	
	@Override
	public void addUser(BindUserInputDto input) {
		// TODO Auto-generated method stub
		GoodsCountOutputDto dto=bingUserMapper.isExistsUser(input.getPhoneNo(), input.getPartId());
		if(dto==null||dto.getRow()<=0)
		{
			bingUserMapper.BindUser(input.getOpenId(), input.getPhoneNo(), input.getSharePhoneNo(), input.getPartId(),input.getModuleId(),input.getSubscribeTime());
		}
		
		
		
	}

}
