package com.wbajjouk.taskmanager.taskmanagement;

import com.wbajjouk.taskmanager.projectmanagement.Project;
import com.wbajjouk.taskmanager.usermanagement.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {


           List<Task> findByPriority(String priority);
           List<Task> findByProject(Project project);
           List<Task> findByStatus(String status);
           List<Task> findByProjectIdAndStatus(Long projectId, String status);

    int countByProjectId(long projectId);

    int countByProjectIdAndStatus(long projectId, String completed);

//    @Query("select t.status, count(*) as cnt from Task t where t.project.id = ? group by t.status")
//    List<TaskStats> countByProjectId();
//    public static class TaskStats {
//        String status;
//        int cnt;
//    }
}
