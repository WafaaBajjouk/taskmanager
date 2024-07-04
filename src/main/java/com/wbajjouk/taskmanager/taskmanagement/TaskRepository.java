package com.wbajjouk.taskmanager.taskmanagement;

import com.wbajjouk.taskmanager.projectmanagement.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {


           List<Task> findByPriority(String priority);
           List<Task> findByProject(Project project);
           List<Task> findByStatus(String status);
           List<Task> findByProjectIdAndStatus(Long projectId, String status);

    int countByProjectId(long projectId);

    int countByProjectIdAndStatus(long projectId, String completed);

}
