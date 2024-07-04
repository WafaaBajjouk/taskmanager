package com.wbajjouk.taskmanager.usermanagement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
//    between your entity classes (User) and your Data Transfer Object (DTO) classes (UserResponse).
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);
    User userRequestToUser(UserRequest userrequest);
    void userRequestToUser(UserRequest userrequest, @MappingTarget User user);

}
