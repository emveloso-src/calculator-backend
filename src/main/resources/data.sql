INSERT INTO calculator_user (username, password, balance
) VALUES ('elemudela@gmail.com', '$2a$10$myravgi3JT4DsH6.aICPKewiSxEUO1/5OD1V1ODB4D2GYstURT3fe', 100);

INSERT INTO calculator_user (username, password, balance
) VALUES ('test@gmail.com', '$2a$10$myravgi3JT4DsH6.aICPKewiSxEUO1/5OD1V1ODB4D2GYstURT3fe', 10);

INSERT INTO operation VALUES (1, 'addition', 1);
INSERT INTO operation VALUES (2, 'substraction', 2);
INSERT INTO operation VALUES (3,'multiplication', 3);
INSERT INTO operation VALUES (4,'division', 4);
INSERT INTO operation VALUES (5,'square root', 5);
INSERT INTO operation VALUES (6,'random string', 6);

INSERT INTO role ( name) VALUES ('USER');
INSERT INTO role (name) VALUES ('ADMIN');


INSERT INTO user_roles (user_id, role_id) values ( 1, 1);
INSERT INTO user_roles ( user_id, role_id) values ( 2, 1);
