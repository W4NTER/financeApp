package ru.vadim.financeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.vadim.financeapp.entity.User;
import ru.vadim.financeapp.service.UserService;

@Controller
public class SomeSimpleController {
    @Autowired
    UserService userService;

    @GetMapping("/some")
    public String someMethod() {
        userService.addUser(new User());
        return "Hello World";
    }
}
