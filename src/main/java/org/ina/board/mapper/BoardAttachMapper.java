package org.ina.board.mapper;

import java.util.List;

import org.ina.board.domain.BoardAttachVO;

public interface BoardAttachMapper {
	
	void insert(BoardAttachVO vo);
	
	void delete(String uuid);
	
	List<BoardAttachVO> findByBno(Long bno);
	
	void deleteAll(Long bno);

	List<BoardAttachVO> getOldFiles();
}
