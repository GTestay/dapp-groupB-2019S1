package com.edu.unq.tpi.dapp.grupoB.Eventeando.webService;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.User;
import com.edu.unq.tpi.dapp.grupoB.Eventeando.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

        User user = userService.createUser(newUser);

        return user;
    }

    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findUser(@PathVariable("id") Long id) {

        User user = userService.searchUser(id);
        return user;
    }

}
