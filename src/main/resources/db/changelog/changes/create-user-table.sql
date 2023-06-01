--liquibase formatted sql
--changeset IvanZhuravlev:create-user-table
CREATE TABLE IF NOT EXISTS users
(
    id bigint NOT NULL AUTO_INCREMENT,
    email character varying(256) NOT NULL,
    first_name character varying(256) NOT NULL,
    last_name character varying(256) NOT NULL,
    password character varying(256) NOT NULL,
    role character varying(256) NOT NULL,

    CONSTRAINT user_pk PRIMARY KEY(id),
    CONSTRAINT user_email_unique UNIQUE (email)
    );

--rollback DROP TABLE users;
