package com.lerx.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lerx.annotation.Token;
import com.lerx.dao.iface.IArticleAttaDao;
import com.lerx.entities.ArticleAtta;
import com.lerx.portal.obj.AttaReturn;

@RequestMapping("/action_atta")
@Controller
public class AttaHandler {

	private static final String LOGINPAGE = "jsp/user/login";
	
	
	@Autowired
	private IArticleAttaDao articleAttaDaoImpl;
	
	
	@ResponseBody
	@RequestMapping(value = "/del/{id}")
	@Token(ajax=true,log=true,mark="atta--<del>",loginOrAdmin=true,failedPage=LOGINPAGE)
	public int del(@PathVariable(value = "id", required = true) Long id,HttpSession session) {
		if (articleAttaDaoImpl.delByID(id)) {
			return 0;
		}else {
			return -1;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/reloadByAid/{aid}")
	public List<AttaReturn> reloadByAid(@PathVariable(value = "aid", required = true) Long aid,HttpSession session) {
		List<ArticleAtta> list=articleAttaDaoImpl.findByArticleID(aid);
		List<AttaReturn> listr=new ArrayList<AttaReturn>();
		for (ArticleAtta aa:list) {
			AttaReturn ar = new  AttaReturn();
			ar.setAid(aa.getId());
			ar.setFid(aa.getUf().getId());
			ar.setOid(aa.getArticle().getId());
			ar.setUrl(aa.getUf().getUrl());
			ar.setUploadDatetime(aa.getUf().getUploadDatetime());
			ar.setTitle(aa.getTitle());
			listr.add(ar);
		}
		return listr;
		
	}
	
}
