-- BASIC user
INSERT INTO users (id, user_name, password, email)
VALUES (
  1,
  'basic_user',
  '$2a$10$4Jb5dqlOQiXKvcs15efQiez/SdrSgajE1gPNeo5HBFe1hdUl4OYHy',
  'basic@corp.com'
);

INSERT INTO user_roles (user_id, role)
VALUES (1, 'ROLE_BASIC');

-- ADMIN user
INSERT INTO users (id, user_name, password, email)
VALUES (
  2,
  'admin_user',
  '$2a$10$50WZEq6uhurT23/6GSRCvOnZvJluc5t8/XtLscBJgy9G3yFpjgbkO',
  'admin@corp.com'
);

INSERT INTO user_roles (user_id, role)
VALUES (2, 'ROLE_ADMIN');
