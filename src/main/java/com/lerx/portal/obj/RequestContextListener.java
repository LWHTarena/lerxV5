package com.lerx.portal.obj;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestContextListener implements ServletRequestListener {

	private static final String REQUEST_ATTRIBUTES_ATTRIBUTE = RequestContextListener.class.getName() + ".REQUEST_ATTRIBUTES";

	@Override
	public void requestDestroyed(ServletRequestEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestInitialized(ServletRequestEvent requestEvent) {
		if (!(requestEvent.getServletRequest() instanceof HttpServletRequest)) {

			throw new IllegalArgumentException(

					"Request is not an HttpServletRequest: " + requestEvent.getServletRequest());

		}

		HttpServletRequest request = (HttpServletRequest) requestEvent.getServletRequest();

		ServletRequestAttributes attributes = new ServletRequestAttributes(request);

		request.setAttribute(REQUEST_ATTRIBUTES_ATTRIBUTE, attributes);

		LocaleContextHolder.setLocale(request.getLocale());

		RequestContextHolder.setRequestAttributes(attributes);

	}

}
