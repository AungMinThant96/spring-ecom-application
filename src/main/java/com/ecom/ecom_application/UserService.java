package com.ecom.ecom_application;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();
    private Long nextIndex = 1L;

    public List<User> fetchUsers(){
        return userList;
    }

    public List<User> addUser(User user){
        user.setId(nextIndex++);
        userList.add(user);
        return userList;
    }

    public Optional<User> fetchUser(Long id){
        return userList.stream().
                filter(user -> user.getId().equals(id)).findFirst();
    }
}
