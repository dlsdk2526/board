package org.ina.board.controller;

import java.io.Console;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.ina.board.domain.BoardAttachVO;
import org.ina.board.dto.BoardDTO;
import org.ina.board.service.BoardService;
import org.ina.common.page.PageDTO;
import org.ina.common.page.PageMaker;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	@PreAuthorize("isAuthenticated()")
	public void register() {
		
	}
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String register(BoardDTO boardDTO, RedirectAttributes rttr) {
		log.info("register: "+ boardDTO);
		
		if (boardDTO.getAttachList() != null) {
			boardDTO.getAttachList().forEach(attach -> log.info(attach));
		}
		
		boardService.register(boardDTO);
		
		//등록작업이 끝나고 다시 목록화면으로 이동할 때 새롭게 등록된 게시물의 번호를 같이 전달하기 위한것
		rttr.addFlashAttribute("result", boardDTO.getBno());
		
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
	
	@PreAuthorize("principal.username == #boardDTO.writer")
	@PostMapping("/modify")
	public String register(BoardDTO boardDTO, PageDTO pageDTO,String writer, RedirectAttributes rttr) {
		
		log.info("modify: "+ boardDTO);
		
		log.info("writer: " + writer);
		
		log.info("modify pageDTO: "+ pageDTO);
		
		boardService.modify(boardDTO);
		
		rttr.addAttribute("bno", boardDTO.getBno());
		rttr.addAttribute("page", pageDTO.getPage());
		rttr.addAttribute("perSheet", pageDTO.getPerSheet());
		
		return "redirect:/board/get";
	}

	
	@PreAuthorize("principal.username == #writer") //파라미터 writer = 실제로그인한 userid
	@PostMapping("/remove")
	public String remove(Long bno,BoardDTO boardDTO,PageDTO pageDTO,RedirectAttributes rttr) {
		
		log.info("remove bno :" + bno);
		
		
		List<BoardAttachVO> attachList = boardService.getAttachList(bno);
		
		if (boardService.remove(bno)) {
			deleteFiles(attachList);
			rttr.addFlashAttribute("result","success");
		}
		
//		rttr.addAttribute("bno", boardDTO.getBno());
//		rttr.addAttribute("page", pageDTO.getPage());
//		rttr.addAttribute("perSheet", pageDTO.getPerSheet());
		
		return "redirect:/board/list" + pageDTO.getListLink();
	}
	
	//RestController로 작성되지 않았기 때문에 직접 responseBody를 적용해서 JSON데이터 반환하도록 처리한다.
	@GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("get attach list: " +bno);
		
		return new ResponseEntity<>(boardService.getAttachList(bno),HttpStatus.OK);
	}
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		
		if (attachList == null || attachList.size() == 0 ) {
			return;
		}
		
		log.info("delete attach files......");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\upload\\"+ attach.getUploadPath()+"\\"+ attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);
				
				//파일이 이미지라면 썸네일도 삭제
				if (Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\"+ attach.getUploadPath()+"\\"+ attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbNail);
				}
			
			} catch (Exception e) {
				log.error("delete file error" + e.getMessage());
			}
			
		});//end foreach
	}
	
	
	
	
}


