package org.ina.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.ina.board.domain.BoardAttachVO;
import org.ina.board.domain.BoardVO;
import org.ina.board.dto.BoardDTO;
import org.ina.board.mapper.BoardAttachMapper;
import org.ina.board.mapper.BoardMapper;
import org.ina.common.page.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	BoardMapper boardmapper;
	@Autowired
	BoardAttachMapper attachMapper;
	
	
	@Transactional
	@Override
	public Long register(BoardDTO boardDTO) {
		
		BoardVO vo = toDomain(boardDTO);
		boardmapper.insertSelectkey(vo);
		
		if (boardDTO.getAttachList() == null || boardDTO.getAttachList().size() <= 0) {
			return vo.getBno();
		}
		
		boardDTO.getAttachList().forEach(attach ->{
			attach.setBno(vo.getBno());
			attachMapper.insert(attach);
		});
		
		return vo.getBno();
	}

	@Override
	public BoardDTO get(Long bno) {
		return toDTO(boardmapper.read(bno));
	}
	

	@Transactional
	@Override
	public boolean modify(BoardDTO boardDTO) {
		
		log.info("modify........"+ boardDTO);
		BoardVO vo = toDomain(boardDTO);
		
		attachMapper.deleteAll(vo.getBno()); //일단 이미지 전체 삭제
		
		boolean modifyResult = boardmapper.update(vo) == 1;
		
		if (modifyResult && boardDTO.getAttachList() != null && boardDTO.getAttachList().size() > 0) {
			
			boardDTO.getAttachList().forEach(attach -> {
				attach.setBno(vo.getBno());
				attachMapper.insert(attach);
			});
		}
		return modifyResult;
	}
		

	@Transactional
	@Override
	public boolean remove(Long bno) {

		log.info("delete bno....:" + bno);
		attachMapper.deleteAll(bno);
		 
		return boardmapper.delete(bno) == 1;
		
	}

	@Override
	public List<BoardDTO> getList(PageDTO pageDTO) {
		
		List<BoardVO> voList = boardmapper.getList(pageDTO.getSkip(),
												   pageDTO.getPerSheet(),
												   pageDTO.getArr(),
												   pageDTO.getKeyword());
		
		List<BoardDTO> dtoList = voList.stream().map(vo -> {
			
			BoardDTO boardDTO = BoardDTO.builder()
						.bno(vo.getBno())
						.title(vo.getTitle())
						.content(vo.getContent())
						.writer(vo.getWriter())
						.regdate(vo.getRegdate())
						.updatedate(vo.getUpdatedate())
						.replyCnt(vo.getReplyCnt())
						.build();
			
			return boardDTO;
	
		}).collect(Collectors.toList());
		
		return dtoList;
	}

	@Override
	public int getTotal(PageDTO pageDTO) {
		return boardmapper.getTotal(pageDTO.getArr(), pageDTO.getKeyword());
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {

		log.info("get attach list by bno: " + bno);
		
		return attachMapper.findByBno(bno);
	}

}






