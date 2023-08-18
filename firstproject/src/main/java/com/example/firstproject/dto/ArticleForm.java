package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

// Lombok
@AllArgsConstructor
// 생성자 생략

@ToString
// toString 생략
public class ArticleForm {

    private Long id;
    private String title;
    private String content;
//    private List<Comment> comments;


//    public ArticleForm(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

//    @Override
//    public String toString() {
//        return "ArticleForm{" +
//                "title='" + title + '\'' +
//                ", content='" + content + '\'' + '}';
//    }
// update를 위한 dto 변격 - id 필드 추가 및 엔ㅌ티 변호나 메소드 변경
//    public Article toEntity() {
//        return new Article(id, title, content, comments);
//    }
    public Article toEntity() {
        return new Article(id, title, content);
    }

    // toEntity() 메소드는 새로운 Article 을 생성하는데
    // 기본인 id 는 null 로 설정
    // title 과 content 는 dto 에서 전달된 값으로 설정

    // DTO - 데이터 전송객체
    // 계층을 사이에서 데이터를 간단하고 구조화된 방식으로 캡슐화하고 전송하는 것

}
