-- Crear tabla INTERESBOTANICO
CREATE TABLE INTERESBOTANICO (
  interesId     NUMBER(5) PRIMARY KEY, 
  nombreInteres VARCHAR2(30) NOT NULL UNIQUE
);

-- Crear tabla INSECTO
CREATE TABLE INSECTO (
  insectoId               NUMBER(11) PRIMARY KEY, 
  nombreCientificoInsecto VARCHAR2(50) NOT NULL UNIQUE, 
  nombreComunInsecto      VARCHAR2(30) NOT NULL UNIQUE, 
  descripcion             VARCHAR2(1000)
);

-- Crear tabla PLANTA
CREATE TABLE PLANTA (
  plantaId               NUMBER(11) PRIMARY KEY, 
  nombreCientificoPlanta VARCHAR2(50) NOT NULL UNIQUE, 
  nombreComunPlanta      VARCHAR2(30) NOT NULL UNIQUE, 
  descrpcion 		 VARCHAR2(300),
  tipoPlanta             VARCHAR2(30) NOT NULL, 
  cuidadosEspecificos    VARCHAR2(300), 
  imagen                 VARCHAR2(150) NOT NULL UNIQUE, 
  CONSTRAINT tipoPlantaValid 
    CHECK (LOWER(tipoPlanta) IN ('arbol', 'arbusto', 'hierba', 'plantas suculentas', 'plantas trepadoras', 'higuerones', 'helechos', 'musgos', 'hepaticas', 'plantas insectivoras', 'flor') )
);


-- Crear tabla USUARIO
CREATE TABLE USUARIO (
  usuarioId                NUMBER(11) PRIMARY KEY, 
  nombreUsuario            VARCHAR2(15) NOT NULL UNIQUE, 
  contraseña               VARCHAR2(16), 
  nombre                   VARCHAR2(30), 
  apellido1                VARCHAR2(30), 
  apellido2                VARCHAR2(30), 
  dni                      VARCHAR2(9) UNIQUE, 
  correo                   VARCHAR2(50) NOT NULL UNIQUE, 
  InteresBotanicointeresId NUMBER(5) NOT NULL, 
  CONSTRAINT emailValido 
    CHECK (REGEXP_LIKE(correo, '^[^@]+@[^@]+\.(com|es)$') ), 
  CONSTRAINT usuarioNoCarEspeciales 
    CHECK (REGEXP_LIKE(nombreUsuario, '^[a-zA-Z0-9]+$')), 
  CONSTRAINT fk_interes_botanico FOREIGN KEY (InteresBotanicointeresId) REFERENCES INTERESBOTANICO (interesId) ON DELETE CASCADE
);


-- Crear tabla GUIA
CREATE TABLE GUIA (
  guiaId         NUMBER(11) PRIMARY KEY, 
  titulo         VARCHAR2(50) NOT NULL, 
  contenido      VARCHAR2(1500) NOT NULL, 
  PLANTAplantaId NUMBER(11) NOT NULL, 
  CONSTRAINT fk_planta FOREIGN KEY (PLANTAplantaId) REFERENCES PLANTA (plantaId) ON DELETE CASCADE
);

-- Crear tabla RESEÑA
CREATE TABLE RESEÑA (
  reseñaId         NUMBER(11) PRIMARY KEY, 
  calificacion     NUMBER(5,2) NOT NULL, 
  comentario       VARCHAR2(200), 
  fechaReseña      DATE NOT NULL, 
  USUARIOusuarioId NUMBER(11) NOT NULL, 
  GUIAguiaId       NUMBER(11) NOT NULL, 
  CONSTRAINT calificacionValida 
    CHECK (calificacion >= 1 AND calificacion <= 5),
  CONSTRAINT fk_usuario FOREIGN KEY (USUARIOusuarioId) REFERENCES USUARIO (usuarioId) ON DELETE CASCADE,
  CONSTRAINT fk_guia FOREIGN KEY (GUIAguiaId) REFERENCES GUIA (guiaId) ON DELETE CASCADE
);

-- Crear tabla USUARIO_PLANTA
CREATE TABLE USUARIO_PLANTA (
  USUARIOusuarioId NUMBER(11) NOT NULL, 
  PLANTAplantaId   NUMBER(11) NOT NULL, 
  CONSTRAINT fk_usuario_planta_usuario FOREIGN KEY (USUARIOusuarioId) REFERENCES USUARIO (usuarioId) ON DELETE CASCADE,
  CONSTRAINT fk_usuario_planta_planta FOREIGN KEY (PLANTAplantaId) REFERENCES PLANTA (plantaId) ON DELETE CASCADE,
  PRIMARY KEY (USUARIOusuarioId, PLANTAplantaId)
);

-- Crear tabla PLANTA_INSECTO
CREATE TABLE PLANTA_INSECTO (
  PLANTAplantaId   NUMBER(11) NOT NULL, 
  INSECTOinsectoId NUMBER(11) NOT NULL, 
  CONSTRAINT fk_planta_insecto_planta FOREIGN KEY (PLANTAplantaId) REFERENCES PLANTA (plantaId) ON DELETE CASCADE,
  CONSTRAINT fk_planta_insecto_insecto FOREIGN KEY (INSECTOinsectoId) REFERENCES INSECTO (insectoId) ON DELETE CASCADE,
  PRIMARY KEY (PLANTAplantaId, INSECTOinsectoId)
);


