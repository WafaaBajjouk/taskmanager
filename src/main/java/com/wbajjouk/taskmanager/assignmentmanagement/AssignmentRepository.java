package com.wbajjouk.taskmanager.assignmentmanagement;
import com.wbajjouk.taskmanager.taskmanagement.Task;
import com.wbajjouk.taskmanager.usermanagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<TaskAssignment, Long> {
    List<TaskAssignment> findByTask(Task task);

    List<TaskAssignment> findByUser(User delendo);

    void deleteByUser(User delendo);
}
