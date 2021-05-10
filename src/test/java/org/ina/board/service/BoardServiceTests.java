package org.ina.board.service;

import static org.junit.Assert.assertNotNull;

import org.ina.board.config.BoardConfig;
import org.ina.board.dto.BoardDTO;
import org.ina.common.config.RootConfig;
import org.ina.common.page.PageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,BoardConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class BoardServiceTests {
	
	@Autowired
	private BoardService boardService;
	
	@Test
	public void testExist() {
		log.info(boardService);
		assertNotNull(boardService);
	}
	
	@Test
	public void testRegister() {
		
		BoardDTO dto = BoardDTO.builder().title("service").content("service").writer("service").build();
		boardService.register(dto);
	}
	
	@Test
	public void testList() {
		
		PageDTO pageDTO = PageDTO.builder().page(1).perSheet(10).type("tc").keyword("up").build();
		boardService.getList(pageDTO).forEach(list -> log.info(list));
	}
	
	@Test
	public void testTotal() {
		PageDTO pageDTO = PageDTO.builder().page(1).perSheet(10).type("tc").keyword("up").build();
		log.info(boardService.getTotal(pageDTO));
	}
	
	@Test
	public void testUpdate() {
		
		BoardDTO dto = BoardDTO.builder().bno(57L).title("service").content("service").writer("service").build();
		boardService.modify(dto);
	}
	
	@Test
	public void testDelete() {
		boardService.remove(37L);
	}

}
