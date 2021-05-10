package org.ina.board.domain;


import java.sql.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardVO {

	private Long bno;
	private String title,content,writer;
	private Date regdate;
	private Date updatedate;
	
	private int replyCnt;
	
	private List<BoardAttachVO> attachList;
}

