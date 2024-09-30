package com.projects.e_commerce.services.user;


import com.projects.e_commerce.dto.UserDto;
import com.projects.e_commerce.entity.User;
import com.projects.e_commerce.request.CreateUserRequest;
import com.projects.e_commerce.request.UserUpdateRequest;

public interface IUserService {

    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UserUpdateRequest request, Long userId);
    void deleteUser(Long userId);

    UserDto convertUserToDto(User user);
}
