package org.ina.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ina.board.domain.BoardVO;
import org.ina.board.dto.BoardDTO;
import org.ina.board.mapper.BoardMapper;
import org.ina.common.page.PageDTO;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@RequiredArgsConstructor
@Log4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper boardmapper;
	
	
	@Override
	public Long register(BoardDTO boardDTO) {
		
		BoardVO vo = toDomain(boardDTO);
		
		boardmapper.insertSelectkey(vo);
		
		return vo.getBno();
	}

	@Override
	public BoardDTO get(Long bno) {
		return toDTO(boardmapper.read(bno));
	}

	@Override
	public void modify(BoardDTO boardDTO) {
		
		boardmapper.update(toDomain(boardDTO));
	}

	@Override
	public void remove(Long bno) {

		boardmapper.delete(bno);
	}

	@Override
	public List<BoardDTO> getList(PageDTO pageDTO) {
		
		List<BoardVO> voList = boardmapper.getList(pageDTO.getPage(),
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

}






