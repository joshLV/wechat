package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.ArticleInfoItemOutputDto;
import com.bingosoft.models.dto.ArticleInfoOutputDto;

public interface IArticleInfoMapper {
	@Select("select a.article_id articleId,a.article_title articleTitle,b.tags from article_info a\r\n"
			+ " left join article_category b on a.category_id = b.category_id where a.is_top = 1 and b.parent_id = #{category_id} order by a.update_time desc limit #{records}")
	public List<ArticleInfoOutputDto> getHomeArticleList(@Param("category_id") int category_id,
			@Param("records") int records);

	@Select("select article_title articleTitle,article_content articleContent,article_author articleAuthor,article_source articleSource,image_url imageUrl,keywords,tags,article_type articleType,click_count clickCount,create_time createTime from article_info where article_id = #{articleId} and article_status = 1")
	public ArticleInfoItemOutputDto getArticleInfo(int articleId);
}
