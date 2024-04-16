DROP DATABASE IF EXISTS `csf_mp2`;
CREATE DATABASE `csf_mp2`;
USE `csf_mp2`;

CREATE TABLE `users`
(
    user_id       CHAR(26)      NOT NULL,
    user_name     VARCHAR(25)   NOT NULL,
    user_email    VARCHAR(254)  NOT NULL,
    user_dob      DATE          NOT NULL,
    user_username VARCHAR(20)   NOT NULL,
    user_password CHAR(60)      NOT NULL,

    is_confirmed  BOOLEAN       DEFAULT FALSE,
    created_date  TIMESTAMP     DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT pk_user_id       PRIMARY KEY (user_id),
    CONSTRAINT uq_user_email    UNIQUE (user_email),
    CONSTRAINT uq_user_username UNIQUE (user_username),

    INDEX idx_user_id       (user_id),
    INDEX idx_user_email    (user_email),
    INDEX idx_user_username (user_username)
);

-- 1 to 1 relationship between `users` and `user_login`
-- upsert (update) upon successful login
CREATE TABLE `user_login` 
(
    user_id         CHAR(26)    NOT NULL,
    last_login_date TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT pk_user_id       PRIMARY KEY (user_id),
    CONSTRAINT fk_user_id       FOREIGN KEY (user_id) REFERENCES `users`(user_id) ON DELETE CASCADE
);

-- many to many relationship between `users` and `event_bookmarks`
CREATE TABLE `event_bookmarks`
(
    id             INT          AUTO_INCREMENT PRIMARY KEY,
    event_id       VARCHAR(20)  NOT NULL,
    user_id        CHAR(26)     NOT NULL,

    CONSTRAINT fk_event_bookmarks_user_id FOREIGN KEY (user_id) REFERENCES `users`(user_id) ON DELETE CASCADE,

    INDEX idx_event_id      (event_id),
    INDEX idx_user_id       (user_id)
);

-- grant all privileges on csf_mp2.* to fred@'%';
-- flush privileges;