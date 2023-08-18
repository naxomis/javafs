package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")    // Article 에서 id 를 가져오기 위해서 article_id로 이름을 줌

    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;



    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );

    }
    // 수정하기
    public void patch(CommentDto dto) {
        // 예외 발생
        if(this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");

        // 객체 갱신
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname();

        if(dto.getBody() != null)
            this.body = dto.getBody();

    }

}
