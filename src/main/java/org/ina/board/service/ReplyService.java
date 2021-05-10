package org.ina.board.service;

import java.util.List;

import org.ina.board.domain.ReplyVO;
import org.ina.board.dto.ReplyPageDTO;
import org.ina.common.page.PageDTO;

public interface ReplyService {

	int register(ReplyVO vo);
	
	ReplyVO get(Long rno);
	
	int modify(ReplyVO vo);
	
	int remove(Long rno);
	
	List<ReplyVO> getList(PageDTO pageDTO, Long bno);
	
	ReplyPageDTO getListPage(PageDTO pageDTO, Long bno);
}
