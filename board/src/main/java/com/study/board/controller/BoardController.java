package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.criteria.CriteriaBuilder;

@Controller // 스프링이 controller 라고 알 수 있게함
public class BoardController {

  @Autowired
  private BoardService boardService;

  @GetMapping("/board/write") // 어떤 url 주소로 접근할지 지정 -> localhost:8080/board/write 로 접근
  public String boardWriteForm() {
    return "Boardwrite"; // 어떤 html 파일을 뷰파일로 보여줄 건지
  }

  @PostMapping("board/writepro") // 여기 url과 'boardwrite.html' 파일의 form action의 주소가 같아야한다.
  public String boardWritePro(Board board, Model model) {
    boardService.write(board);
    model.addAttribute("message", "글 작성이 완료되었습니다.");
    model.addAttribute("searchUrl", "/board/list");
    return "message";
  }

  @GetMapping("/board/list")
  public String boardlist(Model model) {
    model.addAttribute("list", boardService.boardList());
    return "boardlist";
  }

  @GetMapping("/board/view") // http://localhost:8080/board/view?id=1
  public String boardview(Model model, Integer id) {
    model.addAttribute("board", boardService.boardView(id));
    return "boardview";
  }

  @GetMapping("/board/delete")
  public String boardDelete(Integer id) {
    boardService.boardDelete(id);
    return "redirect:/board/list";
  }

  @GetMapping("/board/modify/{id}")
  public String boardModify(@PathVariable("id") Integer id,
                            Model model) {
    model.addAttribute("board", boardService.boardView(id));
    return "boardmodify";
  }

  @PostMapping("/board/update/{id}")
  public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model) {
    Board boardTemp = boardService.boardView(id);
    boardTemp.setTitle(board.getTitle());
    boardTemp.setContent(board.getContent());
    boardService.write(boardTemp);
    model.addAttribute("message", "글 수정이 완료되었습니다.");
    model.addAttribute("searchUrl", "/board/list");
    return "message";
  }

}
