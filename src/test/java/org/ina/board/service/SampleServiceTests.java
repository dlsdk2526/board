package org.ina.board.service;

import org.ina.board.config.BoardConfig;
import org.ina.common.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,BoardConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class SampleServiceTests {

	@Autowired
	private BoardService boardService;
	
	@Test
	public void testAOP() {
		log.info(boardService);
		log.info(boardService.getClass().getName());
	}

	
}
