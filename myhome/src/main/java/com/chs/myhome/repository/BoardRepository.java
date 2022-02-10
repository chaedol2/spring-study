package com.chs.myhome.repository;

import com.chs.myhome.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
//JPA에 있는 JpaRepository<model클래스, PK타입>으로 상속받는다.
public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findByTitle(String title);

    List<Board> findByTitleOrContent(String title, String content);
    
    //검색에 사용하는 쿼리
    Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

}
