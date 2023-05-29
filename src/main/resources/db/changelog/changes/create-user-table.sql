--liquibase formatted sql
--changeset IvanZhuravlev:create-car-table
CREATE TABLE IF NOT EXISTS users
(
    id bigint NOT NULL,
    email character varying(256) NOT NULL,
    first_name character varying(256) NOT NULL,
    last_name character varying(256) NOT NULL,
    password character varying(256) NOT NULL,
    role character varying(256) NOT NULL,

    CONSTRAINT user_pk PRIMARY KEY(id)
    );

--rollback DROP TABLE cars;