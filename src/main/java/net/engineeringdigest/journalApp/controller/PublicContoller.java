package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/public")
class PublicContoller{

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    String healthcheck(){
        return "okkh";
    }
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.SaveNewUser(user);
    }
}
