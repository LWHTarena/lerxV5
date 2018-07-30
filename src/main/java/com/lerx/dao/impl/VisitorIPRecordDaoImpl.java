package com.lerx.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.lerx.dao.iface.IVisitorIPRecordDao;
import com.lerx.entities.VisitorIPRecord;
import com.lerx.hql.entities.Rs;
import com.lerx.hql.util.HibernateCallbackUtil;
import com.lerx.sys.util.TimeUtil;

public class VisitorIPRecordDaoImpl extends HibernateDaoSupport implements IVisitorIPRecordDao {

	@Override
	public VisitorIPRecord add(VisitorIPRecord vipr) {
		this.getHibernateTemplate().save(vipr);
		return vipr;
	}
	
	@Override
	public void modify(VisitorIPRecord vipr) {
		this.getHibernateTemplate().update(vipr);
	}

	@Override
	public VisitorIPRecord findLast(long bid) {
		String hql="select max(v.id) from VisitorIPRecord v where v.vbook.id=?";
		@SuppressWarnings("unchecked")
		List<Long> list= (List<Long>) this.getHibernateTemplate().find(hql,bid);
		if (list.size()>0) {
			long id=list.get(0);
			return this.getHibernateTemplate().get(VisitorIPRecord.class, id);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitorIPRecord> findSameIPAfterDatetime(long bid, long datetime,String ip) {
		String hql="from VisitorIPRecord v where v.vbook.id="+bid+" and v.visitDatetime>"+datetime+" and v.ip=?";
		return (List<VisitorIPRecord>) this.getHibernateTemplate().find(hql,ip);
	}

	@Override
	public Rs findSameIPAfterDatetime(long bid, long datetime,String ip, int page, int pagesize) {
		String hql="from VisitorIPRecord v where v.vbook.id="+bid+" and v.visitDatetime>"+datetime+" and v.ip=?";
		int firstPlace = 0;
		return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
	}

	@Override
	public Rs find(long bid, int page, int pagesize) {
		String hql="from VisitorIPRecord v where v.vbook.id="+bid + " order by v.visitDatetime desc";
		int firstPlace = 0;
		return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, firstPlace, page, pagesize);
	}

	@Override
	public long currPeriod(long bid, int stamp) {
		long startT=TimeUtil.firstDay(stamp);
//		System.out.println("value:"+startT+" date:" + TimeUtil.coverLongToStr(startT, "yyyy-MM-dd HH:mm:ss"));
		String hql="select count(*) from VisitorIPRecord v where v.vbook.id="+bid+" and v.visitDatetime > " +startT;
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql);
		return list.get(0);
	}

	

}
