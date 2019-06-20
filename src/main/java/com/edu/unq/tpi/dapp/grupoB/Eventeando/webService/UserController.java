package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.webService.dtos.UserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@PathVariable("id") Long id) {
        return userService.searchUser(id);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public User loginUser(@RequestBody UserLogin userLogin) {
        return userService.findUserByEmail(userLogin.getEmail());
    }

    @GetMapping("/users/emails")
    @ResponseStatus(HttpStatus.OK)
    public List<String> allEmailsContaining(@RequestParam(required = false, defaultValue = "", name = "email") String email) {
        return userService.allEmailsContaining(email);
    }

}
