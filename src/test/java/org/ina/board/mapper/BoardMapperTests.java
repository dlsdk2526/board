package org.ina.board.mapper;

import org.ina.board.config.BoardConfig;
import org.ina.board.domain.BoardVO;
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
public class BoardMapperTests {
	
	@Autowired 
	private BoardMapper boardMapper;
	
	@Test
	public void testList() {
		
		String[] arr = {"w","c"};

		boardMapper.getList(0,10,arr,"up").forEach(board -> log.info(board));
	}
	
	@Test
	public void testTotal() {
		log.info("total====:"+ boardMapper.getTotal(null, null));
	}

	@Test
	public void testInsert() {
		
		BoardVO vo = BoardVO.builder().title("insert").content("content").writer("writer").build();
		boardMapper.insert(vo);
		
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO vo = BoardVO.builder().title("key").content("key").writer("key").build();
		boardMapper.insertSelectkey(vo);
		log.info(vo);
	}
	
	@Test
	public void testRead() {
		log.info(boardMapper.read(38L));
	}
	
	@Test
	public void testDelete() {
		boardMapper.delete(37L);
	}
	
	@Test
	public void testUpdate() {
		BoardVO vo = BoardVO.builder().bno(34L).title("up").content("up").writer("up").build();
		boardMapper.update(vo);
		
	}

}









