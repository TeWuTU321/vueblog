package com.cheng.vueblog.config;


import com.cheng.vueblog.shiro.AccountRealm;
import com.cheng.vueblog.shiro.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    //向spring容器中注入  `JwtFilter`
    @Autowired
    JwtFilter jwtFilter;
    //向spring容器中注入  `accountRealm`  bean
    @Bean
    AccountRealm accountRealm() {
        return new AccountRealm();
    }

    //禁用session, 不保存用户登录状态。保证每次请求都重新认证
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator()
    {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }


	//配置安全管理器 SecurityManage
    //并关联上JwtRealm
    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //配置Realm
        securityManager.setRealm(accountRealm);

        //关闭shiro自带得session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        return securityManager;
    }

    //注意这里是是十分重要的一点，如果没有这个bean，会出现一个关于`securityManager`无效配置的一个错误
    /**
     * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器
     * 全局过滤器会对所有请求进行拦截，而本例中只需要拦截除 /login 和 /logout 外的请求
     * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解
     */
    @Bean
    public FilterRegistrationBean<Filter> registration(JwtFilter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>(filter);
        registration.setEnabled(false);
        return registration;
    }





    //  设置拦截策略链，在这里我们的拦截策略是，所有的请求都由 `JwtFilter` 进行拦截，包括login，
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition()
    {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        LinkedHashMap<String, String> filterMap = new LinkedHashMap<>();
        //filterMap.put("/login", "anon");
        filterMap.put("/**","JwtFilter");
        chainDefinition.addPathDefinitions(filterMap);

        return chainDefinition;
    }


    /**
     * 配置访问资源需要的权限
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition)
    {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();

        //设置安全管理器
        filterFactoryBean.setSecurityManager(securityManager);

        //自定义jwt专用过滤器
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("JwtFilter",jwtFilter);
        filterFactoryBean.setFilters(filters);


        Map<String, String> filterChainMap = shiroFilterChainDefinition.getFilterChainMap();
        filterFactoryBean.setFilterChainDefinitionMap(filterChainMap);

        return filterFactoryBean;

    }

}
