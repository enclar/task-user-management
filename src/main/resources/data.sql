

INSERT INTO users (first_name, last_name, email_address, password, user_role) VALUES
('Michael', 'Smith', 'michael.smith@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('Emily', 'Johnson', 'emily.johnson@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'Admin'),
('Jessica', 'Williams', 'jessica.williams@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('Matthew', 'Jones', 'matthew.jones@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('Emma', 'Brown', 'emma.brown@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('Daniel', 'Garcia', 'daniel.garcia@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'Admin'),
('Olivia', 'Martinez', 'olivia.martinez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('William', 'Rodriguez', 'william.rodriguez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('Sophia', 'Wilson', 'sophia.wilson@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
('James', 'Lopez', 'james.lopez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'Admin');

INSERT INTO tasks (created_date_time, task_state, created_by, assigned_to, description) VALUES
(NOW(), 'New', 1, 2, 'Implement user authentication feature'),
(NOW(), 'InProgress', 2, 3, 'Design dashboard UI'),
(NOW(), 'Closed', 3, 4, 'Bug fixes in data processing module'),
(NOW(), 'Canceled', 4, 5, 'Refactor legacy codebase'),
(NOW(), 'New', 5, 6, 'Deploy application update'),
(NOW(), 'InProgress', 6, 7, 'Write API documentation'),
(NOW(), 'Closed', 7, 8, 'Design new homepage layout'),
(NOW(), 'New', 8, 9, 'Optimize database queries'),
(NOW(), 'InProgress', 9, 10, 'Fix performance issues in backend'),
(NOW(), 'Canceled', 10, 1, 'Update security protocols');