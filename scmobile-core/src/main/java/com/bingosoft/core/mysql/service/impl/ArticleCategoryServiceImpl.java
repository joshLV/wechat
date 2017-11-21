package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bingosoft.core.mysql.service.IArticleCategoryService;
import com.bingosoft.models.dto.ArticleCategoryOutputDto;
import com.bingosoft.models.dto.ArticleInfoItemOutputDto;
import com.bingosoft.models.dto.ArticleInfoOutputDto;
import com.bingosoft.models.dto.ArticleOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mysql.dao.IArticleCategoryMapper;
import com.bingosoft.persist.mysql.dao.IArticleInfoMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.LuaScriptUtils;
import com.bingosoft.utils.serialize.JsonUtils;


@Service
public class ArticleCategoryServiceImpl implements IArticleCategoryService {

	@Autowired
	IArticleCategoryMapper articleCategoryMapper;

	@Autowired
	IArticleInfoMapper articleInfoMapper;

	@Autowired
	IRedisService redisService;

	@Override
	public List<ArticleCategoryOutputDto> getArticleCategoryList(int categoryId) {
		// TODO Auto-generated method stub
		String cateKey = String.format(RedisKeyPrefix.ArticleCategoryList_Prefix, categoryId);
		String cache = redisService.get(cateKey);
		List<ArticleCategoryOutputDto> output = null;
		if (StringUtils.isEmpty(cache)) {
			output = articleCategoryMapper.getArticleCategoryList(categoryId);
			redisService.set(cateKey, JsonUtils.toJson(output), 10000);
		} else {
			output = JsonUtils.toList(cache, ArticleCategoryOutputDto.class);
		}
		return output;
	}

	@Override
	public List<ArticleOutputDto> getArticleList(int categoryId, int articleType, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		String adKey = String.format(RedisKeyPrefix.ArticleList_Prefix, categoryId, articleType);
		String cache = redisService.get(adKey);
		List<ArticleOutputDto> outputDto = null;
		if (StringUtils.isEmpty(cache)) {
			int pageEnd = pageIndex * pageSize;
			outputDto = articleCategoryMapper.getArticleList(categoryId, articleType, pageEnd - pageSize, pageEnd);
			redisService.set(adKey, JsonUtils.toJson(outputDto), 10000);
		} else {
			outputDto = JsonUtils.toList(cache, ArticleOutputDto.class);
		}
		return outputDto;
	}

	@Override
	public List<ArticleInfoOutputDto> getHomeArticleList(int category_id, int records) {
		// TODO Auto-generated method stub
		return articleInfoMapper.getHomeArticleList(category_id, records);
	}

	@Override
	public ArticleInfoItemOutputDto getArticleInfo(int articleId) {
		// TODO Auto-generated method stub
		// 获取文章缓存key
		String adKey = String.format(RedisKeyPrefix.ArticleCategoryDetail_Prefix, articleId);
		String cache = redisService.get(adKey);
		// 文章浏览次数累加Key
		String countKey = String.format(RedisKeyPrefix.ArticleViewCount_Prefix, articleId);
		ArticleInfoItemOutputDto outputDto = null;
		if (StringUtils.isEmpty(cache)) {
			outputDto = articleInfoMapper.getArticleInfo(articleId);
			redisService.set(adKey, JsonUtils.toJson(outputDto), 10000);
		} else {
			outputDto = JsonUtils.toBean(cache, ArticleInfoItemOutputDto.class);
		}
		String[] params = { countKey };
		redisService.evalScript(LuaScriptUtils.INCR_BY_KEY, 1, params);
		return articleInfoMapper.getArticleInfo(articleId);
	}

}
