package com.ecom.ecom_application.services;

import com.ecom.ecom_application.dto.AddressDTO;
import com.ecom.ecom_application.dto.UserRequestDTO;
import com.ecom.ecom_application.dto.UserResponseDTO;
import com.ecom.ecom_application.models.Address;
import com.ecom.ecom_application.models.User;
import com.ecom.ecom_application.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();
    private Long nextIndex = 1L;

    public List<UserResponseDTO> fetchUsers(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse).collect(Collectors.toList());
    }

    public void addUser(UserRequestDTO userRequestDTO){
//        user.setId(nextIndex++);
//        userList.add(user);
        User user = new User();
        updateUserFromRequest(user, userRequestDTO);
        userRepository.save(user);
    }

    public Optional<UserResponseDTO> fetchUser(Long id){
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, UserRequestDTO userRequestDTO){
        return userRepository.findById(id)
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, userRequestDTO);
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponseDTO mapToUserResponse(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(String.valueOf(user.getId()));
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setPhone(user.getPhone());
        userResponseDTO.setUserRole(user.getUserRole());

        if (user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setId(String.valueOf(user.getAddress().getId()));
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());

            userResponseDTO.setAddressDto(addressDTO);
        }
        return userResponseDTO;
    }

    private void updateUserFromRequest(User user, UserRequestDTO userRequestDTO) {
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        user.setUserRole(userRequestDTO.getUserRole());

        if (userRequestDTO.getAddressDto() != null){
            Address address  = new Address();
            address.setStreet(userRequestDTO.getAddressDto().getStreet());
            address.setCity(userRequestDTO.getAddressDto().getCity());
            address.setState(userRequestDTO.getAddressDto().getState());
            address.setCountry(userRequestDTO.getAddressDto().getCountry());

            user.setAddress(address);
        }
    }
}
