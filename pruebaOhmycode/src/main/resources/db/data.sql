INSERT IGNORE INTO user (id, name, username, password, street, city, zipcode, country) VALUES
    (1, 'Oscar', 'oscar434', '$2a$12$/0LLVvaEvP3TDU7XvsjpWOPuohFbU3kR7jVC.a6ZEIqoSx4vSR9ti', 'Carrer de Rosselló', 'Barcelona', '08001', 'España'),
    (2, 'Javier', 'javi123', '$2a$12$VhU5wD/OrP0Iy1u2zuR7tudwz06oteA3njjUcnEbh/bTDK.RMZQse', 'Carrer de Pallars', 'Paris', '24684', 'Francia'),
    (3, 'Ivan', 'ivan785', '$2a$12$DaJcHewx0lAZcIEAbZKZw.rBasrVS4Wmvrtvvm3n46z/wcvXr94G2', 'Carrer de Roger de Flor', 'New York', '35796', 'EEUU');

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

