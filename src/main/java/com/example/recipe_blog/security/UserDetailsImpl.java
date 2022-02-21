package com.example.recipe_blog.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.recipe_blog.model.User;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> rollsAndAuthorities;

    public UserDetailsImpl(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.rollsAndAuthorities = List.of(new SimpleGrantedAuthority(user.getRole()));

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rollsAndAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

