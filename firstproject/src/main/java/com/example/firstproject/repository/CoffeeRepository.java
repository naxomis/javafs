package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.entity.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
    @Override
    ArrayList<Coffee> findAll();
}
