/* Creamos Clientes */
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (1, 'Lilibeth', 'Camico', 'lili@gmail.com', '2019-10-26', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (2, 'Leonardo', 'Vivero', 'leo@gmail.com', '2019-10-26', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (3, 'Luis', 'Camico', 'luiso@gmail.com', '2019-10-21', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (4, 'Lee', 'Acosta', 'lee@gmail.com', '2019-09-26', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (5, 'Maria', 'Santaella', 'ana@gmail.com', '2019-10-01', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (6, 'Lusi', 'Herrera', 'lusi@gmail.com', '2019-10-22', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (7, 'Adrianna', 'Acosta', 'adri@gmail.com', '2019-04-26', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (8, 'Juan', 'Marquez', 'juano@gmail.com', '2019-10-10', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (9, 'Moises', 'Vivero', 'moises@gmail.com', '2019-10-03', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (10, 'Alana', 'Esteban', 'Alana@gmail.com', '2019-09-26', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (11, 'Jose', 'Marquez', 'jose@gmail.com', '2019-07-25', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (12, 'Monica', 'Macias', 'Moni@gmail.com', '2019-07-04', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (13, 'Cristiano', 'Ronaldo', 'cristiano@gmail.com', '2019-08-26', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (14, 'Messi', 'Leonel', 'leonel@gmail.com', '2019-10-23', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (15, 'Oscar', 'Henriquez', 'osc@gmail.com', '2019-07-16', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (16, 'Dayana', 'vazquez', 'daya@gmail.com', '2019-10-14', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (17, 'Luismar', 'Camico', 'luism@gmail.com', '2019-10-09', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (18, 'Ameli', 'Castillo', 'casti@gmail.com', '2019-10-06', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (19, 'Avril', 'Camico', 'avril@gmail.com', '2019-10-05', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (20, 'Anthonella', 'Camico', 'antho@gmail.com', '2019-10-13', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (21, 'Juana', 'Vivero', 'viv@gmail.com', '2019-10-20', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (22, 'Luis', 'Herrera', 'lh@gmail.com', '2019-10-23', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (23, 'Miguel', 'Acosta', 'miguelo@gmail.com', '2019-10-19', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (24, 'Maryelin', 'Yepez', 'maye@gmail.com', '2019-10-18', '');
INSERT INTO clientes (id, nombre, apellido, correo, create_at, foto) VALUES (25, 'Karlha', 'Magliocco', 'karlhaM@gmail.com', '2019-10-30', '');

/* Creamos algunos Productos */
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Lapto HP', 25700, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Lapto DELL', 23500, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Sonido Panasonic', 60500, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Telefono Sansumg', 15700, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Telefono Google', 20700, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Computadora DELL', 55700, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Auriculares VB', 9770, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Corneta Grande', 2650, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Disco Duro 6TB', 45700, NOW());
INSERT INTO productos (nombre, precio, create_at ) VALUES ('Tablet Sansumg', 43800, NOW());

/* Creamos algunas facturas */
INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura equipos de oficina', null, 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 1);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(2, 1, 4);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 5);
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(1, 1, 7);

INSERT INTO facturas (descripcion, observacion, cliente_id, create_at) VALUES('Factura PC', 'Alguna nota importante!', 1, NOW());
INSERT INTO facturas_items (cantidad, factura_id, producto_id) VALUES(3, 2, 6);

/*Creamos Usuarioos con sus Roles*/
INSERT INTO users (username, password, enabled) VALUES('ana', '$2a$10$5sNDZEZ9gJs3jCueIqiADuyJeqgHB/B6E/ggbhEXDubK.bv3TX8JK', 1);
INSERT INTO users (username, password, enabled) VALUES('admin', '$2a$10$he3F754D8mussh2Wr5/5yuctWgSzxJ4O445knHAzBuF8dSfjR.Whq', 1);

INSERT INTO authorities (user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities (user_id, authority) VALUES(2, 'ROLE_ADMIN');
