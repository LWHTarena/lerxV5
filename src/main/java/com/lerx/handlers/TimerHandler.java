package com.lerx.handlers;

import java.io.File;
import java.util.List;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lerx.dao.iface.IGroupDao;
import com.lerx.entities.ArticleGroup;
import com.lerx.sys.util.ConfigReader;
import com.lerx.sys.util.FileUtil;
import com.lerx.sys.util.SysUtil;
import com.lerx.v5.util.GroupUtil;

@Component
public class TimerHandler {

	
	@Autowired
	private IGroupDao groupDaoImpl;
	
	@Autowired
	private ResourceBundleMessageSource messageSource;
	
	
   // @Scheduled(fixedRate = 1000*60*30)   
    public void htmlCreateUpdate(){ 
    	List<ArticleGroup> list=groupDaoImpl.queryChanged();
    	String realPath,strCreateDateTime,fromurl;
    	String charset=messageSource.getMessage("charset", null, "utf-8", null);
    	String htmlRoot=messageSource.getMessage("group.file.static.root", null, "html", null);
    	String indexFile=messageSource.getMessage("file.html.default", null, "index.html", null);
    	
    	
    	File configFile=SysUtil.getConfigFile(messageSource, "app");
    	String contextPath= ConfigReader.getKey(configFile,"context.path");
    	
    	if (contextPath==null || contextPath.trim().equals("")) {
    		contextPath="http://localhost/";
    	}
//    	System.out.println("定时任务contextPath:"+contextPath);
    	strCreateDateTime=messageSource.getMessage("msg.datetime.html.static.create", null, "", null);
    	fromurl=contextPath+"action_show/index";
    	String index=FileUtil.appPath()+indexFile;
    	
    	FileUtil.htmlBySniff(fromurl , index, strCreateDateTime,  charset);
    	
//    	String contextPath="http://localhost:8080/LerxV5/";
    	for (ArticleGroup ag:list) {
    		realPath = FileUtil.appPath()+GroupUtil.htmlFolder(ag,htmlRoot);
    		realPath = FileUtil.repairFilePath(realPath)+indexFile;
    		strCreateDateTime=messageSource.getMessage("msg.datetime.html.static.create", null, "", null);
			
    		fromurl=contextPath+"action_show/nav/"+ag.getId();
			FileUtil.htmlBySniff(fromurl, realPath, strCreateDateTime,  charset);
    	}
       /* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
        System.out.println("timer : "+format.format(new Date()));*/   
    } 
    
}
