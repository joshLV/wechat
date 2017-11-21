package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.AdInfoOutputDto;

public interface IAdInfoMapper {
	
	@Select("select image_url imageUrl,link_url linkUrl from ad_info where position_id = #{positionId} and ad_status = 1 order by sort_order")
    public List<AdInfoOutputDto> getAdList(int postionId);
}
