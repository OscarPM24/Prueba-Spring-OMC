CREATE TABLE IF NOT EXISTS user
(
    id       int PRIMARY KEY AUTO_INCREMENT,
    name     varchar(100) NOT NULL,
    username varchar(100) NOT NULL,
    password varchar(100) NOT NULL,
    street   VARCHAR(100) NOT NULL,
    city     VARCHAR(100) NOT NULL,
    zipcode  VARCHAR(100) NOT NULL,
    country  VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS todo
(
    id        int PRIMARY KEY AUTO_INCREMENT,
    title     varchar(100) NOT NULL,
    completed bool         NOT NULL,
    user int,
    CONSTRAINT fk_user FOREIGN KEY (user) REFERENCES user(id) ON DELETE SET NULL
);

