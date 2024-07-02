CREATE TABLE Task_Assignments (
                                  assignment_id SERIAL PRIMARY KEY,
                                  task_id INT NOT NULL,
                                  user_id INT NOT NULL,
                                  assigned_date DATE,
                                  FOREIGN KEY (task_id) REFERENCES Tasks(task_id),
                                  FOREIGN KEY (user_id) REFERENCES Users(user_id),
                                  CONSTRAINT unique_assignment UNIQUE (task_id, user_id)
);
