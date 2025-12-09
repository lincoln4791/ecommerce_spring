CREATE TABLE category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

INSERT INTO category (name, description) VALUES
('Electronics', 'Phones, laptops, gadgets'),
('Fashion', 'Clothes, shoes, accessories'),
('Home & Kitchen', 'Furniture, decor, appliances'),
('Sports', 'Sports goods and equipment'),
('Books', 'All kinds of books');

ALTER TABLE products
ADD COLUMN category_id BIGINT;

ALTER TABLE products
ADD CONSTRAINT pk_products_category
FOREIGN KEY(category_id)
REFERENCES category(id)
ON DELETE SET NULL
ON UPDATE CASCADE;