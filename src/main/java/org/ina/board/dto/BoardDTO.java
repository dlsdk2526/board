package org.ina.board.dto;

import java.sql.Date;
import java.util.List;

import org.ina.board.domain.BoardAttachVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {

	private Long bno;
	private String title,content,writer;
	private Date regdate;
	private Date updatedate;
	
	private int replyCnt;
	
	private List<BoardAttachVO> attachList;
}
