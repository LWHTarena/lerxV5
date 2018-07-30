package com.lerx.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.lerx.dao.iface.ITempletPortalMainDao;
import com.lerx.entities.TempletPortalMain;
import com.lerx.hql.entities.Rs;
import com.lerx.hql.util.HibernateCallbackUtil;
import com.lerx.sys.util.StringUtil;

public class TempletPortalMainDaoImpl extends HibernateDaoSupport implements ITempletPortalMainDao {

	@Override
	public long add(TempletPortalMain templet) {
		List<TempletPortalMain> findedResult=findByTitle(templet.getName());
		if (findedResult.size()==0){
			this.getHibernateTemplate().save(templet);
			return templet.getId();
		}else{
			return -1;
		}
		
	}

	@Override
	public boolean delByID(long id) {
		
		this.getHibernateTemplate().delete(findByID(id));
		
		if (findByID(id)==null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void modify(TempletPortalMain templet) {
		this.getHibernateTemplate().update(templet);

	}

	@Override
	public TempletPortalMain findByID(long id) {
		TempletPortalMain main=this.getHibernateTemplate().get(TempletPortalMain.class, id);
		if (main!=null && (main.getUuid()==null || main.getUuid().trim().equals(""))) {
			main.setUuid(StringUtil.uuidStr());
			modify(main);
		}
		return main;
	}

	@Override
	public List<TempletPortalMain> findByTitle(String title) {
		String hql="from TempletPortalMain t where t.name=?";
		
		@SuppressWarnings("unchecked")
		List<TempletPortalMain> list = (List<TempletPortalMain>) this.getHibernateTemplate().find(hql,title);
		return list;
	}

	/*
	 * 分页查询
	 */
	
	@Override
	public Rs query(int page, int pagesize) {
		String hql;
		hql="from TempletPortalMain t order by t.orderNum asc,t.id desc";
		
		int firstPlace=0;
		return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql,firstPlace, page, pagesize);
	}
	
	@Override
	public Rs queryByAllow(int page, int pagesize) {
		String hql;
		hql="from TempletPortalMain t where t.state is true order by t.orderNum asc,t.id desc";
		
		int firstPlace=0;
		return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql,firstPlace, page, pagesize);
	}

	@Override
	public List<TempletPortalMain> queryAll() {
		String hql;
		hql="from TempletPortalMain t order by t.orderNum asc,t.id desc";
//		String hql="from TerminalShowTemplet t where t.market is null or t.market.id="+mid+" order by t.orderNum asc,t.id desc";
		@SuppressWarnings("unchecked")
		List<TempletPortalMain> list = (List<TempletPortalMain>) this.getHibernateTemplate().find(hql);
		
		return list;
	}

	@Override
	public void setDef(long id) {
		// TODO Auto-generated method stub
		
		String hql="update TempletPortalMain t set t.def=false";
		this.getHibernateTemplate().bulkUpdate(hql);
		hql="update TempletPortalMain t set t.def=true where t.id="+id;
		this.getHibernateTemplate().bulkUpdate(hql);
	}

	@Override
	public TempletPortalMain findDef() {
		String hql;
		hql="from TempletPortalMain t where t.def is true order by t.orderNum asc,t.id desc";
		@SuppressWarnings("unchecked")
		List<TempletPortalMain> list = (List<TempletPortalMain>) this.getHibernateTemplate().find(hql);
		if (list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public TempletPortalMain findByUuid(String uuid) {
		String hql;
		hql="from TempletPortalMain t where t.uuid=?";
		@SuppressWarnings("unchecked")
		List<TempletPortalMain> list = (List<TempletPortalMain>) this.getHibernateTemplate().find(hql,uuid);
		if (list.size()>0){
			return list.get(0);
		}
		return null;
	}

	

	

}
