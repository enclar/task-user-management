INSERT INTO users (first_name, last_name, email_address, password, user_role) VALUES
('Michael', 'Smith', 'michael.smith@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('Emily', 'Johnson', 'emily.johnson@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'ADMIN'),
('Jessica', 'Williams', 'jessica.williams@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('Matthew', 'Jones', 'matthew.jones@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('Emma', 'Brown', 'emma.brown@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('Daniel', 'Garcia', 'daniel.garcia@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'ADMIN'),
('Olivia', 'Martinez', 'olivia.martinez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('William', 'Rodriguez', 'william.rodriguez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('Sophia', 'Wilson', 'sophia.wilson@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'USER'),
('James', 'Lopez', 'james.lopez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'ADMIN');

INSERT INTO tasks (created_date_time, task_state, created_by, assigned_to, short_description, description) VALUES
(NOW(), 'NEW', 1, 2, 'Implement user authentication feature', 'Implement user authentication feature'),
(NOW(), 'IN_PROGRESS', 2, 3, 'Design dashboard UI', 'Design dashboard UI'),
(NOW(), 'CLOSED', 3, 4, 'Bug fixes in data processing module', 'Bug fixes in data processing module'),
(NOW(), 'CANCELED', 4, 5, 'Refactor legacy codebase', 'Refactor legacy codebase'),
(NOW(), 'NEW', 5, 6, 'Deploy application update', 'Deploy application update'),
(NOW(), 'IN_PROGRESS', 6, 7, 'Write API documentation', 'Write API documentation'),
(NOW(), 'CLOSED', 7, 8, 'Design new homepage layout', 'Design new homepage layout'),
(NOW(), 'NEW', 8, 9, 'Optimize database queries', 'Optimize database queries'),
(NOW(), 'IN_PROGRESS', 9, 10, 'Fix performance issues in backend', 'Fix performance issues in backend'),
(NOW(), 'CANCELED', 10, 1, 'Update security protocols', 'Update security protocols');