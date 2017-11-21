package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.ArticleCategoryOutputDto;
import com.bingosoft.models.dto.ArticleOutputDto;

public interface IArticleCategoryMapper {
  @Select("select category_id categoryId,category_name categoryName,category_desc categoryDesc,tags from article_category where parent_id = #{categoryId} and category_status = 1 order by sort_order")
  public List<ArticleCategoryOutputDto> getArticleCategoryList(int categoryId);
  
  @Select("select article_id articleId,article_type articleType,image_url imageUrl,article_title articleTitle,article_note articleNote,tags tags,click_count clickCount from article_info where category_id = #{categoryId} and article_type = #{articleType} order by create_time desc limit #{pageSart},#{pageEnd}")
  public List<ArticleOutputDto> getArticleList(@Param("categoryId") int categoryId,
		  @Param("articleType")int articleType,
		  @Param("pageSart")int pageSart,
		  @Param("pageEnd")int pageEnd);
}
