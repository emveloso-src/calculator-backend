CREATE TABLE calculator_user ( id INT AUTO_INCREMENT PRIMARY KEY,
username VARCHAR(250) NOT NULL, password VARCHAR(250) NOT NULL , balance float not null
);

CREATE TABLE operation ( id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(250) NOT NULL , cost double not null
);