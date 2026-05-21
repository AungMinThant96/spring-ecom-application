package com.ecom.ecom_application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> getUserList(){
        return userService.fetchUsers();
    }

    @GetMapping("/api/users/{id}")
    public User getUserList(@PathVariable Long id){
        User user = userService.fetchUser(id);
        return user;
    }

    @PostMapping("/api/users")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "Success";
    }
}
