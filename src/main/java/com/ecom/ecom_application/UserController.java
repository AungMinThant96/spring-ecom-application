package com.ecom.ecom_application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUserList(){
        return ResponseEntity.ok(userService.fetchUsers());
//        return new ResponseEntity<>(userService.fetchUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/users/{id}")
    public ResponseEntity<User> getUserList(@PathVariable Long id){
        return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping("/api/users")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/api/users/{id}")
    public ResponseEntity<String> updateUser(@RequestBody User updatedUser, @PathVariable Long id){
        boolean updated = userService.updateUser(id, updatedUser);
        if(updated){
            return ResponseEntity.ok("Update success");
        }
        return ResponseEntity.notFound().build();
    }
}
