-- INSERT INTO users (id, first_name, last_name, email_address, password, user_role) VALUES
-- ('e4b69db2-b4d8-4f7a-bc7b-2c474d8427a7', 'Michael', 'Smith', 'michael.smith@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('adb9f254-2a23-4b49-88f6-03f6bbec6375', 'Emily', 'Johnson', 'emily.johnson@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'Admin'),
-- ('a689b19f-6c6e-44ef-95e1-c631a4d5375e', 'Jessica', 'Williams', 'jessica.williams@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('1db19361-b439-470d-bb11-b28d4a449f32', 'Matthew', 'Jones', 'matthew.jones@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('bf5cc36d-cf24-469b-9955-e05538e28af1', 'Emma', 'Brown', 'emma.brown@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('b6d4351c-7392-4e8c-a16a-50c4b178f0a8', 'Daniel', 'Garcia', 'daniel.garcia@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'Admin'),
-- ('402f6638-5f5e-44d4-baa1-d248da6c8967', 'Olivia', 'Martinez', 'olivia.martinez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('aa649fc1-2a40-4d17-8311-8aab1e631383', 'William', 'Rodriguez', 'william.rodriguez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('5bde3ad5-14ea-445b-abe3-28039a482b78', 'Sophia', 'Wilson', 'sophia.wilson@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'User'),
-- ('7bc9ff7d-2ac1-4c33-8db6-54ff8b8c2dbe', 'James', 'Lopez', 'james.lopez@example.com', '$2y$10$nxwMYLBpR8qg9m46HEG4au7C8KrteE7VlQnBB7H5zLVqOR7P32c46', 'Admin');

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