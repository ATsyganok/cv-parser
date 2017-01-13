use cvdb;
CREATE  TABLE usersec (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO usersec(username,password,enabled)
VALUES ('mkyong','$2a$10$X1fPSIfrkiZ.vs68jDfYhOcXe0H7jTk4h.3aTgqGAKaGJVzGEK7zq', true);
INSERT INTO usersec(username,password,enabled)
VALUES ('mkyong1','123456', true);
INSERT INTO usersec(username,password,enabled)
VALUES ('alex','$2a$10$X1fPSIfrkiZ.vs68jDfYhOcXe0H7jTk4h.3aTgqGAKaGJVzGEK7zq', true);


INSERT INTO user_roles (username, role)
VALUES ('mkyong', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('mkyong', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('alex', 'ROLE_USER');

SHOW TABLES ;
SELECT * FROM user_security;
SELECT * FROM users;
SELECT * FROM uploaded_file;
SELECT *
FROM user_roles;