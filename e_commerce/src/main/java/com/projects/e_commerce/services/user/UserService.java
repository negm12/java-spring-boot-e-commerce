package com.projects.e_commerce.services.user;


import com.projects.e_commerce.dto.UserDto;
import com.projects.e_commerce.entity.User;
import com.projects.e_commerce.errors.AlreadyExistRecord;
import com.projects.e_commerce.errors.ResourceNotFound;
import com.projects.e_commerce.reposetory.UserRepository;
import com.projects.e_commerce.request.CreateUserRequest;
import com.projects.e_commerce.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
//    private final ModelMapper modelMapper;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFound("User not found!"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistRecord(request.getEmail() +" already exists!"));
    }

    @Override
    public User updateUser(UserUpdateRequest request, Long userId) {
        return  userRepository.findById(userId).map(existingUser ->{
            existingUser.setFirstName(request.getFirstName());
            existingUser.setLastName(request.getLastName());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new ResourceNotFound("User not found!"));

    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository :: delete, () ->{
            throw new ResourceNotFound("User not found!");
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        UserDto converted_user_dto = new UserDto();

        converted_user_dto.setId(user.getId());
        converted_user_dto.setEmail(user.getEmail());
        converted_user_dto.setFirstName(user.getFirstName());
        converted_user_dto.setLastName(user.getLastName());
        converted_user_dto.setOrders(user.getOrders());
        return converted_user_dto;
    }
    
}
