CREATE DATABASE assignment_db;
\c assignment_db;

CREATE OR REPLACE FUNCTION random_between(low INT ,high INT)
   RETURNS INT AS
$$
BEGIN
   RETURN floor(random()* (high-low + 1) + low);
END;
$$ language 'plpgsql' STRICT;

CREATE TABLE payment_type (
	payment_type_id SERIAL PRIMARY KEY,
	type_name VARCHAR (255) UNIQUE NOT NULL
);

INSERT INTO payment_type (type_name)
    SELECT concat('Payment Type - ', left(md5(random()::text), 5))
    FROM generate_series(1, 10) s(i);

CREATE TABLE payment (
    payment_id BIGSERIAL PRIMARY KEY,
    amount BIGINT NOT NULL CHECK (amount > 0),
    payment_type_id SERIAL,
    date DATE NOT NULL DEFAULT CURRENT_DATE,
    customer_id BIGSERIAL,
    CONSTRAINT fk_payment_type FOREIGN KEY(payment_type_id) REFERENCES payment_type(payment_type_id)
);

INSERT INTO payment (amount, payment_type_id, customer_id)
    SELECT random_between(1000, 10000), random_between(1, 10), i
    FROM generate_series(1, 1000) s(i);

INSERT INTO payment (amount, payment_type_id, customer_id)
    SELECT random_between(1000, 10000), random_between(1, 10), 1
    FROM generate_series(1, 1500000) s(i);

CREATE TABLE inventory (
    item_id BIGSERIAL PRIMARY KEY,
    item_name VARCHAR (255),
    quantity SMALLINT NOT NULL,
    price BIGINT NOT NULL CHECK (price > 0)
);

INSERT INTO inventory (item_name, quantity, price)
SELECT concat('Inventory - ', left(md5(random()::text), 5)),
random_between(1, 100),
random_between(1000, 10000) FROM generate_series(1, 1000000) s(i);