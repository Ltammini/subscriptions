package com.filmland.subscription.controller;

import com.filmland.subscription.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.filmland.subscription.dto.AllCategoriesDto;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private  CategoryService categoryService;

    /**
     *
     * @param username
     * @return
     */
    @GetMapping
    public AllCategoriesDto getCategory(@RequestParam("username") String username){
        return categoryService.getCategories(username);

    }

}
