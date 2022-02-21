package com.example.recipe_blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.recipe_blog.model.Recipe;
import com.example.recipe_blog.model.User;
import com.example.recipe_blog.repository.RecipeRepository;

import java.util.*;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    public Recipe save(Recipe toSave, User user) {
        toSave.setUser(user);
        return recipeRepository.save(toSave);
    }

    public void delete(Recipe recipe) {
        recipeRepository.delete(recipe);
    }

    public void deleteById(Long id, String currentUserName) {
        authorizer(id, currentUserName);
        recipeRepository.deleteById(id);
    }

    public void updateById(Recipe updatedRecipe, Long id, User user) {
        authorizer(id, user.getEmail());
        updatedRecipe.setId(id);
        updatedRecipe.setUser(user);
        recipeRepository.save(updatedRecipe);
    }

    public List<Recipe> searchByNameOrCategory(String name, String category) {
        if ( (name.equals("empty") && category.equals("empty")) ||
                (!name.equals("empty") && !category.equals("empty"))
        ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else if (!name.equals("empty") && category.equals("empty")) {
            return recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
        } else {
            return recipeRepository.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
        }

    }


    public void authorizer(Long id, String currentUserName) {
        if(!currentUserName.equals(recipeRepository.findById(id).get().getUser().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

}

