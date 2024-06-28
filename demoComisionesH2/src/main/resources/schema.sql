-- src/main/resources/schema.sql
-- src/main/resources/schema.sql

-- Crear la tabla Usuario 
CREATE TABLE IF NOT EXISTS Usuario (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255),
    email VARCHAR(255),
    NIVEL_USUARIO INTEGER NULL,
    password VARCHAR(300) NULL
   
);

-- Crear la tabla users

CREATE TABLE IF NOT EXISTS Users(
 username  VARCHAR (200) PRIMARY KEY,
 password  VARCHAR (200),
 enabled boolean DEFAULT true);

CREATE TABLE IF NOT EXISTS Comision (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255),
    estado VARCHAR(255) DEFAULT 'pendiente',
    descripcion VARCHAR(255),
    prioridad INTEGER,
    USUARIO_CREADOR_ID INTEGER,
    USUARIO_GESTOR_ID INTEGER,
    USUARIO_VALIDADOR_ID INTEGER,
    USUARIO_VERIFICADOR_ID INTEGER,
    USUARIO_CANCELADOR_ID INTEGER,  -- Define the column here
    verificada BOOLEAN DEFAULT FALSE,
    validada BOOLEAN DEFAULT FALSE,
    cancelada BOOLEAN DEFAULT FALSE,
    aprobada BOOLEAN DEFAULT FALSE,
    motivoCancelacion VARCHAR(255),
    FECHACOMISION DATE NOT NULL,
    FECHAVERIFICACION DATE,
    FECHAVALIDACION DATE,
    FOREIGN KEY (USUARIO_CREADOR_ID) REFERENCES Usuario(id),
    FOREIGN KEY (USUARIO_VALIDADOR_ID) REFERENCES Usuario(id),
    FOREIGN KEY (USUARIO_VERIFICADOR_ID) REFERENCES Usuario(id),
    FOREIGN KEY (USUARIO_CANCELADOR_ID) REFERENCES Usuario(id)  -- Reference the column here
);


CREATE TABLE IF NOT EXISTS Participante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) ,
    apellidos VARCHAR(255),
    email VARCHAR(255),
    COMISION_ID INTEGER
);

CREATE TABLE IF NOT EXISTS Estado(
	id INTEGER PRIMARY KEY,
	descripcion VARCHAR(300),
	codigo INTEGER
);
