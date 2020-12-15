package com.example.tedlogin.controller;

import com.example.tedlogin.model.User;
import com.example.tedlogin.repository.UserRepository;
import com.example.tedlogin.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/game")
public class GameController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("{id}")
    public String getGame(@PathVariable int id) {
        return "This is game number" + id;
    }

    @PostMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public String addGame(@PathVariable int id) {
        Integer userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow();
        user.addGame(id);
        userRepository.save(user);
        return "Success";
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Integer> getGames() {
        Integer userId = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        User user = userRepository.findById(userId).orElseThrow();
        return user.getGames();
    }

}
