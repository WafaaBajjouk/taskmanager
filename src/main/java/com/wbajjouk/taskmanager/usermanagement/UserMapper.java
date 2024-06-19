package com.wbajjouk.taskmanager.usermanagement;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

//    between your entity classes (User) and your Data Transfer Object (DTO) classes (UserResponse).
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);
    User userResponseToUser(UserResponse userResponse);

    List<UserResponse> usersToUserResponses(List<User> users);
}
