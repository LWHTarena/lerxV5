package com.lerx.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.lerx.annotation.Token;
import com.lerx.hql.entities.Rs;
import com.lerx.hql.util.RsUtil;
import com.lerx.sys.obj.ResFile;
import com.lerx.sys.util.DesUtils;
import com.lerx.sys.util.FileUtil;
import com.lerx.sys.util.ListUtil;
import com.lerx.sys.util.Log4jInit;
import com.lerx.sys.util.MavUtil;

@RequestMapping("/action_log")
@Controller
public class LogHandler {
	
	/*private static final String SUCCESS = "jsp/result/success";
	private static final String FAILED = "jsp/result/failed";*/

	private static final String ADMINFORBID = "_admin.forbid_";
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	@RequestMapping("/list")
	@Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
	public ModelAndView list(HttpServletRequest request,Map<String, Object> map,@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pageSize", required = false) Integer pageSize) {
		
		if (page==null) {
			page=1;
		}
		if (pageSize==null) {
			pageSize=10;
		}
		List<ResFile> lfArray=FileUtil.getResFiles(messageSource,request.getServletContext().getRealPath(Log4jInit.outputDir));
		List<Object> lobject=new ArrayList<Object>();
		for (Object o1:lfArray) {
			lobject.add(o1);
		}
		List<Object> currpage=ListUtil.paging(lobject, page, pageSize);
		List<ResFile> currPageFiles=new ArrayList<ResFile>();
		for (Object o2:currpage) {
			ResFile lf=(ResFile) o2;
			currPageFiles.add(lf);
		}
		Rs rs=RsUtil.init(page, pageSize, lfArray.size());
				
		rs.setList(currPageFiles);
		map.put("rs", rs);
		map.put("pageUrl", "/action_log/list");
		return MavUtil.mav("jsp/logs/list", "");
		
	}
	
	@RequestMapping("/details")
	@Token(ajax = false, admin = true, failedPage = ADMINFORBID, msgKey = "fail.permission")
	public ModelAndView details(Map<String, Object> map,@RequestParam(value = "securityStr", required = true) String securityStr) {
		String realPath=DesUtils.decrypt(messageSource, securityStr,true);
		String log;
		log = FileUtil.readLargeFile4(realPath,null, System.getProperty("file.encoding"));
		log=HtmlUtils.htmlEscape(log);
		map.put("log", log);
		return MavUtil.mav("jsp/logs/details", "");
	}
	
	@ResponseBody
	@RequestMapping("/del")
	@Token(ajax = true, admin = true,log=true,mark="log--<delete>",  failedPage = ADMINFORBID, msgKey = "fail.permission")
	public int del(@RequestParam(value = "securityStr", required = true) String securityStr) {
		String realPath=DesUtils.decrypt(messageSource, securityStr,true);
		if (FileUtil.delete(realPath)) {
			return 0;
		}else {
			return -1;
		}
		
	}
}