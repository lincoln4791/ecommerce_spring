INSERT INTO category (name, description)
VALUES ('Uncategorized', 'Default category for old products')
ON CONFLICT (name) DO NOTHING;

-- Assign all old products to it
UPDATE products
SET category_id = (SELECT id FROM category WHERE name = 'Uncategorized')
WHERE category_id IS NULL;