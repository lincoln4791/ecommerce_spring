CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

INSERT INTO category (name, description) VALUES
('Mobile Phones', 'Smartphones and feature phones'),
('Laptops', 'Personal and professional laptops'),
('Tablets', 'Android and iOS tablets'),
('Smart Watches', 'Wearable tech and fitness bands'),
('Televisions', 'LED, QLED, OLED TVs'),
('Headphones', 'Bluetooth and wired headphones'),
('Cameras', 'DSLR, mirrorless, and action cameras'),
('Gaming Consoles', 'PlayStation, Xbox, Nintendo'),
('Speakers', 'Bluetooth speakers and sound systems'),
('Computer Accessories', 'Mouse, keyboard, SSD, RAM'),
('Home Appliances', 'Fridge, AC, microwave, washing machine');

ALTER TABLE products
ADD COLUMN category_id BIGINT;

ALTER TABLE products
ADD CONSTRAINT fk_products_category
FOREIGN KEY(category_id)
REFERENCES category(id)
ON DELETE SET NULL
ON UPDATE CASCADE;

CREATE INDEX idx_products_category_id
ON products(category_id);

