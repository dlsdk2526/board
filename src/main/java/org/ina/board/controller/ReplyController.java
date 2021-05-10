package org.ina.board.controller;


import org.ina.board.domain.ReplyVO;
import org.ina.board.dto.ReplyPageDTO;
import org.ina.board.service.ReplyService;
import org.ina.common.page.PageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/replies/")
@Log4j
@RequiredArgsConstructor
public class ReplyController {

	private final ReplyService replyService;
	
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> create(@RequestBody ReplyVO vo){
		log.info("ReplyVO : "+ vo);
		
		int insertCount = replyService.register(vo);
		
		log.info("Reply INSERT COUNT: " +insertCount);
		
		return insertCount ==1 ? 
				  new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/pages/{bno}/{page}",
			produces = {MediaType.APPLICATION_XML_VALUE,
						MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("bno")Long bno,@PathVariable("page")int page){
		
		log.info("getList======================");
		PageDTO pageDTO = new PageDTO(page, 10, null, null);
		
		log.info("get Reply List bno:" + bno);
		
		log.info(pageDTO);
		return new ResponseEntity<>(replyService.getListPage(pageDTO, bno), HttpStatus.OK);
		
	}
	
	//조회
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno){
		
		log.info("get: "+ rno);
		return new ResponseEntity<>(replyService.get(rno),HttpStatus.OK);
	}
	
	//삭제
	@DeleteMapping(value = "/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove (@PathVariable("rno")Long rno){
		
		log.info("remove: "+ rno);
		
		return replyService.remove(rno) == 1 ? 
				  new ResponseEntity<>("success", HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH},
			value = "/{rno}",
			consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo,@PathVariable("rno")Long rno){
		
		//파라미터로 전달되는 댓글 번호 
		vo.setRno(rno);
		log.info("rno: "+ rno);
		
		log.info("modify: "+ vo);
		return replyService.modify(vo) == 1 ?
				 new ResponseEntity<>("success", HttpStatus.OK)
				:new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
}
