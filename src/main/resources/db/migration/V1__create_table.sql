DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE roles (
    role_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    name NVARCHAR(50) NOT NULL
);

CREATE TABLE users (
    user_id UNIQUEIDENTIFIER PRIMARY KEY DEFAULT NEWID(),
    username NVARCHAR(255) NOT NULL,
    password NVARCHAR(255) NOT NULL,
    active BIT NOT NULL,
    email VARCHAR(100) NOT NULL,
    failed_attempt INTEGER NOT NULL,
    date_locked DATETIME,
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