package org.ina.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.method.GlobalMethodSecurityBeanDefinitionParser;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc //스프링MVC를 활성화 시키는 방법
@ComponentScan(basePackages = {"org.ina.time.controller",
							   "org.ina.common.exception",
							   "org.ina.board.controller"}) //스캔되면서 
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ServletConfig implements WebMvcConfigurer{

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) { //뷰 리졸버 registry

		//Default(기본) 뷰 리졸버. JSP를 뷰로 사용할 때 쓰인다.
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/WEB-INF/views/");
		bean.setSuffix(".jsp");
		registry.viewResolver(bean);
	}

	@Override   //css,js,jsp는 프론트 컨트롤러를 지나치면 안된다.
	public void addResourceHandlers(ResourceHandlerRegistry registry) { //핸들러 registry(css,js,jsp)는 resources에 담아준다.

		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Bean
	public CommonsMultipartResolver getResolver() throws Exception{
		
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		
		resolver.setMaxUploadSize(1024 * 1024 * 10);
		resolver.setMaxUploadSizePerFile(1024 * 1024 * 10);
		resolver.setMaxInMemorySize(1024 * 1024 * 10);
		resolver.setUploadTempDir(new FileSystemResource("C:\\upload\\tmp"));
		
		resolver.setDefaultEncoding("UTF-8");
		
		return resolver;
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver 
								= new StandardServletMultipartResolver();
		
		return resolver;
	}
	
	
}
