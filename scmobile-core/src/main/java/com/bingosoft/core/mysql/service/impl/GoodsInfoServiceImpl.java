package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bingosoft.core.mysql.service.IGoodsInfoService;
import com.bingosoft.models.dto.AdInfoOutputDto;
import com.bingosoft.models.dto.GoodsCategoryIdOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsCategoryOutputDto;
import com.bingosoft.models.dto.GoodsInfoOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;
import com.bingosoft.models.dto.HotCategoryGoodsOutputDto;
import com.bingosoft.models.dto.HotGoodsCategoryOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mysql.dao.IGoodsInfoMapper;
import com.bingosoft.persist.mysql.dao.IGoodsMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService {

	@Autowired
	IGoodsInfoMapper goodsInfoMapper;

	@Autowired
	IGoodsMapper goodsMapper;

	@Autowired
	IRedisService redisService;

	@Override
	public GoodsCategoryInfoOutputDto getGoodsCategory(int categoryId) {
		// TODO Auto-generated method stub
		GoodsCategoryInfoOutputDto dto = goodsInfoMapper.getGoodsCategory(categoryId);
		if (dto != null) {
			List<GoodsInfoOutputDto> dtoList = goodsInfoMapper.getGoodsInfoByCategoryId(dto.getCategoryId());
			dto.setGoodsList(dtoList);
		}
		return dto;
	}

	@Override
	public GoodsItemInfoOutputDto getGoodsInfo(long goodsId) {
		// TODO Auto-generated method stub
		String adKey = String.format(RedisKeyPrefix.GoodsInfo_Prefix, goodsId);
		String cache = redisService.get(adKey);
		GoodsItemInfoOutputDto outputDto = null;
		if (StringUtils.isEmpty(cache)) {
			outputDto = goodsInfoMapper.getGoodsInfo(goodsId);
			redisService.set(adKey, JsonUtils.toJson(outputDto), 10000);
		} else {
			outputDto = JsonUtils.toBean(cache, GoodsItemInfoOutputDto.class);
		}
		return outputDto;
	}

	@Override
	public List<GoodsCategoryOutputDto> getGoodsCategoryList(int categoryId) {
		// TODO Auto-generated method stub
		return goodsMapper.getGoodsCategoryList(categoryId);
	}

	@Override
	public List<HotGoodsCategoryOutputDto> getHotGoodsCategory(int categoryId, int records) {
		// TODO Auto-generated method stub

		String adKey = String.format(RedisKeyPrefix.HotGoodsCategory_Prefix, categoryId, records);
		String cache = redisService.get(adKey);
		List<HotGoodsCategoryOutputDto> outputDto = null;
		if (StringUtils.isEmpty(cache)) {
			outputDto = goodsMapper.getHotGoodsCategory(categoryId, records);
			redisService.set(adKey, JsonUtils.toJson(outputDto), 10000);
		} else {
			outputDto = JsonUtils.toList(cache, HotGoodsCategoryOutputDto.class);
		}
		return outputDto;
	}

	@Override
	public GoodsCategoryIdOutputDto getCategoryId(int goodsId) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.getCategoryId(goodsId);
	}

	@Override
	public List<HotCategoryGoodsOutputDto> getHotCategoryGoodsSaleCount() {
		// TODO Auto-generated method stub
		return goodsMapper.getHotCategoryGoodsSaleCount();
	}

	@Override
	public GoodsItemInfoAndProdOutputDto getGoodsInfoAndProd(long goodsId) {
		// TODO Auto-generated method stub
		String adKey = String.format(RedisKeyPrefix.GoodsInfo_Prefix+":prod", goodsId);
		String cache = redisService.get(adKey);
		GoodsItemInfoAndProdOutputDto outputDto = null;
		if (StringUtils.isEmpty(cache)) {
			outputDto = goodsInfoMapper.getGoodsInfoAndProd(goodsId);
			redisService.set(adKey, JsonUtils.toJson(outputDto), 10000);
		} else {
			outputDto = JsonUtils.toBean(cache, GoodsItemInfoAndProdOutputDto.class);
		}
		return outputDto;
	}

}
