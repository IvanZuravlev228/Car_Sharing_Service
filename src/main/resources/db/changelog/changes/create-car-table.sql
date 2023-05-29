--liquibase formatted sql
--changeset OlehSarapuk:create-car-table
CREATE TABLE IF NOT EXISTS cars
(
    id bigint NOT NULL,
    model character varying(256) NOT NULL,
    brand character varying(256) NOT NULL,
    type character varying(256) NOT NULL,
    inventory int CHECK (inventory >= 0),
    daily_fee decimal NOT NULL,

    CONSTRAINT car_pk PRIMARY KEY(id)
    );

--rollback DROP TABLE cars;
