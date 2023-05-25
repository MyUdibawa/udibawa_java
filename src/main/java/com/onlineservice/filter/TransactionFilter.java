package com.onlineservice.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.onlineservice.exception.ApiExceptionHandler;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TransactionFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Credentials", "true");
		res.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD");
		res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, Authorization");

		if (req.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
			res.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(request, response);
		}
		logger.trace(
				req.getServerName() + "" + req.getRequestURI() + " - " + req.getMethod() + " " + req.getRemoteAddr());
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Calls On Init
	}

	@Override
	public void destroy() {
		// Calls On Destroy
	}
}