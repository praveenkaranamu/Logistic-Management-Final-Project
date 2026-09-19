CREATE DATABASE logistics_db;

USE logistics_db;

ALTER TABLE shipments
ADD COLUMN order_id BIGINT;

DESCRIBE shipments;

ALTER TABLE shipments
ADD CONSTRAINT fk_shipment_order
FOREIGN KEY (order_id)
REFERENCES logistics_orders(id);

INSERT INTO shipments
(tracking_number, carrier, shipment_status, shipped_date, expected_delivery_date, order_id)
VALUES
('TRK00002', 'BlueDart', 'PENDING', NULL, DATE_ADD(NOW(), INTERVAL 3 DAY), 2),
('TRK00003', 'DTDC', 'SHIPPED', NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), 3),
('TRK00004', 'Delhivery', 'DELIVERED', NOW(), DATE_ADD(NOW(), INTERVAL 1 DAY), 4),
('TRK00005', 'FedEx', 'PENDING', NULL, DATE_ADD(NOW(), INTERVAL 4 DAY), 5),
('TRK00006', 'DHL', 'SHIPPED', NOW(), DATE_ADD(NOW(), INTERVAL 2 DAY), 6),
('TRK00007', 'BlueDart', 'PENDING', NULL, DATE_ADD(NOW(), INTERVAL 5 DAY), 7),
('TRK00008', 'DTDC', 'SHIPPED', NOW(), DATE_ADD(NOW(), INTERVAL 3 DAY), 8),
('TRK00009', 'Delhivery', 'PENDING', NULL, DATE_ADD(NOW(), INTERVAL 4 DAY), 9);

SELECT * FROM customers;

UPDATE logistics_orders o
JOIN customers c
ON o.customer_name = c.name
SET o.customer_id = c.id;

SELECT id, customer_name, customer_id
FROM logistics_orders;

SELECT * FROM shipments;

INSERT INTO shipments
(tracking_number, carrier, shipment_status, shipped_date, expected_delivery_date, order_id)
SELECT
CONCAT('TRK', LPAD(id, 5, '0')),
'BlueDart',
'PENDING',
NULL,
DATE_ADD(NOW(), INTERVAL 3 DAY),
id
FROM logistics_orders;

SELECT * FROM shipments;

SELECT
    o.id AS order_id,
    o.customer_name,
    o.source,
    o.destination,
    s.tracking_number,
    s.carrier,
    s.shipment_status,
    s.shipped_date,
    s.expected_delivery_date
FROM logistics_orders o
JOIN shipments s
ON o.id = s.order_id;USE logistics_db;

INSERT INTO shipments
(tracking_number, carrier, shipment_status, shipped_date, expected_delivery_date, order_id)
SELECT
    CONCAT('TRK', LPAD(id, 5, '0')),
    'BlueDart',
    'PENDING',
    NULL,
    DATE_ADD(NOW(), INTERVAL 3 DAY),
    id
FROM logistics_orders;

SHOW CREATE TABLE logistics_orders;

SHOW CREATE TABLE shipments;

SELECT * FROM logistics_orders;
SELECT * FROM customers;