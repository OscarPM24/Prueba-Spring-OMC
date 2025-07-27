INSERT IGNORE INTO user (id, name, username, password, street, city, zipcode, country) VALUES
    (1, 'Oscar', 'oscar434', '$2a$10$rXK1g7QH8VJ5hUaLJrn8KeWE5ydFt5WKj2mQJ8N9jJHK7L9mN0oP6', 'Carrer de Rosselló', 'Barcelona', '08001', 'España'),
    (2, 'Javier', 'javi123', '$2a$10$mN5hK8jL7Q3vB9cD2eF6gH4iJ1kL8mN9oP0qR2sT4uV6wX8yZ0aB1', 'Carrer de Pallars', 'Paris', '24684', 'Francia'),
    (3, 'Ivan', 'ivan785', '$2a$10$sT8uV6wX0yZ2aB4cD6eF8gH1iJ3kL5mN7oP9qR1sT3uV5wX7yZ9aB', 'Carrer de Roger de Flor', 'New York', '35796', 'EEUU');

/* Passwords de ejemplo:
    - Oscar: 1234
    - Javier: 4567
    - Ivan: 9876
 */

INSERT IGNORE INTO todo (id, title, completed, user) VALUES
(1, 'Comprar pan', false, 1),
(2, 'Pagar facturas', true, 1),
(3, 'Ir a clase', false, 2),
(4, 'Hacer deberes', true, 2),
(5, 'Aprender Java', false, 3),
(6, 'Vender ordenador', true, 3);

