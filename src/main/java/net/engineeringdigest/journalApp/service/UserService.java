package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.User;

import net.engineeringdigest.journalApp.repository.UserRepositry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepositry userRepositry;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User SaveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("user"));
           return userRepositry.save(user);
    }
    public User SaveNewAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        return userRepositry.save(user);
    }
    public void SaveUser(User user){
         userRepositry.save(user);
    }

    public List<User> getAll(){
        return userRepositry.findAll();
    }

    public Optional<User> getById(ObjectId id) {
        return userRepositry.findById(id);
    }

    public void DeleteById(ObjectId id){
        userRepositry.deleteById(id);
    }

    public User FindUserByName(String name){
       return userRepositry.findByUsername(name);
    }

    public void deleteByUserName(String username) {
        userRepositry.deleteByUsername(username);
    }
}
