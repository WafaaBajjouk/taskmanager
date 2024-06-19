package com.wbajjouk.taskmanager.taskmanagement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
//
//        public Optional<Task> findById(Long id);
//        public void deleteById(Long id);
//        public Task save(Task task);

}
