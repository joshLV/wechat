package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "city")
public class GoodsInfoServiceImpl implements IGoodsInfoService {

	@Autowired
	IGoodsInfoMapper goodsInfoMapper;

	@Autowired
	IGoodsMapper goodsMapper;

	@Autowired
	IRedisService redisService;

	@Override
	@Cacheable(value="goods_cate",key="#categoryId")
	public GoodsCategoryInfoOutputDto getGoodsCategory(int categoryId) {
		// TODO Auto-generated method stub
		String adKey = String.format(RedisKeyPrefix.CateGoodsInfo_Prefix, categoryId);
		String cache = redisService.get(adKey);
		GoodsCategoryInfoOutputDto dto = null;
		if (StringUtils.isEmpty(cache)) {
			dto = goodsInfoMapper.getGoodsCategory(categoryId);
			if (dto != null) {
				List<GoodsInfoOutputDto> dtoList = goodsInfoMapper.getGoodsInfoByCategoryId(dto.getCategoryId());
				dto.setGoodsList(dtoList);
			}
			redisService.set(adKey, JsonUtils.toJson(dto), 1000);
		} else {
			dto=JsonUtils.toBean(cache, GoodsCategoryInfoOutputDto.class);
		}
		return dto;
	}

	@Override
	@Cacheable(value="goods_info",key="#goodsId")
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
	@Cacheable(value="goods_cate_list",key="#categoryId")
	public List<GoodsCategoryOutputDto> getGoodsCategoryList(int categoryId) {
		// TODO Auto-generated method stub
		return goodsMapper.getGoodsCategoryList(categoryId);
	}

	@Override
	@Cacheable(value="hot_goods_cate",key="#categoryId.#records")
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
	@Cacheable(value="cate_id",key="#goodsId")
	public GoodsCategoryIdOutputDto getCategoryId(int goodsId) {
		// TODO Auto-generated method stub
		return goodsInfoMapper.getCategoryId(goodsId);
	}

	/**
	 * 获取销售数量
	 */
	@Override
	@Cacheable(value="goods_sale")
	public List<HotCategoryGoodsOutputDto> getHotCategoryGoodsSaleCount() {
		// TODO Auto-generated method stub
		String saleCacheKey=RedisKeyPrefix.GoodsSale_Prefix;
		String saleCache = redisService.get(saleCacheKey);
		List<HotCategoryGoodsOutputDto> output=null;
		if(StringUtils.isEmpty(saleCache))
		{
			output= goodsMapper.getHotCategoryGoodsSaleCount();
			redisService.set(saleCacheKey, JsonUtils.toJson(output), 10);
		}else
		{
			output = JsonUtils.toList(saleCache, HotCategoryGoodsOutputDto.class);
		}
		return output;
	}

	@Override
	@Cacheable(value="goods_info_prod",key="#goodsId")
	public GoodsItemInfoAndProdOutputDto getGoodsInfoAndProd(long goodsId) {
		// TODO Auto-generated method stub
		String adKey = String.format(RedisKeyPrefix.GoodsInfo_Prefix + ":prod", goodsId);
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
