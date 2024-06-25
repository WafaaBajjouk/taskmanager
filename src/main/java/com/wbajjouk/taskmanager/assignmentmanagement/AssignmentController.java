package com.wbajjouk.taskmanager.assignmentmanagement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// LocalDate "2024-07-01"
// LocalDateTime "2024-07-01T10:00:00"
// Instant N microseconds since 1970
// OffsetDateTime 2024-07-01T00:00:00+02:00
// ZonedDateTime 2024-07-01T00:00:00[Europe/Rome]

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping
    public List<AssignmentResponse> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getAssignmentById(@PathVariable Long id) {
        Optional<AssignmentResponse> assignmentResponse = assignmentService.getAssignmentById(id);
        return assignmentResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/tasks/{taskId}")
    public ResponseEntity<List<AssignmentResponse>> getAssignmentByTaskId(@PathVariable Long taskId) {
        Optional<List<AssignmentResponse>> assignmentResponse = assignmentService.getAssignmentByTaskId(taskId);
        return assignmentResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody AssignmentRequest assignmentRequest) {
        AssignmentResponse savedAssignment = assignmentService.saveAssignment(assignmentRequest);
        return ResponseEntity.ok(savedAssignment);
    }

    // TODO: implement update
    @PutMapping("/{id}")
    public AssignmentResponse updateAssignment(@PathVariable long id, @RequestBody AssignmentRequest assignmentRequest) {
        return assignmentService.updateAssignment(id, assignmentRequest);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.noContent().build();
    }
//
//
//    public static class AddressResponse {
//        public String streetName;
//        public String streetNumber;
//        public String city;
//    }
//
//    public static class Person {
//        public String streetName;
//        public String streetNumber;
//        public String city;
//    }
//
//    public static class Company {
//        public String streetName;
//        public String streetNumber;
//        public String city;
//    }
//
//
//    public static class PersonResponse {
//        public AddressResponse residence;
//    }
//
//    public static class CompanyResponse {
//        public AddressResponse mainSite;
//    }





}
