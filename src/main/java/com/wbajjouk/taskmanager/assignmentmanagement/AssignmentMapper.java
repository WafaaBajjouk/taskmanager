package com.wbajjouk.taskmanager.assignmentmanagement;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssignmentMapper {

    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    AssignmentResponse assignmentToAssignmentResponse(TaskAssignment assignment);
    TaskAssignment assignmentRequestToAssignment(AssignmentRequest assignmentRequest);

    void  assignmentRequestToAssignment(AssignmentRequest assignmentRequest, @MappingTarget TaskAssignment taskAssignment);


    //@Mapping(source=".", target = "residence")
   // AssignmentController.PersonResponse personToPersonResponse(AssignmentController.Person person);
//    void updateAssignmentFromRequest(AssignmentRequest request,  TaskAssignment task);

}

