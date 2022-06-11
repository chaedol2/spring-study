package com.chs.myhome.controller;

import com.chs.myhome.model.Board;
import com.chs.myhome.repository.BoardRepository;
import com.chs.myhome.service.BoardService;
import com.chs.myhome.validator.BoardValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    private final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);

    //boardRepository Defendency Injection 해준다.
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    //boardValidator를 Dependency Injection 해준다.
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam (required = false, defaultValue = "")String searchText){
        LOGGER.info("list 출력!");
        //위에서 Pageable객체를 생성후 대입해주어도 된다.
//        Page<Board> boards = boardRepository.findAll(pageable);

        //위에서 Pagealbe객체를 생성하지 않은경우 아래와같이 사용해도된다.
       //0부터 20개까지 한페이지에 담아서 출력
//        Page<Board> boards = boardRepository.findAll(PageRequest.of(0,20));
        //boards.getTotalElements()를 사용하면 페이지의 전체 개수를 알수 있는 메소드이다. ->이 메소드를 화면에 thymeleaf를 통해 사용한다.
        //boards.getTotalPages()메소드로 총 몇페이지가 있는지 알 수 있다.
        //boards.getPageable()메소드로 페이지 정보를 사용할 수 있다.
        //boards.getPageable().getPageNumber()메소드로 페이지 숫자를 가져올 수 있다. _> thymeleaf로 each문을 통해 페이징 갯수구함

        //검색하는 쿼리 받아오기
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        //첫페이지는 자바의 Math클래스의 max메소드를 사용하여 1보다 작은값이 오지 않게 하고 현재페이지에 -4를 한다.
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
        //마지막페이지는 boards.getTotalpages()를 사용하여 끝페이지값을 넣고 현재페이지에 +4를 한다.
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        LOGGER.info("form 출력!");
        if(id == null){
            model.addAttribute("board", new Board());
        } else {
            //orElse는 자바에서 지원해주는 Optional메소드로 id값이 없을경우 null처리
            Board board = boardRepository.findById(id).orElse(null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    //@Valid 어노테이션을 설정해주고 model Board에 설정한 @Notnull, @Size 어노테이션이 부합하지 않으면 bindingResult.hasErros() 메소드가 호출된다.
    public String postform(@Valid Board board, BindingResult bindingResult, Authentication authentication){
        LOGGER.info("postform 출력!");
        //보드클래스에 바인딩리절트를 넘겨준다.
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()){
            return "board/form";
        }
//        Authentication a = SecurityContextHolder.getContext().getAuthentication(); //인증정보 가져오기에서 다양한 방법중에 이런방법도 있다.
        String username = authentication.getName();
        boardService.save(username, board);
        boardRepository.save(board);
        return "redirect:/board/list";
    }
}
