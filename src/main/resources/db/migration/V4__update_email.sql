UPDATE users
SET email = 'admin@gmail.com'
WHERE username = 'admin';

UPDATE users
SET email = 'user@gmail.com'
WHERE username = 'user';

UPDATE users
SET email = 'guest@gmail.com'
WHERE username = 'guest';