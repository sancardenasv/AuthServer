CREATE DATABASE AUTH_SERVICE;
USE AUTH_SERVICE;

CREATE TABLE permission (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20));

CREATE TABLE client (
id INT PRIMARY KEY AUTO_INCREMENT,
clientId VARCHAR(20),
authorities VARCHAR(255),
authorizedGrantTypes VARCHAR(255),
autoApproveScopes VARCHAR(255),
redirectUri VARCHAR(255),
scopes VARCHAR(255),
secret VARCHAR(1000));

CREATE TABLE user (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20),
email VARCHAR(255) UNIQUE KEY,
password VARCHAR(1000),
clientId INT, FOREIGN KEY(clientId) REFERENCES client (id),
status BOOLEAN);

CREATE TABLE role (
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(20));

CREATE TABLE permissionToRole (
id INT PRIMARY KEY AUTO_INCREMENT,
permissionId INT, FOREIGN KEY(permissionId) REFERENCES permission (id),
roleId INT, FOREIGN KEY(roleId) REFERENCES role(id));

CREATE TABLE userToRole (
id INT PRIMARY KEY AUTO_INCREMENT,
userId INT, FOREIGN KEY(userId) REFERENCES user(id),
roleId INT, FOREIGN KEY(roleId) REFERENCES role(id));


INSERT INTO permission (id, name) VALUES (1, 'CREATE_NOTE'), (2, 'EDIT_NOTE'), (3, 'DELETE_NOTE'), (4, 'VIEW_ALL_NOTE'), (5, 'VIEW_NOTE');

INSERT INTO role (id, name) VALUES (1, 'ADMINISTRATOR'), (2, 'AUDITOR');

INSERT INTO client (id, clientId, authorities, authorizedGrantTypes, autoApproveScopes, redirectUri, scopes, secret)
VALUES (1, "client", "", "password,refresh_token", "", "http://localhost:8081/login", "read,write", "$2a$10$4k/PgfTdny5pIVvri.HAqO8Qo0pCyIOwqSij34ys/hjIWxhEy02X6");

INSERT INTO user (id, name, email, password, clientId, status) VALUES (1, 'John', 'john@gmail.com','$2a$10$jbIi/RIYNm5xAW9M7IaE5.WPw6BZgD8wcpkZUg0jm8RHPtdfDcMgm', 1, 1);
INSERT INTO user (id, name, email, password, clientId, status) VALUES (2, 'Mike', 'mike@gmail.com','$2a$10$jbIi/RIYNm5xAW9M7IaE5.WPw6BZgD8wcpkZUg0jm8RHPtdfDcMgm', 1, 1);

INSERT INTO permissionToRole (permissionId, roleId) VALUES (1, 1);
INSERT INTO permissionToRole (permissionId, roleId) VALUES (2, 1);
INSERT INTO permissionToRole (permissionId, roleId) VALUES (3, 1);
INSERT INTO permissionToRole (permissionId, roleId) VALUES (4, 1);
INSERT INTO permissionToRole (permissionId, roleId) VALUES (5, 1);

INSERT INTO permissionToRole (permissionId, roleId) VALUES (4, 2);
INSERT INTO permissionToRole (permissionId, roleId) VALUES (5, 2);

INSERT INTO userToRole (userId, roleId) VALUES (1, 1);
INSERT INTO userToRole (userId, roleId) VALUES (2, 2);

SELECT * FROM user WHERE email ='john@gmail.com';

SELECT DISTINCT P.name FROM permission P
INNER JOIN permissionToRole P_R ON P.id = P_R.permissionId
INNER JOIN role R ON R.id = P_R.roleId
INNER JOIN userToRole U_R ON U_R.roleId = R.id
INNER JOIN user U ON U.id = U_R.userId
WHERE U.email = 'john@gmail.com';