package com.justclick.authentication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.justclick.authentication.interceptors.RequestInterceptor;
import com.justclick.authentication.repositories.TenantRepository;


@Configuration
public class AppConfig implements WebMvcConfigurer {
	
	 @Autowired
	 TenantRepository tenantRepo;
	 @Override
	 public void addInterceptors(InterceptorRegistry registry)   {
		 registry.addInterceptor(new RequestInterceptor(tenantRepo));
	 }

}
