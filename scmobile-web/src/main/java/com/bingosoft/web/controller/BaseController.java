package com.bingosoft.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;

import com.bingosoft.models.dto.MessageDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.utils.JSONUtils;
import com.bingosoft.utils.crypt.TokenUtils;

public class BaseController {
	
	final String tokenHeader = "os";
	
	@SuppressWarnings("unused")
	protected MessageDto validateToken(HttpServletRequest request) {
		MessageDto dto = validateBaseToken(request);
		if (dto.getUserInfo() == null)
			return dto;
		if (StringUtils.isEmpty(dto.getUserInfo().getPhoenNo())) {
			dto.setStatus(501);
			dto.setMessage("您暂未绑定手机号");
			dto.setUserInfo(null);
			return dto;
		}
		// dto.setUserInfo(userDto);
		return dto;
	}

	@SuppressWarnings("unused")
	protected MessageDto validateBaseToken(HttpServletRequest request) {
		MessageDto dto = new MessageDto();
		String token = request.getHeader(tokenHeader);
		// token="26F86C8245045FC656E3EE6B156D54943748E844C3C5D44A3BAD935D7480A2BA367EB523C6D2AA9A5324786E7B430EBBA4293008D086D1A5195A711FEF265449CB8AE62671E8C798B45E23170B6C49C34F4A0082841EF23C55B4C6F15F7369EE1D7C4D92100EC878E164B7E86DDEA3231F9AC14645FCBEDE187CBF6CAB66B348EBE9504C79B320085EB8F968743B08CEC9110E33AD43BF4D883B8A41488B396B26DDB98C2EE3B1D22AAC3ACAFB5832BA6369BBCC75CFE133064F42038E5EE9CD31F2B3CCE9BF18FD6206A2FBC82E58E9AE876773DBC628B166E4FAAED359B5A5";
		// token="26F86C8245045FC656E3EE6B156D54943748E844C3C5D44A3BAD935D7480A2BA367EB523C6D2AA9A5324786E7B430EBBA4293008D086D1A5195A711FEF265449CB8AE62671E8C798B45E23170B6C49C34F4A0082841EF23C55B4C6F15F7369EE1D7C4D92100EC878E164B7E86DDEA3231F9AC14645FCBEDE187CBF6CAB66B348EBE9504C79B320085EB8F968743B08CEC9110E33AD43BF4D883B8A41488B396B26DDB98C2EE3B1D22AAC3ACAFB5832BA6369BBCC75CFE133064F42038E5EE9CD31F2B3CCE9BF18FD6206A2FBC82E58E9AE876773DBC628B166E4FAAED359B5A5";
		if (StringUtils.isEmpty(token)) {
			dto.setStatus(502);
			dto.setMessage("非法请求");
			return dto;
		}
		String user = "";
		try {
			user = TokenUtils.decrypt(token);
		} catch (Exception e) {

		}
		if (StringUtils.isEmpty(user)) {
			dto.setStatus(502);
			dto.setMessage("非法请求");
			return dto;
		}
		UserInfoDto userDto = JSONUtils.toBean(user, UserInfoDto.class);

		if (userDto == null) {
			dto.setStatus(503);
			dto.setMessage("登录超时，请重新进入该页面");
			return dto;

		}
		
		dto.setUserInfo(userDto);
		return dto;
	}

	protected int getPartId(String phoneNo) {
		return Integer.valueOf(phoneNo.substring(10));
	}
}
