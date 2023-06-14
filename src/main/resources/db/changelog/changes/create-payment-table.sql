--liquibase formatted sql
--changeset MykytaNikonets:create-payment-table
CREATE TABLE IF NOT EXISTS payments
(
    id bigint NOT NULL AUTO_INCREMENT,
    rental_id bigint NOT NULL,
    session_url character varying(256) NOT NULL,
    session_id character varying(256) NOT NULL,
    amount_to_pay decimal(10,2) NOT NULL,
    status character varying(256) NOT NULL,
    type character varying(256) NOT NULL,

    CONSTRAINT car_pk PRIMARY KEY(id),
    FOREIGN KEY (rental_id) REFERENCES rentals (id)
    );

--rollback DROP TABLE payments;
