package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
   private UserService userService;

   @Autowired
   private WeatherService weatherService;

   @PutMapping("/")
   public ResponseEntity<?> upadateUser(@RequestBody User user){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String username = authentication.getName();
       User userInDb = userService.FindUserByName(username);

           userInDb.setUsername(user.getUsername());
           userInDb.setPassword(user.getPassword());
           userService.SaveUser(userInDb);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteByUserName(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse =  weatherService.getWeather("Mumbai");
        String greeting = "";
        if(weatherResponse!=null){
             greeting = ", Weather feel Like"+weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi"+authentication.getName()+ greeting ,HttpStatus.OK);
    }
}
