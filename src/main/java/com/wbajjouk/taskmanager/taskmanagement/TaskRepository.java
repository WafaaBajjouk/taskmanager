package com.wbajjouk.taskmanager.taskmanagement;

import com.wbajjouk.taskmanager.projectmanagement.Project;
import com.wbajjouk.taskmanager.usermanagement.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {


           List<Task> findByPriority(String priority);
           List<Task> findByProject(Project project);
           List<Task> findByStatus(String status);

}
