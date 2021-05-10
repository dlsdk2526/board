package org.ina.common.config;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

import org.ina.board.config.BoardConfig;
import org.ina.time.config.TimeConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebConfig extends AbstractAnnotationConfigDispatcherServletInitializer {

	// ContextLoaderListener가 생성한 애플리케이션 컨텍스트를 설정하는 데 사용된다.
	@Override
	protected Class<?>[] getRootConfigClasses() { //프로그램을 실행하기 위한 설정, 그것들을 찾는다. 
		return new Class[] {RootConfig.class,
							WebConfig.class,
							TimeConfig.class,
							BoardConfig.class};
	}

	//DispatcherServlet이 애플리케이션 컨텍스트를 WebConfig 설정 클래스(java 설정)에서 정의된 빈으로 로딩하도록 되어있다.
	@Override
	protected Class<?>[] getServletConfigClasses() { //url을 치고 들어가는 순간 로드 , 컨트롤러 담당
		return new Class[] {ServletConfig.class};
	}

	//DispatcherServlet이 매핑되기 위한 하나 혹은 여러 개의 패스를 지정한다.
	//코드에서는 애플리케이션 기본 서블릿인 /에만 매핑이 되어 있다. 그리고 이것은 애플리케이션으로 들어오는 모든 요청을 처리한다.
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);

		return new Filter[] { characterEncodingFilter };
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {

		registration.setInitParameter("throwExceptionIfNoHandlerFound", "true");
		
		MultipartConfigElement multipartConfig = new MultipartConfigElement("C:\\upload\\temp",20971520, 41943040, 20971520);
		registration.setMultipartConfig(multipartConfig);
	}


}
