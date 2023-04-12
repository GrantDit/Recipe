package pro.sky.recipe.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
    @GetMapping("/")
    public String status() {
        return "приложение запущено";
    }

    @GetMapping("/info")
    public String information() {
        return "Студент: Гавриш Кирилл " +
                " Название приложения: Recipes " +
                " Дата: 23.02.23" +
                " Описание: Приложение рецептов ";
    }
}