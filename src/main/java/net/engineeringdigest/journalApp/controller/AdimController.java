package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntries;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/adim")
public class AdimController{
    @Autowired
    private UserService userService;

    @GetMapping("/all-user")
    public ResponseEntity<?>getAllusers(){
        List<User> all =userService.getAll();
        if(!all.isEmpty()){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin-user")
    public ResponseEntity<?> createNewAdmin(@RequestBody User user){
        User admin=  userService.SaveNewAdmin(user);
        if(admin!=null){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
