package com.example.recipe_blog.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.recipe_blog.model.Recipe;
import com.example.recipe_blog.model.User;
import com.example.recipe_blog.service.RecipeService;
import com.example.recipe_blog.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@RestController
@Validated
@RequestMapping("/api/")
public class RecipeController {


    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }


    @PostMapping("/recipe/new")
    public Map<String, Long> postRecipe(@RequestBody @Valid Recipe recipe, Principal principal) {
        User user = userService.getUser(principal.getName());
        recipeService.save(recipe, user);
        return Map.of("id", recipe.getId());
    }

    @GetMapping("/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        if (recipe.isPresent()) {
            return recipe.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipe/search")
    public List<Recipe> searchByNameOrCategory(@RequestParam(defaultValue = "empty") @NotBlank String name,
                                               @RequestParam(defaultValue = "empty") @NotBlank String category) {
        return recipeService.searchByNameOrCategory(name, category);
    }



    @PutMapping("/recipe/{id}")
    public void updateRecipe(@PathVariable long id, @RequestBody @Valid Recipe updatedRecipe, Principal principal) {
        Optional<Recipe> recipe = recipeService.findById(id);
        if(recipe.isPresent()) {
            recipeService.updateById(updatedRecipe, id,userService.getUser(principal.getName()));
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/recipe/{id}")
    public void deleteRecipeById(@PathVariable long id, Principal principal) {
        if (recipeService.findById(id).isPresent()) {
            recipeService.deleteById(id, principal.getName());
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}


