-- ==============================================================
-- UNIFIED DATABASE SETUP SCRIPT (NORMALIZED SCHEMA)
-- Car Rental System Schema Definition & Initial Seeds
-- ==============================================================

-- 1. Create and select the database
CREATE DATABASE IF NOT EXISTS hello;
USE hello;

-- 2. Create the users table (Sprint 1: Account Recovery System)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    security_question VARCHAR(255) NOT NULL,
    security_answer VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'user'
);

-- 3. Create the brands table (Sprint 2 Extension: Brand Selection)
CREATE TABLE IF NOT EXISTS brands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    logo_path VARCHAR(255) DEFAULT NULL
);

-- 4. Create the cars table (Sprint 2: Fleet Management Inventory - Normalized)
CREATE TABLE IF NOT EXISTS cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    brand_id INT NOT NULL,
    model VARCHAR(50) NOT NULL,
    category VARCHAR(20) NOT NULL,
    price_per_day DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Available',
    FOREIGN KEY (brand_id) REFERENCES brands(id) ON DELETE CASCADE
);

-- 5. Seed initial brands (Only if not already present)
INSERT INTO brands (name, logo_path) 
SELECT 'Tesla', '/images/logos/tesla.png'
WHERE NOT EXISTS (SELECT 1 FROM brands WHERE name = 'Tesla');

INSERT INTO brands (name, logo_path) 
SELECT 'Ford', '/images/logos/ford.png'
WHERE NOT EXISTS (SELECT 1 FROM brands WHERE name = 'Ford');

INSERT INTO brands (name, logo_path) 
SELECT 'Toyota', '/images/logos/toyota.png'
WHERE NOT EXISTS (SELECT 1 FROM brands WHERE name = 'Toyota');

INSERT INTO brands (name, logo_path) 
SELECT 'BMW', '/images/logos/bmw.png'
WHERE NOT EXISTS (SELECT 1 FROM brands WHERE name = 'BMW');

INSERT INTO brands (name, logo_path) 
SELECT 'Audi', '/images/logos/audi.png'
WHERE NOT EXISTS (SELECT 1 FROM brands WHERE name = 'Audi');

-- 6. Seed initial cars into inventory (Only if not already present)
INSERT INTO cars (brand_id, model, category, price_per_day, status)
SELECT 1, 'Model S', 'Luxury', 120.00, 'Available'
WHERE NOT EXISTS (SELECT 1 FROM cars WHERE model = 'Model S' AND brand_id = 1);

INSERT INTO cars (brand_id, model, category, price_per_day, status)
SELECT 2, 'Mustang GT', 'Sports', 95.00, 'Available'
WHERE NOT EXISTS (SELECT 1 FROM cars WHERE model = 'Mustang GT' AND brand_id = 2);

INSERT INTO cars (brand_id, model, category, price_per_day, status)
SELECT 3, 'RAV4', 'SUV', 65.00, 'Available'
WHERE NOT EXISTS (SELECT 1 FROM cars WHERE model = 'RAV4' AND brand_id = 3);

INSERT INTO cars (brand_id, model, category, price_per_day, status)
SELECT 4, '3 Series', 'Sedan', 85.00, 'Available'
WHERE NOT EXISTS (SELECT 1 FROM cars WHERE model = '3 Series' AND brand_id = 4);

INSERT INTO cars (brand_id, model, category, price_per_day, status)
SELECT 5, 'e-tron', 'Luxury', 130.00, 'Maintenance'
WHERE NOT EXISTS (SELECT 1 FROM cars WHERE model = 'e-tron' AND brand_id = 5);

-- 7. Verify the setups
SELECT * FROM users;
SELECT * FROM brands;
SELECT * FROM cars;
