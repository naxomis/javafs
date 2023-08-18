package com.example.firstproject.dto;

import com.example.firstproject.entity.Coffee;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;


@AllArgsConstructor

@ToString
public class CoffeeForm {

    private Long id;

    private String name;

    private Integer price;

    public Coffee toEntity() {
        return new Coffee(id, name, price);
    }
}
