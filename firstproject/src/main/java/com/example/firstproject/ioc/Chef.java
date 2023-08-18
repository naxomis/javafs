package com.example.firstproject.ioc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component      //스프링부트의 DI
public class Chef {
    @Autowired
    private IngredientFactory ingredientFactory;

    public Chef(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    public String cook(String menu){
        // 재료준비
        // Pork pork = new Pork("한돈 등심");
//        Beef beef = new Beef("한우 꽃등심");
        Ingredient ingredient = ingredientFactory.get(menu);
        // 요리반환
        // return pork.getName() + "으로 만든 " + menu;
//        return beef.getName() + "으로 만든 " + menu;
        return ingredient.getName() + "으로 만든 " +menu;

    }
}
