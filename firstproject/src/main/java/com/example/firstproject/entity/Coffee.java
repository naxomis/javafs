package com.example.firstproject.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Coffee {
    @Id // id 라는 것을 알려주는것 -> (PRIMARY KEY (id))
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")     // 숫자를 한개씩 증가시킨다.
    // generator = "increment" - 숫자를 순차적으로 증가시켜줌(더미데이터를 사용시 꼭 추가)
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column     // 쓰는게 좋음
    private String name;

    @Column     // 쓰는게 좋음
    private Integer price;

    public void patch(Coffee coffee) {
        if(coffee.name != null)
            this.name = coffee.name;
        if(coffee.price != null)
            this.price = coffee.price;
    }
}
