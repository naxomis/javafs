package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // get
    public List<Article> index() {
        return articleRepository.findAll();
    }
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // post
    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if(article.getId() != null){
            return null;
        }   // 기존데이터 방지
        return articleRepository.save(article);
    }


    // patch
    public Article update(Long id, ArticleForm dto) {
        // 1. dto -> 엔티티
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 2. 타겟조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청처리
        if(target == null || id != article.getId()){
            // 400, 잘못된 요청 응답
            log.info("id: {}, article: {}", id, article.toString());
            return null;
        }
        // 4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }

    // DELETE
    public Article delete(Long id) {
        // 대상 찾기
        Article target =articleRepository.findById(id).orElse(null);
        // 잘못된 요청 처리
        if(target == null){
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }

    @Transactional
    public List<Article> createArticle(List<ArticleForm> dtos) {
//        List<Article> articleList = new ArrayList<>();
//        for(int i = 0; i < dtos.size(); i++){
//            ArticleForm dto = dtos.get(i);
//            Article entity = dto.toEntity();
//            articleList.add(entity);
//        }
        // 위의 내용을 축약한 것(스트림문법)
        List<Article> articleList = dtos.stream()
                .map(dto ->dto.toEntity())
                .collect(Collectors.toList());

        // entity 리스트로 만들기 위해 map 을 통해 스트림화 하여
        // dto 가 올때마다
        // entity 로 매핑된 것을 리스트로 변환
//        for(int i = 0; i<articleList.size(); i++){
//            Article article = articleList.get(i);
//            // get(i) 하나씩 꺼낸다.
//            articleRepository.save(article);
//        }
        // 위의 내용을 축약한 것(스트림문법)
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow (
                ()-> new IllegalCallerException("결재실패!!")
        );

        // 결과값 반환
        return articleList;
    }
}
