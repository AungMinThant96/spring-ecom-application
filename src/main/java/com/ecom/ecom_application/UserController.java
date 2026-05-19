package com.ecom.ecom_application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public List<User> getUserList(){
        return userService.fetchUsers();
    }

    @PostMapping("/api/users")
    public String createUser(@RequestBody User user){
        userService.addUser(user);
        return "Success";
    }
}
