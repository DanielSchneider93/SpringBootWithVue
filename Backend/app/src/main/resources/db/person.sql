CREATE TABLE IF NOT EXISTS person (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    zip CHAR(5),
    city VARCHAR(100),
    color VARCHAR(50)
);