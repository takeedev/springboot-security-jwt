INSERT INTO roles (role_id, name)
SELECT NEWID(), 'ROLE_ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

INSERT INTO roles (role_id, name)
SELECT NEWID(), 'ROLE_USER'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');

INSERT INTO roles (role_id, name)
SELECT NEWID(), 'ROLE_GUEST'
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'GUEST');

-- Insert users if they do not exist
INSERT INTO users (user_id, username, password, active, create_by, create_date)
SELECT NEWID(), 'admin', '$2a$10$sYAQ9pJkMA1fbipd/V3//.nZBmuHAJyq.LYF.Pk3C4/Oykz.p7RrS', 1, 'system', GETDATE()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (user_id, username, password, active, create_by, create_date)
SELECT NEWID(), 'user', '$2a$10$sYAQ9pJkMA1fbipd/V3//.nZBmuHAJyq.LYF.Pk3C4/Oykz.p7RrS', 1, 'system', GETDATE()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

INSERT INTO users (user_id, username, password, active, create_by, create_date)
SELECT NEWID(), 'guest', '$2a$10$sYAQ9pJkMA1fbipd/V3//.nZBmuHAJyq.LYF.Pk3C4/Oykz.p7RrS', 1, 'system', GETDATE()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'guest');

INSERT INTO user_role (user_id, role_id)
SELECT u.user_id, r.role_id
FROM users u
JOIN roles r ON r.name = 'ROLE_ADMIN'
WHERE u.username = 'admin'
  AND NOT EXISTS (
    SELECT 1
    FROM user_role
    WHERE user_id = u.user_id
      AND role_id = r.role_id
);

INSERT INTO user_role (user_id, role_id)
SELECT u.user_id, r.role_id
FROM users u
JOIN roles r ON r.name = 'ROLE_USER'
WHERE u.username = 'user'
  AND NOT EXISTS (
    SELECT 1
    FROM user_role
    WHERE user_id = u.user_id
      AND role_id = r.role_id
);

INSERT INTO user_role (user_id, role_id)
SELECT u.user_id, r.role_id
FROM users u
JOIN roles r ON r.name = 'ROLE_GUEST'
WHERE u.username = 'guest'
  AND NOT EXISTS (
    SELECT 1
    FROM user_role
    WHERE user_id = u.user_id
      AND role_id = r.role_id
);