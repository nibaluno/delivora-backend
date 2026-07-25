package org.example.lr7.controller;

import org.example.lr7.model.entity.CategoryEntity;
import org.example.lr7.model.entity.UserEntity;
import org.example.lr7.repository.CategoryRepository;
import org.example.lr7.service.CategoryService;
import org.example.lr7.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<CategoryEntity> createCategory(@RequestBody CategoryEntity category,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        CategoryEntity createCategory = categoryService.createCategory(category.getName(), user.getId());
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);

    }

    @GetMapping("/category/restaurant")
    public ResponseEntity<List<CategoryEntity>> getRestaurantCategory(
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        UserEntity user = userService.findUserByJwtToken(jwt);
        List<CategoryEntity> categories = categoryService.findCategoryByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.CREATED);

    }
}
