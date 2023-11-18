--
-- Dumping data for table garment
--
INSERT INTO garment
(prd_description,
prd_base_price,
prd_garment_type
)
VALUES
('Blue Shirt',10.24,'SHIRT'),
('White Shirt',100.50,'SHIRT'),
('Coat Dress',102.40,'COAT'),
('Beige Gabardine Pants',1004.00,'PANTS'),
('Black Covered',3234.22,'COVERED'),
('Skin Covered',232.20,'COVERED'),
('Light Gray Shirt',165.20,'SHIRT'),
('Gray Shirt',1645.24,'SHIRT'),
('Dark Grat Shirt',203.00,'SHIRT'),
('Scarf',34.45,'SCARF'),
('Socks',9898.00,'SOCK');

--
-- Dumping data for table client
--

INSERT INTO client (cli_last_name, cli_name)
VALUES
    ('González', 'Juan'),
    ('Martínez', 'María'),
    ('Rodríguez', 'Carlos'),
    ('Fernández', 'Ana'),
    ('López', 'David'),
    ('García', 'Sofía'),
    ('Pérez', 'José'),
    ('Gómez', 'Patricia'),
    ('Sánchez', 'Miguel'),
    ('Díaz', 'Lucía');

--
-- Dumping data for table sale
--

INSERT INTO sale (sale_type, sale_date, sale_cli_id)
VALUES
    ('Cash', '2023-01-01 10:00:00', 1),
    ('Card', '2023-01-02 11:00:00', 2),
    ('Cash', '2023-01-03 12:00:00', 3),
    ('Card', '2023-01-04 13:00:00', 4),
    ('Cash', '2023-01-05 14:00:00', 5),
    ('Card', '2023-01-06 15:00:00', 6),
    ('Cash', '2023-01-07 16:00:00', 7),
    ('Card', '2023-01-08 17:00:00', 8),
    ('Cash', '2023-01-09 18:00:00', 9),
    ('Card', '2023-01-10 19:00:00', 10);

--
-- Dumping data for table sale_item
--

INSERT INTO sale_item (itm_quantity, itm_prd_id, itm_sale_id)
VALUES
    (2, 1, 1),
    (1, 2, 2),
    (3, 3, 3),
    (4, 4, 4),
    (1, 5, 5),
    (2, 6, 6),
    (1, 7, 7),
    (3, 8, 8),
    (2, 9, 9),
    (1, 10, 10);

--
-- Dumping data for table sale_cash
--

INSERT INTO sale_cash (sale_id)
VALUES
    (1),
    (3),
    (5),
    (7),
    (9);

--
-- Dumping data for table sale_card
--

INSERT INTO sale_card (sale_id, sale_installment_quantity, sale_coefficient)
VALUES
    (2, 12, 1.05),
    (4, 6, 1.03),
    (6, 3, 1.02),
    (8, 18, 1.07),
    (10, 24, 1.09);
