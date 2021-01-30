USE `poynac_website`;

CREATE TABLE user
(
    id       INTEGER      NOT NULL AUTO_INCREMENT,
    login    VARCHAR(255) NOT NULL UNIQUE,
    password CHAR(32)     NOT NULL,
    role     TINYINT      NOT NULL CHECK ( role IN (0, 1, 2)), /* 0 - user, 1 - moderator, 2 - admin */
    name     VARCHAR(255),
    surname  VARCHAR(255),
    mail     VARCHAR(255),
    phone    VARCHAR(255),
    address  VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    id      INTEGER NOT NULL AUTO_INCREMENT,
    user_id INTEGER NOT NULL,
    date    DATE    NOT NULL,
    price   DOUBLE  NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          INTEGER      NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL UNIQUE,
    price       DOUBLE       NOT NULL,
    existence   BOOLEAN      NOT NULL,
    description TEXT,
    visibility  BOOLEAN,
    imageLink   TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE attribute
(
    id    INTEGER NOT NULL AUTO_INCREMENT,
    name  VARCHAR(255),
    value TEXT,
    PRIMARY KEY (id)
);

CREATE TABLE order_product
(
    order_id   INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    amount     INTEGER NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES `order` (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES product (id) ON DELETE CASCADE
);

CREATE TABLE product_attribute
(
    attribute_id INTEGER NOT NULL,
    product_id   INTEGER NOT NULL,
    FOREIGN KEY (attribute_id)
        REFERENCES attribute (id) ON DELETE CASCADE,
    FOREIGN KEY (product_id)
        REFERENCES product (id) ON DELETE CASCADE
);