package org.ina.board.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.cj.jdbc.admin.MiniAdmin;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample")
@Controller
public class SampleController {
	
	@GetMapping("/all")
	public void doAll() {
		log.info("all");
	}
	
	@GetMapping("/member")
	public void doMember() {
		log.info("member");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("admin");
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MEMBER')")
	@GetMapping("/annoMember")
	public void doMember2() {
		
		log.info("logined annotation member");
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/annoAdmin")
	public void doAdmin2() {
		log.info("admin annotation only");
	}
	

	
}
