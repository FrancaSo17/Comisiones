-- Insertar datos en la tabla Usuario
INSERT INTO Usuario (nombre, email, NIVEL_USUARIO) VALUES ('juan', 'creador1@example.com',9);
INSERT INTO Usuario (nombre, email, NIVEL_USUARIO) VALUES ('Juanito', 'gestor1@example.com',3);
INSERT INTO Usuario (nombre, email, NIVEL_USUARIO) VALUES ('Luquita', 'validador1@example.com',1);
INSERT INTO Usuario (nombre, email, NIVEL_USUARIO) VALUES ('Pipito', 'verificador1@example.com', 7);

-- Insertar datos en la tabla Users
INSERT INTO Users (username, password) VALUES ('juan', 'juan');

--Insertar datos de la tabla Estados
INSERT INTO Estado (id,codigo, descripcion) VALUES(1,1,'EN_VALIDACIÓN');
INSERT INTO Estado (id,codigo, descripcion) VALUES(2,2,'EN_VALIDACIÓN');
INSERT INTO Estado (id,codigo, descripcion) VALUES(3,3,'EN_VALIDACIÓN');
INSERT INTO Estado (id,codigo, descripcion) VALUES(4,4,'EN_VALIDACIÓN');
INSERT INTO Estado (id,codigo, descripcion) VALUES(5,5,'EN_VALIDACIÓN');

-- Insertar datos en la tabla Comsion
INSERT INTO Comision (titulo, descripcion, prioridad, USUARIO_CREADOR_ID,FECHACOMISION) 
VALUES ('Púnica', 'Redistribución', 1, 1,'2023-05-31');
INSERT INTO Comision (titulo, descripcion, prioridad, USUARIO_CREADOR_ID,FECHACOMISION) 
VALUES ('M.Rajoy', 'Quién es', 1, 1,'2023-05-31');
INSERT INTO Comision (titulo,  descripcion, prioridad, USUARIO_CREADOR_ID,FECHACOMISION) 
VALUES ('Lezo',  'Lowfare', 2, 2,'2023-05-30');
INSERT INTO Comision(titulo, descripcion, prioridad, USUARIO_CREADOR_ID,FECHACOMISION) 
VALUES('Mascarillas', 'investigación',3,3,'2024-05-30');
INSERT INTO Comision(titulo, descripcion, prioridad, USUARIO_CREADOR_ID,FECHACOMISION) 
VALUES('Dinero', 'investigación',4,4,'2023-05-30');
INSERT INTO Comision(titulo,  descripcion, prioridad, USUARIO_CREADOR_ID,FECHACOMISION) 
VALUES('Compras',  'investigación',1,1,'2013-05-30');


INSERT INTO Participante(nombre,apellidos,email,COMISION_ID) VALUES ('Leo','Messi','Lioonel@lapulgita',2);
INSERT INTO Participante(nombre,apellidos,email,COMISION_ID) VALUES ('Juan','Dávila','JD1983@gmail.com',1);
INSERT INTO Participante(nombre,apellidos,email,COMISION_ID) VALUES ('Leandro','Romero','hukou@gmail.com',3);
INSERT INTO Participante(nombre,apellidos,email,COMISION_ID) VALUES ('Lucia','Gonzales','JD1983@gmail.com',1);

