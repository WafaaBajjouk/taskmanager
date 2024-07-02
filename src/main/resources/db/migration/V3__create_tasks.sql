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
