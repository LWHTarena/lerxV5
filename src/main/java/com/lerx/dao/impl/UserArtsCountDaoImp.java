package com.lerx.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.lerx.dao.iface.IUserArtsCountDao;
import com.lerx.entities.User;
import com.lerx.entities.UserArtsCount;
import com.lerx.hql.entities.Rs;
import com.lerx.hql.util.HibernateCallbackUtil;


public class UserArtsCountDaoImp extends HibernateDaoSupport implements IUserArtsCountDao {

	@Override
	public boolean modify(UserArtsCount uac) {
		try {
			this.getHibernateTemplate().saveOrUpdate(uac);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public UserArtsCount findByUK(User user, int timeKey) {
		String hql = "from UserArtsCount u where u.user.id="+user.getId()+" and u.timeKey="+timeKey;
		@SuppressWarnings("unchecked")
		List<UserArtsCount> list = (List<UserArtsCount>)this.getHibernateTemplate().find(hql);

		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	@Override
	public Rs findTopByUKAndGroup(int groupId, int timeKey, int mod,int page,
			int pagesize) {

		String hql;
		String order;
		if (mod == 0) {
			order = " order by u.artsPassed desc";
		} else {
			order = " order by u.artsTotal desc";
		}

		if (groupId > 0) {
			hql = "from UserArtsCount u where u.user.role.id=" + groupId  + "and u.timeKey="+timeKey+ order;
		} else {
			hql = "from UserArtsCount u where u.timeKey="+timeKey+ order;
		}
		
		return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, 0, page, pagesize);
	
	}

	@Override
	public Rs findTopByUKAndGroup(String gidStr, int timeKey, int mod,
			int page, int pagesize) {


		String hql;
		String order;
		if (mod == 0) {
			order = " order by u.artsPassed desc";
		} else {
			order = " order by u.artsTotal desc";
		}
		
		String[] sArray = gidStr.split("_");
		String hqlWhere="";
		int gidtmp;
		for (int step = 0; step < sArray.length; step++) {
			if (sArray[step]!=null && !sArray[step].trim().equals("")){
				gidtmp=Integer.parseInt(sArray[step]);
				if (gidtmp>0){
					if (hqlWhere.equals("")){
						hqlWhere += " u.user.role.id="+gidtmp + " ";
					}else{
						hqlWhere += " or u.user.role.id="+gidtmp + " ";
					}
				}
			}
		}

		if (hqlWhere.trim().equals("")) {
			hql = "from UserArtsCount u where u.timeKey="+timeKey+ order;
			
		} else {
			hqlWhere = " ("+hqlWhere + ") ";
			hql = "from UserArtsCount u where " + hqlWhere  + "and u.timeKey="+timeKey+ order;
		}

		return HibernateCallbackUtil.getRs(this.getHibernateTemplate(), hql, 0, page, pagesize);
	
	
	}

	@Override
	public long countByUid(long uid) {
		String hql="select count(*) from UserArtsCount u where u.user.id=?";
		@SuppressWarnings("unchecked")
		List<Long> list = (List<Long>) this.getHibernateTemplate().find(hql,uid);
		return list.get(0);
	}

}
