package com.ecom.ecom_application.controllers;

import com.ecom.ecom_application.dto.UserRequestDTO;
import com.ecom.ecom_application.dto.UserResponseDTO;
import com.ecom.ecom_application.models.User;
import com.ecom.ecom_application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
//    @RequestMapping(value="/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDTO>> getUserList(){
        return ResponseEntity.ok(userService.fetchUsers());
//        return new ResponseEntity<>(userService.fetchUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserList(@PathVariable Long id){
        return userService.fetchUser(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserRequestDTO userDto){
        userService.addUser(userDto);
        return ResponseEntity.ok("Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO updatedUser, @PathVariable Long id){
        boolean updated = userService.updateUser(id, updatedUser);
        if(updated){
            return ResponseEntity.ok("Update success");
        }
        return ResponseEntity.notFound().build();
    }
}
