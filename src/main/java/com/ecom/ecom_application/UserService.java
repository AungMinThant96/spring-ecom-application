package com.ecom.ecom_application;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();

    public List<User> fetchUsers(){
        return userList;
    }

    public List<User> addUser(User user){
        if (userList.isEmpty()){
            user.setId(1L);
            userList.add(user);
        }
        else{
            Long lastId = userList.getLast().getId();
            Long id = 0L;
            if (lastId > 0){
                id = lastId + 1;
            }else{
                id = 1L;
            }
            user.setId(id);
            userList.add(user);
        }
        return userList;
    }
}
