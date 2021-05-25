package org.ina.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ina.board.domain.BoardVO;
import org.ina.common.page.PageDTO;

public interface BoardMapper {

	List<BoardVO> getList(@Param("skip")int skip,
						  @Param("perSheet")int perSheet,
						  @Param("arr")String[] type,
						  @Param("keyword")String keyword);
		
	int getTotal(@Param("arr")String[] type,
				 @Param("keyword")String keyword);
	
	void insert(BoardVO boardVO);
	
	void insertSelectkey(BoardVO boardVO);
	
	BoardVO read(Long bno);
	
	int delete(Long bno);
	
	int update(BoardVO boardVO);
	
	void updateReplyCnt(@Param("bno") Long bno,
						@Param("amount") int amount); //bno의 증가나 감소를 의미
	
	
}
