package com.lerx.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.lerx.dao.iface.IArticleAttaDao;
import com.lerx.entities.ArticleAtta;

public class ArticleAttaDaoImpl extends HibernateDaoSupport implements IArticleAttaDao {

	@Override
	public ArticleAtta add(ArticleAtta aa) {
		List<ArticleAtta> list=findByArticleAndFileID(aa.getArticle().getId(),aa.getUf().getId());
		if (list.isEmpty() || list.size()==0) {
			this.getHibernateTemplate().save(aa);
			return aa;
		}else {
			return null;
		}
		
	}

	@Override
	public ArticleAtta findByID(long id) {
		return this.getHibernateTemplate().get(ArticleAtta.class, id);
	}

	@Override
	public boolean delByID(long id) {
		this.getHibernateTemplate().delete(findByID(id));

		if (findByID(id) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ArticleAtta> findByArticleID(long aid) {
		String hql="from ArticleAtta a where a.article.id="+aid;
		@SuppressWarnings("unchecked")
		List<ArticleAtta> list = (List<ArticleAtta>) this.getHibernateTemplate().find(hql);
		return list;
	}

	@Override
	public List<ArticleAtta> findByArticleAndFileID(long aid, long uid) {
		String hql="from ArticleAtta a where a.article.id="+aid + " and a.uf.id="+uid;
		@SuppressWarnings("unchecked")
		List<ArticleAtta> list = (List<ArticleAtta>) this.getHibernateTemplate().find(hql);
		return list;
	}

}
