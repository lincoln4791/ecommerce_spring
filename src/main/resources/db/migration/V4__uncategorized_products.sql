INSERT INTO category (name, description)
VALUES ('Uncategorized', 'Default category for old products')
ON CONFLICT (name) DO NOTHING;

-- Assign all old products to it
UPDATE products
SET id = (SELECT id FROM category WHERE name = 'Uncategorized')
WHERE id IS NULL;