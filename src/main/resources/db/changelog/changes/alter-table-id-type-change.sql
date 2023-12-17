--liquibase formatted sql

--changeset Vladislav Matuizo:4 dbms:mysql
--preconditions onFail:HALT
--precondition-sql-check expectedResult:1 SELECT COUNT(*) FROM information_schema.tables where table_name = 'beer';
DELETE FROM beer;
ALTER TABLE beer MODIFY COLUMN id BINARY(16) NOT NULL;

--rollback ALTER TABLE beer MODIFY COLUMN id VARCHAR(32) NOT NULL;

--changeset Vladislav Matuizo:5 dbms:mysql
--preconditions onFail:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM beer;
INSERT INTO beer (id, beer_name, beer_style, created_date, last_modified_date, min_on_hand, quantity_to_brew, price, upc, version) VALUES
(unhex(replace('0a818933-087d-47f2-ad83-2f986ed087eb','-','')), 'Mango Boss', 'IPA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12, 200, 12.95, '0631234200036', 1),
(unhex(replace('a712d914-61ea-4623-8bd0-32c0f6545bfd','-','')), 'Galaxy Cat', 'PALE_ALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12, 200, 12.95, '0631234300019', 1),
(unhex(replace('026cc3c8-3a0c-4083-a05b-e908048c1b08','-','')), 'Pinball Porter', 'PORTER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12, 200, 12.95, '0083783375213', 1);

--rollback DELETE FROM beer WHERE id in (unhex(replace('0a818933-087d-47f2-ad83-2f986ed087eb','-','')), unhex(replace('a712d914-61ea-4623-8bd0-32c0f6545bfd','-','')), unhex(replace('026cc3c8-3a0c-4083-a05b-e908048c1b08','-','')));

