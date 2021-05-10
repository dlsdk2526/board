package org.ina.board.service;

import java.util.List;

import org.ina.board.domain.ReplyVO;
import org.ina.board.dto.ReplyPageDTO;
import org.ina.board.mapper.BoardMapper;
import org.ina.board.mapper.ReplyMapper;
import org.ina.common.page.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class ReplySericeImpl implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	@Autowired
	BoardMapper boardMapper;

	
	@Transactional
	@Override
	public int register(ReplyVO vo) {
		
		boardMapper.updateReplyCnt(vo.getBno(), 1);
		
		return replyMapper.insert(vo);
	}

	@Override
	public ReplyVO get(Long rno) {
		return replyMapper.read(rno);
	}

	@Override
	public int modify(ReplyVO vo) {
		// TODO Auto-generated method stub
		return replyMapper.update(vo);
	}

	
	@Transactional
	@Override
	public int remove(Long rno) {
		
		ReplyVO vo = replyMapper.read(rno);
		
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		
		return replyMapper.delete(rno);
	}

	@Override
	public List<ReplyVO> getList(PageDTO pageDTO, Long bno) {
		
		return replyMapper.getListWithPaging(pageDTO, bno);
	}

	@Override
	public ReplyPageDTO getListPage(PageDTO pageDTO, Long bno) {
		
		return new ReplyPageDTO(replyMapper.getCountByBno(bno),replyMapper.getListWithPaging(pageDTO, bno));
	}
	
}
