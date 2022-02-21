package com.example.recipe_blog.repository;


import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.recipe_blog.model.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
    List<Recipe> findAllByCategoryIgnoreCaseOrderByDateDesc(String category);

}
