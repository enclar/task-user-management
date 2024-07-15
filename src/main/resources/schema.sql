CREATE TABLE IF NOT EXISTS users (
    id CHAR(36) PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    email_address VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    user_role ENUM('User', 'Admin') NOT NULL
)

CREATE TABLE IF NOT EXISTS tasks (
    id CHAR(36) PRIMARY KEY,
    created_date_time TIMESTAMP NOT NULL,
    task_state ENUM('New', 'InProgress', 'Closed', 'Canceled') NOT NULL,

    created_by CHAR(36) NOT NULL,
    FOREIGN KEY (created_by) REFERENCES users(id),
    
    assigned_to CHAR(36) NOT NULL,
    FOREIGN KEY (assigned_to) REFERENCES users(id),
    description VARCHAR(500) NOT NULL
)