SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE category;
TRUNCATE TABLE subscription;
TRUNCATE TABLE customer;
SET REFERENTIAL_INTEGRITY TRUE;
ALTER TABLE category ALTER COLUMN id RESTART WITH 1;
ALTER TABLE customer ALTER COLUMN customer_id RESTART WITH 1;
ALTER TABLE subscription ALTER COLUMN id RESTART WITH 1;

INSERT INTO category(name, available_content, price) VALUES
('Dutch Films', 10, 4.0),
('Dutch Series', 20, 6.0),
('International Films', 20, 10.0);

INSERT INTO customer(first_name, last_name, email, password) VALUES
('Spingboot', 'Java', 'java@sogeti.com', '$2y$12$mWnIVHyk0b.A0JzD16i.2OpWT9aYiZHpPtuskSlFokoMpCV/oQiBG'),
('Dev', 'Tammini', 'dev@sogeti.com', '$2y$12$m7dg.lreS1GaFXZZnn/VF.i8m.74pzeDUhL4W7onsQ2VZhjnadWau');

INSERT INTO subscription(customer_id, name, remaining_content, price, owner, start_date) VALUES
(1, 'International Films', 10, 5.0, 'java@sogeti.com',  '2021-01-01' ),
(2, 'International Films', 10, 5.0,  'java@sogeti.com', '2021-01-01' ),
(2, 'Dutch Films', 10, 4.0, 'dev@sogeti.com', '2021-01-01' );
