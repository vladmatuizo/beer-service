--liquibase formatted sql


--changeset Vladislav Matuizo:2
--preconditions onFail:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM beer
insert into beer (id, beer_name, beer_style, created_date, last_modified_date, min_on_hand, quantity_to_brew, price, upc, version) values ('7b845a2d-129f-47e9-8a62-10c400cb8688', 'Mango Boss', 'IPA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12, 200, 12.95, '32834923749821', 1);
insert into beer (id, beer_name, beer_style, created_date, last_modified_date, min_on_hand, quantity_to_brew, price, upc, version) values ('8b42a329-0bc2-4efd-a76e-1f4b13ff1de7', 'Galaxy Cat', 'PALE_ALE', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12, 200, 12.95, '10239023901238', 1);
insert into beer (id, beer_name, beer_style, created_date, last_modified_date, min_on_hand, quantity_to_brew, price, upc, version) values ('2b7039e8-a2ef-4d5c-bf77-e63c59ec30c3', 'Pinball Porter', 'PORTER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12, 200, 12.95, '82739870323492', 1);
--rollback delete from beer where id in ('7b845a2d-129f-47e9-8a62-10c400cb8688', '8b42a329-0bc2-4efd-a76e-1f4b13ff1de7', '2b7039e8-a2ef-4d5c-bf77-e63c59ec30c3');

