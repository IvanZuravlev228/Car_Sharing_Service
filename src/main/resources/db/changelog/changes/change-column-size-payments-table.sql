--liquibase formatted sql
--changeset MykytaNikonets:change-column-size-payments-table
ALTER TABLE payments
    CHANGE COLUMN `session_url` `session_url` VARCHAR(1000) NOT NULL ;

--rollback DROP TABLE payments;