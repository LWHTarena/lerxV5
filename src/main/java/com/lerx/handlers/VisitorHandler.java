package com.lerx.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.lerx.annotation.Token;
import com.lerx.dao.iface.IPortalDao;
import com.lerx.dao.iface.IVisitorIPRecordDao;
import com.lerx.dao.iface.IVisitorsBookDao;
import com.lerx.entities.Portal;
import com.lerx.entities.VisitorIPRecord;
import com.lerx.entities.VisitorsBook;
import com.lerx.hql.entities.Rs;
import com.lerx.ip.qqwry.entities.IPLocation;
import com.lerx.ip.util.IPUtil;
import com.lerx.portal.obj.VisitorRec;
import com.lerx.sys.util.MavUtil;

@RequestMapping("/action_visitor")
@Controller
public class VisitorHandler {
	
	private static final String ADMINFORBID = "_admin.forbid_";
	
	
	@Autowired
	private IPortalDao portalDaoImpl;
	
	@Autowired
	private IVisitorIPRecordDao visitorIPRecordDaoImpl;
	
	@Autowired
	private IVisitorsBookDao visitorsBookDaoImpl;
	
	/*
	 * 列表
	 */
	@RequestMapping("/list")
	@Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
	public ModelAndView list(long id,@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize, HttpSession session,
			Map<String, Object> map) {
		if (id==0) {
			map.put("portal", true);
			Portal portal = portalDaoImpl.query();
			id = portal.getVbook().getId();
		}else {
			map.put("portal", false);
		}
		VisitorsBook vbook =visitorsBookDaoImpl.findByID(id);
		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		Rs rs =visitorIPRecordDaoImpl.find(vbook.getId(), page, pageSize);
		@SuppressWarnings("unchecked")
		List<VisitorIPRecord> list=(List<VisitorIPRecord>) rs.getList();
		List<VisitorIPRecord> listnew = new ArrayList<VisitorIPRecord>();
		for (VisitorIPRecord vipr:list) {
			IPLocation ipl	=	IPUtil.queryQQWry(vipr.getIp());
			String ipfrom	=	ipl.getCountry() + " | " + ipl.getArea();
			vipr.setIpfrom(ipfrom);
			listnew.add(vipr);
		}
		rs.setList(listnew);
		System.gc();
		map.put("pageUrl", "/action_visitor/list?id="+id);
		map.put("rs", rs);
		map.put("vbook", vbook);
		
		VisitorRec vr=new VisitorRec();
		vr.setCurrDay(visitorIPRecordDaoImpl.currPeriod(vbook.getId(), 0));
		vr.setCurrWeek(visitorIPRecordDaoImpl.currPeriod(vbook.getId(), 1));
		vr.setCurrMonth(visitorIPRecordDaoImpl.currPeriod(vbook.getId(), 2));
		vr.setCurrQuarter(visitorIPRecordDaoImpl.currPeriod(vbook.getId(), 3));
		vr.setCurrYear(visitorIPRecordDaoImpl.currPeriod(vbook.getId(), 4));
		map.put("vr", vr);
		
		return MavUtil.mav("jsp/visitor/list", "");

	}
	
}
