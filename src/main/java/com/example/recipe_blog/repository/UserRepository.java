package com.example.recipe_blog.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.recipe_blog.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

}

