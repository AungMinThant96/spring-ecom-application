package com.ecom.ecom_application;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

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

    public User fetchUser(Long id){
        for (User user: userList){
            if (user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
}
