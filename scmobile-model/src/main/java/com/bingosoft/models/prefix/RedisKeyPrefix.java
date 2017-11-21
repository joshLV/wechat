package com.bingosoft.models.prefix;

public class RedisKeyPrefix {
	
	/**
	 * 广告列表redis.key
	 */
   public static final String AD_LIST_PREFIX="ad";
   
   /**
    * 首页流量课堂redis.key
    */
   public static final String ArticleList_Prefix="articleList:%s:records%s";
   
   /**
    * 文章详情redis.key
    */
   public static final String ArticleCategoryList_Prefix="cate：%s";
   
   /**
    * 文章分类redis.key
    */
   public static final String ArticleCategoryDetail_Prefix="cate:item:%s";
   
   /**
    * 流量套餐列表redis.key
    */
   public static final String GoodsCategoryList_Prefix="goods_cate";
   
   /**
    * 商品信息%s redis.key
    */
   public static final String GoodsInfo_Prefix="goods_info:%s";
   
   /**
    * 热门商品%s redis.key
    */
   public static final String HotGoodsCategory_Prefix="hotgoodscate:p:%s:r:%s";
   
   /**
    * 商品订购次数
    */
   public static final String GoodsOrderCount_Prefix="goods:order:%s";
   
   /**
    * 商品类别订购次数
    */
   public static final String CategoryOrderCount_Prefix="cate:order:%s";
   
   /**
    * 文字查看次数
    */
   public static final String ArticleViewCount_Prefix="article:view:%s";
   
   
}
