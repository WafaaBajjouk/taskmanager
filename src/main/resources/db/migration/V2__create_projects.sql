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