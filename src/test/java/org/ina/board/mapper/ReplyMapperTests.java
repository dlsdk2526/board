package org.ina.board.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.ina.board.config.BoardConfig;
import org.ina.board.domain.BoardVO;
import org.ina.board.domain.ReplyVO;
import org.ina.board.dto.BoardDTO;
import org.ina.common.config.RootConfig;
import org.ina.common.page.PageDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RootConfig.class,BoardConfig.class}) //스프링이 실행되면서 어떤 설정 정보를 읽어 들여야 하는지 명시한다
@Log4j
public class ReplyMapperTests {
	
	private Long[] bnoArr = {1549L,1547L,1544L,1545L,1535L};
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Test
	public void testMapper() {
		log.info(replyMapper);
	}
	
	@Test
	public void testInsert() {
	
			ReplyVO vo = ReplyVO.builder().bno(1547L).reply("내용테스트").replyer("작성자테스트").build();
			
			replyMapper.insert(vo);
			
		
	}
	
	@Test
	public void testRead() {
		Long targetRno = 5L;
		
		ReplyVO vo = replyMapper.read(targetRno);
		
		log.info(vo);
	}
	
	@Test
	public void testDelete() {
		
		replyMapper.delete(1L);
	}
	
	@Test
	public void testUpdate() {
		Long targetRno = 10L;
		
		ReplyVO vo = replyMapper.read(targetRno);
		
		vo.setReply("update");
		int count = replyMapper.update(vo);
		
		log.info("update: "+ count);
	}
	
	@Test
	public void testList() {
		PageDTO dto = new PageDTO();
		
		List<ReplyVO> replies = replyMapper.getListWithPaging(dto, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
	}
	
	@Test
	public void testList2() {
		PageDTO pageDTO = new PageDTO(1, 10, null, null);
		List<ReplyVO> replies = replyMapper.getListWithPaging(pageDTO, 1533L);
		
		replies.forEach(reply -> log.info(reply));
	}
}