create or replace TRIGGER "BOTANICA"."INSERTAR_USUARIO"
BEFORE INSERT ON "BOTANICA"."USUARIO"
FOR EACH ROW
BEGIN
    SELECT usuario_seq.nextval INTO :NEW.USUARIOID FROM dual;
END;

CREATE SEQUENCE interesbotanico_seq
START WITH 1
INCREMENT BY 1
NOCACHE;


CREATE SEQUENCE insecto_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE SEQUENCE planta_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE SEQUENCE usuario_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE SEQUENCE guia_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE SEQUENCE resena_seq
START WITH 1
INCREMENT BY 1
NOCACHE;

CREATE OR REPLACE TRIGGER validar_dni
BEFORE INSERT ON USUARIO
FOR EACH ROW
DECLARE
    dni_valido BOOLEAN;
BEGIN
    dni_valido := REGEXP_LIKE(:NEW.dni, '^[a-zA-Z]{8}[a-zA-Z]$') AND 
                  SUBSTR('TRWAGMYFPDXBNJZSQVHLCKET', 
                         MOD(SUM(DECODE(SUBSTR(:NEW.dni, 1, 8), '0', 36, 
                                        DECODE(UPPER(SUBSTR(:NEW.dni, 1, 8)), 'X', 10, 
                                               SUBSTR(UPPER(SUBSTR(:NEW.dni, 1, 8)), 1, 1)) + 
                                        DECODE(UPPER(SUBSTR(:NEW.dni, 1, 8)), 'X', 10, 
                                               SUBSTR(UPPER(SUBSTR(:NEW.dni, 1, 8)), 2, 1)))), 23) + 1) 
                    = SUBSTR(:NEW.dni, 9, 1);
    
    IF NOT dni_valido THEN
        RAISE_APPLICATION_ERROR(-21141, 'El formato del DNI no es válido.');
    END IF;
END;
/




INSERT INTO BOTANICA.PLANTA (PLANTAID, NOMBRECIENTIFICOPLANTA, NOMBRECOMUNPLANTA, TIPOPLANTA, CUIDADOSESPECIFICOS, IMAGEN) 
VALUES (1, 'Eucalyptus globulus', 'Eucalipto', 'Arbol', 'Requiere suelos bien drenados y pleno sol.', 'https://drive.google.com/uc?export=view&id=1AzBX4E4fBCnp4gcmXZim-MX5gVyv8-nh');
INSERT INTO BOTANICA.PLANTA (PLANTAID, NOMBRECIENTIFICOPLANTA, NOMBRECOMUNPLANTA, TIPOPLANTA, IMAGEN) 
VALUES (2, 'Aloe vera', 'Sábila', 'Plantas suculentas', 'https://drive.usercontent.google.com/download?id=1IIgYKIyKhLR50D3enJ3Fcs3_nZ9vIonZ&export=view&authuser=0');
INSERT INTO BOTANICA.PLANTA (PLANTAID, NOMBRECIENTIFICOPLANTA, NOMBRECOMUNPLANTA, TIPOPLANTA, CUIDADOSESPECIFICOS, IMAGEN) 
VALUES (3, 'Ficus lyrata', 'Ficus lira', 'Higuerones', 'Requiere humedad y luz indirecta.', 'https://drive.google.com/uc?export=view&id=1MlDb1H-V2DyI32ncplsbtyYaI_KFGD8S');
INSERT INTO BOTANICA.PLANTA (PLANTAID, NOMBRECIENTIFICOPLANTA, NOMBRECOMUNPLANTA, TIPOPLANTA, CUIDADOSESPECIFICOS, IMAGEN) 
VALUES (4, 'Tulipa gesneriana', 'Tulipán', 'Flor', 'Requiere pleno sol y riego moderado.', 'https://4');
INSERT INTO BOTANICA.PLANTA (PLANTAID, NOMBRECIENTIFICOPLANTA, NOMBRECOMUNPLANTA, TIPOPLANTA, CUIDADOSESPECIFICOS, IMAGEN) 
VALUES (5, 'Rosmarinus officinalis', 'Romero', 'Hierba', 'Requiere pleno sol y suelo bien drenado.', '');


INSERT INTO INTERESBOTANICO (INTERESID, NOMBREINTERES) VALUES (1, "Por defecto");



INSERT INTO USUARIO (USUARIOID, NOMBREUSUARIO, CONTRASEÑA, NOMBRE, APELLIDO1, APELLIDO2, DNI, CORREO, INTERESBOTANICOINTERESID)
VALUES (1, 'usuario_prueba', 'contraseña123', 'Nombre', 'Apellido1', 'Apellido2', '12345678Z', 'usuario_prueba@gmail.com', 1);

INSERT INTO USUARIO_PLANTA VALUES(1,1);
INSERT INTO USUARIO_PLANTA VALUES(1,2);
INSERT INTO USUARIO_PLANTA VALUES(1,3);
INSERT INTO USUARIO_PLANTA VALUES(1,4);
INSERT INTO USUARIO_PLANTA VALUES(1,5);

