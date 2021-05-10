package org.ina.board.service;

import java.util.List;

import org.ina.board.domain.BoardVO;
import org.ina.board.dto.BoardDTO;
import org.ina.common.page.PageDTO;

public interface BoardService {

	Long register(BoardDTO boardDTO);
	
	BoardDTO get(Long bno);
	
	void modify(BoardDTO boardDTO);
	
	void remove(Long bno);
	
	List<BoardDTO> getList(PageDTO pageDTO);
	
	int getTotal(PageDTO pageDTO);
	
	default BoardDTO toDTO(BoardVO vo) {
		BoardDTO dto = new BoardDTO();
		
		dto.setBno(vo.getBno());
		dto.setContent(vo.getContent());
		dto.setRegdate(vo.getRegdate());
		dto.setTitle(vo.getTitle());
		dto.setUpdatedate(vo.getUpdatedate());
		dto.setWriter(vo.getWriter());
		
		return dto;
	}
	
	default BoardVO toDomain(BoardDTO dto) {
		
		BoardVO boardVO = BoardVO.builder()
				.bno(dto.getBno())
				.content(dto.getContent())
				.regdate(dto.getRegdate())
				.updatedate(dto.getUpdatedate())
				.title(dto.getTitle())
				.writer(dto.getWriter()).build();
		
		return boardVO;
	}
}
