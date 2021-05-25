package org.ina.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.log4j.Log4j;

//aop를 이용하는 방식, 공통적인 예외사항을 별도로 분리하는 방식 

//@ControllerAdvice //컨트롤러에서 발생하는 예외를 처리하는 존재를 명시
@Log4j
public class CommonExceptionAdvice {
	
	//() 안에 들어가는 예외타입을 처리 
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		
		log.error("exception........."+ ex.getMessage());
		model.addAttribute("exception",ex);
		log.error(model);
		
		return "error_page";
	}

}
