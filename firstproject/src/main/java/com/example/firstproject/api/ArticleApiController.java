package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;

import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired  // 불러오기
    private ArticleService articleService;

    // get
    // 전체
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleService.index();
    }

    // id로 불러오기
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleService.show(id);
    }

    // post
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        Article created = articleService.create(dto);
        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(null) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // patch
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                          @RequestBody ArticleForm dto){
        Article updated = articleService.update(id, dto);
        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    // ResponseEntity.status(HttpStatus.NO_CONTENT) 204 NO_CONTENT 를 가진 응답 반환
    // 요청이 성공적으로 처리되었음을 알린다. 또한 응답 본문에는 데이터가 반환되지 않는다.


    // 트랜젝션     -> 예외발생     -> 롤백
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>>transactionTest(@RequestBody List<ArticleForm> dtos){
        // Article 을 리스트(묶음) 단위 전송, ResponseEntity 로 감싸서 받음
        List<Article> createList = articleService.createArticle(dtos);
        return (createList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
