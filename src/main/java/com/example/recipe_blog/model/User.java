package com.example.recipe_blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {


    @Id
    @NotNull
    @NotBlank
    @Pattern(regexp = ".+@.+\\..+", message = "Error in email")
    private String email;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 8, message = "Password Must be at least 8 character")
    private String password;

    @JsonIgnore
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipeList;

    private  LocalDateTime date;
    @PrePersist
    public void createdOn() {
        date = LocalDateTime.now();
    }
}

