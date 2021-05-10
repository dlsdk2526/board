package org.ina.board.mapper;


import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ina.board.domain.ReplyVO;
import org.ina.common.page.PageDTO;

public interface ReplyMapper {
	
	int insert(ReplyVO vo);

	ReplyVO read(Long bno);
	
	int delete(Long rno);
	
	int update(ReplyVO reply);
	
	List<ReplyVO> getListWithPaging(@Param("pageDTO") PageDTO pageDTO,
								    @Param("bno") Long bno);
	
	int getCountByBno(Long bno);
}
