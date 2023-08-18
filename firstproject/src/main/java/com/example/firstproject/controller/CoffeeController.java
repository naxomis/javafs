package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CoffeeForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Coffee;
import com.example.firstproject.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @GetMapping("articles/createcoffee")
    public String newCoffeeForm(){
        return "articles/cnew";
    }


    @PostMapping("articles/coffee/create")
    public String CrudRepository(CoffeeForm form){
        System.out.println(124544);
        log.info(form.toString());
        // form 안의 내용을 dto( ArticleForm 이라는 클래스 타입의 객체를 파라미터로 받는다.)

        // 1. Dto 를 Entity 변환

        Coffee coffee = form.toEntity();

        log.info(coffee.toString());

        Coffee saved = coffeeRepository.save(coffee);

        log.info(saved.toString());
        return "redirect:/articles/coffee/" + saved.getId();
    }
    @GetMapping("/articles/coffee/{id}")
    public String show(@PathVariable Long id, Model model){
        // url 에서 id를 변수로 가져옴
        log.info("id= " + id);

        Coffee coffeeEntity = coffeeRepository.findById(id).orElse(null);


        //
        model.addAttribute("coffee", coffeeEntity);

        // 3 : 보여줄 페이지를 설정
        return "/articles/cshow";

    }
    @GetMapping("articles/coffee")
    public String index(Model model){
        // 1 : 모든 Coffee 을 가져온다
        List<Coffee> coffeeEntityList = coffeeRepository.findAll();

        // 2 : 가져온 Articles 을 묶음을 뷰로 전달!
        model.addAttribute("coffeeList", coffeeEntityList);

        // 3 : 뷰페이지를 설정
        return "articles/coffee";
    }

    @GetMapping("/articles/coffee/{id}/cedit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터 가져오기
        Coffee coffeeEntity = coffeeRepository.findById(id).orElse(null);
        // 모델 데이터 등록
        model.addAttribute("coffee", coffeeEntity);
        // 뷰페이지 설정
        return "articles/cedit";
    }
    @PostMapping("/articles/coffee/cupdate")
    public String update(CoffeeForm form){
        log.info(form.toString());
        // 1. dto 를 엔티티로 변환
        Coffee coffeeEntity = form.toEntity();
        log.info(coffeeEntity.toString());


        // 2. 엔티티를 DB를 저장
        // 2-1 : DB에서 기존 데이터를 가져와서
        Coffee target = coffeeRepository.findById(coffeeEntity.getId()).orElse(null);
        // 2.2 : 기존 데이터가 있다면, 값을 갱신
        if(target != null){
            coffeeRepository.save(coffeeEntity);
        }
        // 3. 수정결과 페이지로 리다이렉드
        return "redirect:/articles/coffee/" + coffeeEntity.getId();
    }

    @GetMapping("articles/coffee/{id}/cdelete")
    public String delete(@PathVariable Long id ,
                         RedirectAttributes rttr){
        // 리다이렉트 후 다른 컨트롤러나 뷰로 데이터를 전달할때 쓰임
        log.info("삭제 요청이 들어왔습니다!!");
        // 삭제대상을 가져옴
        Coffee target = coffeeRepository.findById(id).orElse(null);
        log.info(target.toString());
        // 대상을 삭제
        if(target != null){
            coffeeRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제가 완료되었습니다.");
        }

        // 결과 페이지로 리다이렉트
        return "redirect:/articles/coffee/";
    }
}
