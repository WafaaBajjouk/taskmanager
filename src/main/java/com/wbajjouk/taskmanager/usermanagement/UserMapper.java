package com.wbajjouk.taskmanager.usermanagement;

import com.wbajjouk.taskmanager.assignmentmanagement.AssignmentRequest;
import com.wbajjouk.taskmanager.assignmentmanagement.TaskAssignment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

//    between your entity classes (User) and your Data Transfer Object (DTO) classes (UserResponse).
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse userToUserResponse(User user);
    User userRequestToUser(UserRequest userrequest);
    void userRequestToUser(UserRequest userrequest, @MappingTarget User user);

}
