CREATE TABLE roles (
    role_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(50) NOT NULL
);

CREATE TABLE users (
    user_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    username NVARCHAR(255) NOT NULL,
    email NVARCHAR(100) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    active BIT NOT NULL,
    create_by NVARCHAR(255) NOT NULL,
    create_date DATETIME NOT NULL
);

CREATE TABLE user_role (
    user_id UNIQUEIDENTIFIER NOT NULL,
    role_id UNIQUEIDENTIFIER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    PRIMARY KEY (user_id, role_id)
);

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
INSERT INTO users (user_id, username, password, active, create_by, create_date, email)
SELECT NEWID(), 'admin', 'password', 1, 'system', GETDATE(), 'admin@email.com'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (user_id, username, password, active, create_by, create_date, email)
SELECT NEWID(), 'user', 'password', 1, 'system', GETDATE(), 'user@email.com'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'user');

INSERT INTO users (user_id, username, password, active, create_by, create_date, email)
SELECT NEWID(), 'guest', 'password', 1, 'system', GETDATE(), 'guest@email.com'
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