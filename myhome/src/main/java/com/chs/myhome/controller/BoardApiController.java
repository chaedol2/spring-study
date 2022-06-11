package com.chs.myhome.controller;

import com.chs.myhome.model.Board;
import com.chs.myhome.repository.BoardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    private final Logger LOGGER = LoggerFactory.getLogger(BoardApiController.class);

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards")
    //required=false 타이틀이 전달이 되면 타이틀하나출력 타이틀이 전달되지 않으면 전체를 리턴
    List<Board> all(@RequestParam(required=false) String title,
        @RequestParam(required = false, defaultValue = "") String content) {
        LOGGER.info("all 호출!");
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)){
            //둘다 비어있을경우 전체를 검색
            return repository.findAll();
        }else{
            //title 혹은 content 검색
            return repository.findByTitleOrContent(title, content);
        }
    }
    //포스트맨을통해 POST방식을 선택하고 BODY를 체크한 후
    //{ "title" : "테스트제목", "content" : "테스트내용" } 을 작성하여 send하면 데이터가 입력된다.
    //이때 RequestBody 어노테이션을 선언해준다
    //Postman으로 보내고 console을 누르면 보낸방법을 볼수있는데 javascript타입으로 컨텐츠 전송을할경우
    //Content-Type : "application/json"을 꼭 설정해주어야 json타입으로 보낼 수 있다.
    @PostMapping("/boards")
    Board newBoard(@RequestBody Board newBoard) {
        LOGGER.info("newBoard 호출!");
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        LOGGER.info("one 호출!");
        //orElse는 옵셔널로 없을경우 Null 지정
        return repository.findById(id).orElse(null);
    }

    //주로 업데이트를 담당
    //포스트맨을 통해 put으로 설정 후 데이터를 변경싶은 id에 해당하는 번호를 url에 적고
    //json타입으로 send한다.
    @PutMapping("/boards/{id}")
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {
        LOGGER.info("replaceBoard 호출!");

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/boards/{id}")
    void deleteBoard(@PathVariable Long id) {
        LOGGER.info("deleteBoard 호출!");
        repository.deleteById(id);
    }
}
