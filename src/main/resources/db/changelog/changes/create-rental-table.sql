--liquibase formatted sql
--changeset KyryloHyliuk:create-car-table

CREATE TABLE IF NOT EXISTS rentals (
    id BIGINT NOT NULL AUTO_INCREMENT,
    car_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    rental_start_date DATE NOT NULL,
    rental_return_date DATE NOT NULL,
    actual_return_date DATE,

    PRIMARY KEY (id),
    FOREIGN KEY (car_id) REFERENCES cars (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

--rollback DROP TABLE rentals;