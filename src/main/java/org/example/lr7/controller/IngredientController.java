package org.example.lr7.controller;

import org.example.lr7.model.entity.IngredientsCategoryEntity;
import org.example.lr7.model.entity.IngredientsItemEntity;
import org.example.lr7.request.IngrediantRequest;
import org.example.lr7.request.IngredientCategoryRequest;
import org.example.lr7.service.IngredientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

    @Autowired
    private IngredientsService ingredientsService;


    @PostMapping("/category")
    public ResponseEntity<IngredientsCategoryEntity> createIngredientsCategory(
            @RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientsCategoryEntity item = ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PostMapping()
    public ResponseEntity<IngredientsItemEntity> createIngredientsItem(
            @RequestBody IngrediantRequest req) throws Exception {
        IngredientsItemEntity item = ingredientsService.createIngredientItem(req.getRestaurantId(), req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }
    @PutMapping("/{id}/stoke")
    public ResponseEntity<IngredientsItemEntity> updateIngredientStock(
            @PathVariable Long id) throws Exception {
        IngredientsItemEntity item = ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<List<IngredientsItemEntity>> getRestaurantIngredient(
            @PathVariable Long id) throws Exception {
        List<IngredientsItemEntity> item = ingredientsService.findRestaurantIngredients(id); ///////тут ошибка
        //Incompatible types. Found: 'java.util.List<org.example.lr7.model.entity.IngredientsCategoryEntity>',
        // required: 'java.util.List<org.example.lr7.model.entity.IngredientsItemEntity>'
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/restaurant/{id}/category")
    public ResponseEntity<List<IngredientsCategoryEntity>> getRestaurantIngredientCategory(
            @PathVariable Long id
    ) throws Exception {
        // ОШИБКА БЫЛА ТУТ: метод возвращает список КАТЕГОРИЙ, а в ResponseEntity был указан List<IngredientsItemEntity>
        List<IngredientsCategoryEntity> item = ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }


}
