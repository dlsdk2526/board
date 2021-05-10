package org.ina.board.controller;

import org.ina.board.dto.BoardDTO;
import org.ina.board.service.BoardService;
import org.ina.common.page.PageDTO;
import org.ina.common.page.PageMaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService boardService;
	
	@GetMapping("/list")
	public void list(Model model,PageDTO pageDTO) {
		log.info("list!!");
		model.addAttribute("list", boardService.getList(pageDTO));
		model.addAttribute("pageMaker",new PageMaker(pageDTO, boardService.getTotal(pageDTO)));
		
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	@PostMapping("/register")
	public String register(BoardDTO boardDTO, RedirectAttributes rttr) {
		log.info("register: "+ boardDTO);
		
		Long bno = boardService.register(boardDTO);
		log.info("newBnoLong: " + bno);
		
		//등록작업이 끝나고 다시 목록화면으로 이동할 때 새롭게 등록된 게시물의 번호를 같이 전달하기 위한것
		rttr.addFlashAttribute("result", bno);
		
		return "redirect:/board/list";
	}
	
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, Model model, PageDTO pageDTO) {
		
		log.info("get!!!!");
		model.addAttribute("board", boardService.get(bno));
	}
	
	@GetMapping("/modify")
	public void modify(Long bno, Model model,PageDTO pageDTO) {
		
		model.addAttribute("board", boardService.get(bno));
	}
	
	@PostMapping("/modify")
	public String register(BoardDTO boardDTO, PageDTO pageDTO, RedirectAttributes rttr) {
		
		log.info("modify: "+ boardDTO);
		
		log.info("modify pageDTO: "+ pageDTO);
		
		boardService.modify(boardDTO);
		
		rttr.addAttribute("bno", boardDTO.getBno());
		rttr.addAttribute("page", pageDTO.getPage());
		rttr.addAttribute("perSheet", pageDTO.getPerSheet());
		
		return "redirect:/board/get";
	}

	@PostMapping("/remove")
	public String remove(Long bno,BoardDTO boardDTO,PageDTO pageDTO, RedirectAttributes rttr) {
		
		log.info("remove bno :" + bno);
		boardService.remove(bno);
		
		rttr.addAttribute("bno", boardDTO.getBno());
		rttr.addAttribute("page", pageDTO.getPage());
		rttr.addAttribute("perSheet", pageDTO.getPerSheet());
		
		return "redirect:/board/list";
	}
	
	

}





