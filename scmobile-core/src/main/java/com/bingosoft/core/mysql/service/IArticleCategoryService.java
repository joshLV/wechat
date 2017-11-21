package com.bingosoft.core.mysql.service;

import java.util.List;


import com.bingosoft.models.dto.ArticleCategoryOutputDto;
import com.bingosoft.models.dto.ArticleInfoItemOutputDto;
import com.bingosoft.models.dto.ArticleInfoOutputDto;
import com.bingosoft.models.dto.ArticleOutputDto;

public interface IArticleCategoryService {
	public List<ArticleCategoryOutputDto> getArticleCategoryList(int categoryId);

	public List<ArticleOutputDto> getArticleList(int categoryId, int articleType, int pageIndex, int pageSize);
	
	public List<ArticleInfoOutputDto> getHomeArticleList(int category_id, int records);
	
	public ArticleInfoItemOutputDto getArticleInfo(int articleId);
}
