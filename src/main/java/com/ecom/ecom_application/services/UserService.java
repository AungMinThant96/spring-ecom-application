package com.ecom.ecom_application.services;

import com.ecom.ecom_application.models.User;
import com.ecom.ecom_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();
    private Long nextIndex = 1L;

    public List<User> fetchUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user){
//        user.setId(nextIndex++);
//        userList.add(user);
        userRepository.save(user);
    }

    public Optional<User> fetchUser(Long id){
        return userRepository.findById(id);
    }

    public boolean updateUser(Long id, User updatedUser){
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
}
