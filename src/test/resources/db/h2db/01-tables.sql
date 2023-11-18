--
-- Drop Table structure for table Garment
--
DROP TABLE IF EXISTS garment;

--
-- Table structure for table garment
--
CREATE TABLE garment (
                         prd_id bigint NOT NULL AUTO_INCREMENT,
                         prd_description varchar(255) DEFAULT NULL,
                         prd_base_price decimal(19,2) DEFAULT NULL,
                         prd_garment_type varchar(255) DEFAULT NULL,
                         PRIMARY KEY (prd_id)
);

--
-- Drop Table structure for table Client
--
DROP TABLE IF EXISTS client;

--
-- Table structure for table client
--
CREATE TABLE client (
                        cli_id bigint NOT NULL AUTO_INCREMENT,
                        cli_last_name varchar(255) DEFAULT NULL,
                        cli_name varchar(255) DEFAULT NULL,
                        PRIMARY KEY (cli_id)
);

--
-- Drop Table structure for table sale
--
DROP TABLE IF EXISTS sale;

--
-- Table structure for table sale
--
CREATE TABLE sale (
                      sale_id bigint NOT NULL AUTO_INCREMENT,
                      sale_type varchar(31) NOT NULL,
                      sale_date datetime(6) DEFAULT NULL,
                      sale_cli_id bigint DEFAULT NULL,
                      PRIMARY KEY (sale_id),
                      KEY sale_cli_fk (sale_cli_id),
                      CONSTRAINT sale_cli_fk FOREIGN KEY (sale_cli_id) REFERENCES client (cli_id)
);

--
-- Drop Table structure for table sale_item
--
DROP TABLE IF EXISTS sale_item;

--
-- Table structure for table sale_item
--
CREATE TABLE sale_item (
                           itm_id bigint NOT NULL AUTO_INCREMENT,
                           itm_quantity int DEFAULT NULL,
                           itm_prd_id bigint DEFAULT NULL,
                           itm_sale_id bigint NOT NULL,
                           PRIMARY KEY (itm_id),
                           KEY itm_sale_fk (itm_sale_id),
                           CONSTRAINT itm_sale_fk FOREIGN KEY (itm_sale_id) REFERENCES sale (sale_id),
                           KEY itm_prd_fk (itm_prd_id),
                           CONSTRAINT itm_prd_fk FOREIGN KEY (itm_prd_id) REFERENCES garment (prd_id)
);

--
-- Drop Table structure for table sale_cash
--
DROP TABLE IF EXISTS sale_cash;

--
-- Table structure for table sale_cash
--
CREATE TABLE sale_cash (
                           sale_id bigint NOT NULL,
                           PRIMARY KEY (sale_id),
                           CONSTRAINT sale_cash_fk FOREIGN KEY (sale_id) REFERENCES sale (sale_id)
);

--
-- Drop Table structure for table sale_card
--
DROP TABLE IF EXISTS sale_card;

--
-- Table structure for table sale_card
--
CREATE TABLE sale_card (
                           sale_id bigint NOT NULL,
                           sale_installment_quantity int DEFAULT NULL,
                           sale_coefficient decimal(5,2) DEFAULT NULL,
                           PRIMARY KEY (sale_id),
                           CONSTRAINT sale_card_fk FOREIGN KEY (sale_id) REFERENCES sale (sale_id)
);


