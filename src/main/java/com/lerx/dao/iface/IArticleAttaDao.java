package com.lerx.dao.iface;

import java.util.List;

import com.lerx.entities.ArticleAtta;

public interface IArticleAttaDao {
	
	public ArticleAtta add(ArticleAtta aa);
	public ArticleAtta findByID(long id);
	public boolean delByID(long id);
	public List<ArticleAtta> findByArticleID(long aid);
	public List<ArticleAtta> findByArticleAndFileID(long aid, long uid);

}
