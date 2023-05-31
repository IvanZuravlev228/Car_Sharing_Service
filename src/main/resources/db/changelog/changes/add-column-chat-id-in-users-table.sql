--liquibase formatted sql
--changeset NikitaSalohub:create-cars-table
ALTER TABLE users
    ADD COLUMN `chat_id` bigint NULL AFTER `role`;

--rollback DROP TABLE user_chat;