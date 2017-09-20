package com.zzsim.taxi.admin;

import com.zzsim.taxi.admin.base.interceptors.TokenFilter;
import com.zzsim.taxi.admin.base.shiroRealm.CustomAuthenticationFilter;
import com.zzsim.taxi.admin.base.shiroRealm.CustomSubjectFactory;
import com.zzsim.taxi.admin.base.shiroRealm.ShiroDbRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {

	/**
	 * 重新设置 shiro 过滤器配置
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
		filterRegistration.setEnabled(true);
		filterRegistration.addUrlPatterns("/*");
		filterRegistration.setOrder(1);
		List<String> urls = new ArrayList<>(1);
		urls.add("/*");
		filterRegistration.setUrlPatterns(urls);
		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
		return filterRegistration;
	}

	/**
	 * 定义 shiroFilter 过滤器
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(getSecurityManager());
		bean.setLoginUrl("/#/login");
		bean.setSuccessUrl("/#/home");
		bean.setUnauthorizedUrl("redirect:/#/403");

		Map<String, Filter> filters = new HashMap<>();
		filters.put("authToken", getTokenFilter());
		bean.setFilters(filters);

		bean.setFilterChainDefinitions("" +
				"/login* = anon\n" +
				"/#/** = anon\n" +
				"/common/**.htm = anon\n" +
				"/** = authToken\n");
		return bean;
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager getSecurityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setSessionManager(getSessionManager());
		securityManager.setRealm(getUserRealm());
		securityManager.setSubjectFactory(getSubjectFactory());
		securityManager.setSubjectDAO(getDefaultSubjectDAO());
		return securityManager;
	}

	@Bean("sessionManager")
	public DefaultSessionManager getSessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionIdCookieEnabled(false);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		sessionManager.setSessionValidationSchedulerEnabled(false);
		sessionManager.setDeleteInvalidSessions(false);
		return sessionManager;
	}

	@Bean
	public ShiroDbRealm getUserRealm() {
		ShiroDbRealm userRealm = new ShiroDbRealm();
		return userRealm;
	}

	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(getSecurityManager());
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public CustomSubjectFactory getSubjectFactory() {
		return new CustomSubjectFactory();
	}

	@Bean
	public DefaultSubjectDAO getDefaultSubjectDAO(){
		DefaultSubjectDAO defaultSubjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		defaultSubjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		return defaultSubjectDAO;
	}

	private CustomAuthenticationFilter getCustomAuthenticationFilter() {
		CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter();
		return customAuthenticationFilter;
	}
	private TokenFilter getTokenFilter() {
		TokenFilter tokenFilter = new TokenFilter();
		return tokenFilter;
	}

}