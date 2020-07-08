package com.library.demo.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

  @Bean
  public PermissionInterceptor permissionInterceptor() {
    return new PermissionInterceptor();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(permissionInterceptor()).addPathPatterns("/**");// 拦截所有请求
  }
}
