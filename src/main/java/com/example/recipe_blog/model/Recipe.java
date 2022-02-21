package com.example.recipe_blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String category;

    private LocalDateTime date;

    @NotNull
    @NotBlank
    private String description;


    @NotNull
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> ingredients = new ArrayList<>();

    @NotNull
    @NotEmpty
    @Size(min = 1)
    @ElementCollection
    private List<String> directions ;

    @PrePersist
    public void createdOn() {
        date = LocalDateTime.now();
    }

    @PreUpdate
    public void updatedOn() {
        date = LocalDateTime.now();
    }

    @JsonIgnore
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;


}


