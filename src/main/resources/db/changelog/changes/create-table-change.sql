--liquibase formatted sql


--changeset Vladislav Matuizo:1 dbms:mysql
--preconditions onFail:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables where table_name = 'beer';
CREATE TABLE beer
(
    id                 VARCHAR(36) NOT NULL,
    beer_name          VARCHAR(255),
    beer_style         VARCHAR(255),
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    min_on_hand        INT,
    price              DECIMAL(38, 2),
    quantity_to_brew   INT,
    upc                VARCHAR(255) UNIQUE,
    version            BIGINT,
    PRIMARY KEY (id)
);
--changeset Vladislav Matuizo:1 dbms:h2
--preconditions onFail:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables where table_name = 'beer';
CREATE TABLE beer
(
    id                 UUID NOT NULL,
    beer_name          VARCHAR(255),
    beer_style         VARCHAR(255),
    created_date       TIMESTAMP,
    last_modified_date TIMESTAMP,
    min_on_hand        INT,
    price              DECIMAL(38, 2),
    quantity_to_brew   INT,
    upc                VARCHAR(255) UNIQUE,
    version            BIGINT,
    PRIMARY KEY (id)
);

--rollback drop table beer;

