package org.ina.security;

import org.ina.board.config.BoardConfig;
import org.ina.board.domain.MemberVO;
import org.ina.board.mapper.MemberMapper;
import org.ina.common.config.RootConfig;
import org.ina.common.config.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,
								 BoardConfig.class,
								 SecurityConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class MemberMapperTests {
	
	@Autowired
	MemberMapper memberMapper;

	@Test
	public void testRead() {
		
		MemberVO memberVO = memberMapper.read("admin90");
		
		log.info(memberVO);
		
		memberVO.getAuthList().forEach(a -> log.info(a));
	}
}
