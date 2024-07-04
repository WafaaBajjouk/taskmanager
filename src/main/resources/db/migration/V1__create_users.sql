CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(100) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL
);
CREATE TABLE Projects (
                          project_id SERIAL PRIMARY KEY,
                          project_name VARCHAR(100) NOT NULL,
                          description TEXT,
                          start_date DATE,
                          end_date DATE,
                          project_complete BOOLEAN DEFAULT FALSE,
                          project_progress_percent INTEGER DEFAULT 0,
                          CONSTRAINT chk_end_date_after_start CHECK (end_date >= start_date)

);
CREATE TABLE Tasks (
                       task_id SERIAL PRIMARY KEY,
                       task_name VARCHAR(100) NOT NULL,
                       description TEXT,
                       due_date DATE,
                       status VARCHAR(50) DEFAULT 'to-do',
                       project_id INT NOT NULL,
                       FOREIGN KEY (project_id) REFERENCES Projects(project_id),
                       CONSTRAINT chk_due_date CHECK (due_date >= CURRENT_DATE),
                       CONSTRAINT chk_status CHECK (status IN ('to-do', 'completed', 'IN PROGRESS'))
);
CREATE TABLE Task_Assignments (
                                  assignment_id SERIAL PRIMARY KEY,
                                  task_id INT NOT NULL,
                                  user_id INT NOT NULL,
                                  assigned_date DATE,
                                  FOREIGN KEY (task_id) REFERENCES Tasks(task_id),
                                  FOREIGN KEY (user_id) REFERENCES Users(user_id),
                                  CONSTRAINT unique_assignment UNIQUE (task_id, user_id)
);
