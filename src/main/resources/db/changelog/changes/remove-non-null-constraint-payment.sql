--liquibase formatted sql
--changeset MykytaNikonets:remove-non-null-constraint-payment
ALTER TABLE payments
    CHANGE COLUMN `session_url` `session_url` VARCHAR(1000) NULL ,
    CHANGE COLUMN `session_id` `session_id` VARCHAR(256) NULL ,
    CHANGE COLUMN `amount_to_pay` `amount_to_pay` DECIMAL(10,0) NULL ,
    CHANGE COLUMN `status` `status` VARCHAR(256) NULL ;

--rollback DROP TABLE payments;