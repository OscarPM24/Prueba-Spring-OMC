INSERT INTO user (name, username, password, street, city, zipcode, country) VALUES
    ('Oscar', 'oscar434', '1234', 'Carrer de Rosselló', 'Barcelona', '08001', 'España'),
    ('Javier', 'javi123', '4567', 'Carrer de Pallars', 'Paris', '24684', 'Francia'),
    ('Ivan', 'ivan785', '9876', 'Carrer de Roger de Flor', 'New York', '35796', 'EEUU');

INSERT INTO todo (title, completed, user) VALUES
('Comprar pan', false, 1),
('Pagar facturas', true, 1),
('Ir a clase', false, 2),
('Hacer deberes', true, 2),
('Aprender Java', false, 3),
('Vender ordenador', true, 3);