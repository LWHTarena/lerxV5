package com.lerx.handlers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/action_test")
@Controller
public class TestHandler {
	
	
	@ResponseBody
	@RequestMapping("/test")
	public String artAddChk(HttpServletRequest request, HttpServletResponse response) {
		for (int i=0;i<100000;i++) {
			try {
				Thread.sleep(1000);
				response.getWriter().write("i:"+i);
				response.getWriter().flush();
				response.getWriter().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "aaa";
	}
	

}
