-- ============================
-- 1. Brands Table
-- ============================
CREATE TABLE brands (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);


-- ============================
-- 2. Models Table
-- ============================
CREATE TABLE models (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    brand_id BIGINT NOT NULL,
    CONSTRAINT fk_models_brands
        FOREIGN KEY (brand_id)
        REFERENCES brands(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT unique_brand_model UNIQUE (brand_id, name)
);

-- Index for fast filtering by brand
CREATE INDEX idx_models_brand_id ON models (brand_id);



-- ============================
-- 3. Alter Products Table
-- ============================
ALTER TABLE products
ADD COLUMN model_id BIGINT;

ALTER TABLE products
ADD CONSTRAINT fk_products_models
FOREIGN KEY (model_id)
    REFERENCES models(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;

CREATE INDEX idx_products_model_id ON products(model_id);



-- ============================
-- 4. Seed Brands
-- ============================
INSERT INTO brands (name, description) VALUES
('Samsung', 'Electronics, smartphones, appliances'),
('Apple', 'iPhones, MacBooks, gadgets'),
('Sony', 'TVs, audio systems, cameras'),
('Nike', 'Sportswear, shoes, accessories'),
('Adidas', 'Shoes, sports goods, apparel'),
('Puma', 'Sportswear and lifestyle fashion'),
('LG', 'Electronics and home appliances'),
('Dell', 'Laptops, monitors, computers'),
('HP', 'Laptops, printers, electronics'),
('Lenovo', 'Laptops, tablets, electronics'),
('Xiaomi', 'Mobile phones, gadgets, smart devices'),
('Oppo', 'Smartphones and accessories'),
('Vivo', 'Smartphones and electronics'),
('OnePlus', 'Phones and consumer gadgets'),
('Canon', 'Cameras, lenses, imaging'),
('Ikea', 'Home furniture, decor, appliances'),
('Philips', 'Home appliances, electronics'),
('Reebok', 'Sportswear and accessories'),
('Zara', 'Fashion and clothing'),
('H&M', 'Fashion and apparel'),
('Under Armour', 'Sportswear and accessories');




-- ============================
-- 5. Seed Models
-- ============================

INSERT INTO models (name, description, brand_id) VALUES
-- Samsung (brand_id = 1)
('Galaxy S24', 'Flagship smartphone', 1),
('Galaxy A54', 'Mid-range smartphone', 1),
('Galaxy M34', 'Budget smartphone', 1),

-- Apple (brand_id = 2)
('iPhone 15', 'Latest Apple iPhone', 2),
('iPhone 14', 'Previous generation iPhone', 2),
('MacBook Pro 14', 'Professional laptop', 2),

-- Sony (brand_id = 3)
('Bravia X90L', 'Sony UHD TV model', 3),
('Alpha A7 IV', 'Mirrorless camera', 3),

-- Nike (brand_id = 4)
('Air Max 2024', 'Sports footwear', 4),
('Pegasus 40', 'Running shoe', 4),

-- Adidas (brand_id = 5)
('Ultraboost 23', 'Running shoe model', 5),
('Samba OG', 'Classic sneaker', 5),

-- Puma (brand_id = 6)
('Puma Velocity Nitro 3', 'Running shoes', 6),
('Puma RS-X', 'Lifestyle sneaker', 6),

-- LG (brand_id = 7)
('LG C3 OLED', 'Premium OLED TV', 7),
('LG Gram 16', 'Lightweight laptop', 7),

-- Dell (brand_id = 8)
('XPS 15', 'High-performance laptop', 8),
('Inspiron 14', 'Affordable laptop', 8),

-- HP (brand_id = 9)
('Pavilion 15', 'Everyday laptop', 9),
('OMEN 16', 'Gaming laptop', 9),

-- Lenovo (brand_id = 10)
('ThinkPad X1 Carbon', 'Business ultrabook', 10),
('Legion 5', 'Gaming laptop series', 10),

-- Xiaomi (brand_id = 11)
('Redmi Note 13', 'Budget smartphone', 11),
('Xiaomi 13 Pro', 'Flagship camera phone', 11),

-- Oppo (brand_id = 12)
('Reno 10', 'Camera-centric smartphone', 12),
('Oppo A78', 'Entry-level device', 12),

-- Vivo (brand_id = 13)
('Vivo V27', 'Premium midrange phone', 13),
('Vivo Y16', 'Budget phone', 13),

-- OnePlus (brand_id = 14)
('OnePlus 12', 'Flagship killer', 14),
('OnePlus Nord 3', 'Affordable premium device', 14),

-- Canon (brand_id = 15)
('EOS R6 Mark II', 'Full-frame mirrorless', 15),
('EOS 90D', 'Advanced DSLR', 15),

-- Ikea (brand_id = 16)
('Malm Bed Frame', 'Bedroom furniture series', 16),
('Billy Bookcase', 'Popular bookshelf model', 16),

-- Philips (brand_id = 17)
('Philips Airfryer XXL', 'Home kitchen appliance', 17),
('Philips Hue Bulb', 'Smart LED lighting', 17),

-- Reebok (brand_id = 18)
('Reebok Nano X3', 'Training and fitness shoes', 18),
('Reebok Classic Leather', 'Iconic sneaker', 18),

-- Zara (brand_id = 19)
('Zara Slim Fit Shirt', 'Menswear clothing model', 19),
('Zara TRF Jacket', 'Womenswear jacket model', 19),

-- H&M (brand_id = 20)
('H&M Regular Fit Tee', 'Basic clothing model', 20),
('H&M Denim Jacket', 'Fashion model jacket', 20),

-- Under Armour (brand_id = 21)
('UA HOVR Sonic 5', 'Running shoes', 21),
('UA Tech 2.0 Tee', 'Sportswear model', 21);






-- ============================
-- 6.Inserting new products
-- ============================

INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Samsung Galaxy S24 Ultra 256GB', 1199.99, 100, 1, 1),
('Samsung Galaxy A54 256GB', 389.99, 100, 1, 2),

('Apple iPhone 15 Pro 256GB', 1299.99, 100, 1, 4),
('Apple iPhone 14 Plus 128GB', 899.99, 100, 1, 5),

('Xiaomi Redmi Note 13 Pro 5G', 299.99, 100, 1, 17),
('Xiaomi 13 Pro 512GB', 1199.99, 100, 1, 18),

('Oppo Reno 10 Pro 256GB', 549.99, 100, 1, 19),
('Oppo A78 256GB', 219.99, 100, 1, 20),

('Vivo V27 Pro 256GB', 449.99, 100, 1, 21),
('Vivo Y16 128GB', 179.99, 100, 1, 22),

('OnePlus 12R 256GB', 499.99, 100, 1, 23),
('OnePlus Nord 3 256GB', 449.99, 100, 1, 24);


-- ============================
-- LAPTOPS (category_id = 2)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('MacBook Pro 14-inch M3', 2299.99, 100, 2, 6),
('MacBook Pro 14-inch M2', 1899.99, 100, 2, 6),

('Dell XPS 15 OLED Edition', 1999.99, 100, 2, 13),
('Dell Inspiron 14 Plus', 749.99, 100, 2, 14),

('HP Pavilion x360 14', 699.99, 100, 2, 15),
('HP Omen 16 RTX 4060', 1499.99, 100, 2, 16),

('Lenovo ThinkPad X1 Carbon Touch', 1699.99, 100, 2, 17),
('Lenovo Legion 5 Pro', 1299.99, 100, 2, 18);


-- ============================
-- TABLETS (category_id = 3)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Apple iPad Pro 11-inch M2', 999.99, 100, 3, 4),
('Samsung Galaxy Tab S9', 899.99, 100, 3, 1),
('Xiaomi Pad 6', 399.99, 100, 3, 17);


-- ============================
-- SMART WATCHES (category_id = 4)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Apple Watch Series 9', 399.99, 100, 4, 4),
('Samsung Galaxy Watch 6', 299.99, 100, 4, 1),
('Xiaomi Mi Band 8', 49.99, 100, 4, 17);


-- ============================
-- TELEVISIONS (category_id = 5)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Sony Bravia A80L OLED 65-inch', 1999.99, 100, 5, 7),
('LG G3 OLED Evo 65-inch', 2399.99, 100, 5, 9),
('Samsung QN90C Neo QLED 55-inch', 1299.99, 100, 5, 1);


-- ============================
-- HEADPHONES (category_id = 6)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Sony WH-XB910N', 199.99, 100, 6, 8),
('Xiaomi Buds 4 Pro', 129.99, 100, 6, 17),
('Philips TAH8506 ANC', 129.99, 100, 6, 29);


-- ============================
-- CAMERAS (category_id = 7)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Sony Alpha A7 III', 1599.99, 100, 7, 8),
('Canon EOS R8', 1499.99, 100, 7, 25),
('Canon EOS M50 Mark II', 749.99, 100, 7, 26);


-- ============================
-- SPEAKERS (category_id = 9)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Sony SRS-XB43 Bluetooth Speaker', 249.99, 100, 9, 8),
('Xiaomi Mi Portable Speaker', 39.99, 100, 9, 17),
('Philips SoundBar 2.1', 149.99, 100, 9, 29);


-- ============================
-- COMPUTER ACCESSORIES (category_id = 10)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Dell Wireless Mouse WM126', 29.99, 100, 10, 13),
('HP USB Keyboard K150', 19.99, 100, 10, 15),
('Lenovo 512GB Portable SSD', 79.99, 100, 10, 17);


-- ============================
-- HOME APPLIANCES (category_id = 11)
-- ============================
INSERT INTO products (name, price, stock, category_id, model_id) VALUES
('Philips Airfryer XXL', 199.99, 100, 11, 30),
('LG Dual Inverter AC 1.5 Ton', 599.99, 100, 11, 9),
('Ikea Hemnes Bed Frame', 299.99, 100, 11, 27),
('Ikea Poäng Armchair', 149.99, 100, 11, 28);

