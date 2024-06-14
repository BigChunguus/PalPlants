--------------------------------------------------------
-- Archivo creado  - viernes-junio-07-2024   
--------------------------------------------------------
DROP TABLE "BOTANICA"."GUIA" cascade constraints;
DROP TABLE "BOTANICA"."INSECTO" cascade constraints;
DROP TABLE "BOTANICA"."INTERESBOTANICO" cascade constraints;
DROP TABLE "BOTANICA"."PLANTA" cascade constraints;
DROP TABLE "BOTANICA"."PLANTA_INSECTO" cascade constraints;
DROP TABLE "BOTANICA"."RESEÑA" cascade constraints;
DROP TABLE "BOTANICA"."USUARIO" cascade constraints;
DROP TABLE "BOTANICA"."USUARIO_PLANTA" cascade constraints;
DROP SYNONYM "PUBLIC"."DUAL";
DROP SEQUENCE "BOTANICA"."GUIA_SEQ";
DROP SEQUENCE "BOTANICA"."INSECTO_SEQ";
DROP SEQUENCE "BOTANICA"."INTERESBOTANICO_SEQ";
DROP SEQUENCE "BOTANICA"."PLANTA_SEQ";
DROP SEQUENCE "BOTANICA"."RESENA_SEQ";
DROP SEQUENCE "BOTANICA"."USUARIO_SEQ";
--------------------------------------------------------
--  DDL for Sequence GUIA_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BOTANICA"."GUIA_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 16 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence INSECTO_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BOTANICA"."INSECTO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 33 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence INTERESBOTANICO_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BOTANICA"."INTERESBOTANICO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 8 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence PLANTA_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BOTANICA"."PLANTA_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 31 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence RESENA_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BOTANICA"."RESENA_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence USUARIO_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "BOTANICA"."USUARIO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2 NOCACHE  NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table GUIA
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."GUIA" 
   (	"GUIAID" NUMBER(11,0), 
	"TITULO" VARCHAR2(50 BYTE), 
	"CONTENIDO" VARCHAR2(1500 BYTE), 
	"PLANTAPLANTAID" NUMBER(11,0), 
	"CALIFICACIONMEDIA" NUMBER(5,1), 
	"USUARIOUSUARIOID" NUMBER(11,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table INSECTO
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."INSECTO" 
   (	"INSECTOID" NUMBER(11,0), 
	"NOMBRECIENTIFICOINSECTO" VARCHAR2(50 BYTE), 
	"NOMBRECOMUNINSECTO" VARCHAR2(30 BYTE), 
	"DESCRIPCION" VARCHAR2(1000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table INTERESBOTANICO
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."INTERESBOTANICO" 
   (	"INTERESID" NUMBER(5,0), 
	"NOMBREINTERES" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table PLANTA
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."PLANTA" 
   (	"PLANTAID" NUMBER(11,0), 
	"NOMBRECIENTIFICOPLANTA" VARCHAR2(50 BYTE), 
	"NOMBRECOMUNPLANTA" VARCHAR2(30 BYTE), 
	"TIPOPLANTA" VARCHAR2(30 BYTE), 
	"CUIDADOSESPECIFICOS" VARCHAR2(300 BYTE), 
	"IMAGEN" VARCHAR2(150 BYTE), 
	"DESCRIPCION" VARCHAR2(150 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table PLANTA_INSECTO
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."PLANTA_INSECTO" 
   (	"PLANTAPLANTAID" NUMBER(11,0), 
	"INSECTOINSECTOID" NUMBER(11,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table RESEÑA
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."RESEÑA" 
   (	"RESEÑAID" NUMBER(11,0), 
	"CALIFICACION" NUMBER(5,2), 
	"COMENTARIO" VARCHAR2(200 BYTE), 
	"FECHARESEÑA" DATE, 
	"USUARIOUSUARIOID" NUMBER(11,0), 
	"GUIAGUIAID" NUMBER(11,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table USUARIO
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."USUARIO" 
   (	"USUARIOID" NUMBER(11,0), 
	"NOMBREUSUARIO" VARCHAR2(15 BYTE), 
	"CONTRASEÑA" VARCHAR2(50 BYTE), 
	"NOMBRE" VARCHAR2(30 BYTE), 
	"APELLIDO1" VARCHAR2(30 BYTE), 
	"APELLIDO2" VARCHAR2(30 BYTE), 
	"DNI" VARCHAR2(9 BYTE), 
	"CORREO" VARCHAR2(50 BYTE), 
	"INTERESBOTANICOINTERESID" NUMBER(5,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table USUARIO_PLANTA
--------------------------------------------------------

  CREATE TABLE "BOTANICA"."USUARIO_PLANTA" 
   (	"USUARIOUSUARIOID" NUMBER(11,0), 
	"PLANTAPLANTAID" NUMBER(11,0)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into BOTANICA.GUIA
SET DEFINE OFF;
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('2','Guía para el cuidado de la Rosa de Gallica','La Rosa de Gallica es una flor que necesita riego regular y suelos bien drenados. Prefiere la luz solar directa para un mejor crecimiento y floración.','2',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('3','Guía para el cuidado de la Lavanda','La Lavanda es un arbusto que prefiere suelos secos y soleados. Requiere poda regular para mantenerse compacta. Asegúrate de proporcionarle suficiente luz solar y agua moderada.','3',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('4','Guía para el cuidado del Ficus','El Ficus es una planta de interior que requiere riego moderado y luz indirecta. Asegúrate de proporcionarle la cantidad adecuada de agua y podarla para mantener su forma.','4',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('1','Guía para el cuidado del Eucalipto','El Eucalipto es un árbol que requiere suelos bien drenados y riego moderado. Prefiere climas templados. Asegúrate de proporcionarle la cantidad adecuada de agua y luz solar.','1',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('5','Guía para el cuidado del Espatifilo','El Espatifilo es una planta suculenta que requiere riego regular y suelos bien drenados. Prefiere ambientes húmedos. Asegúrate de mantener el sustrato húmedo pero no empapado.','5',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('6','Guía para el cuidado de la Monstera deliciosa','La Monstera deliciosa es una planta tropical que requiere riego regular y suelos bien drenados. Prefiere ambientes cálidos y con luz indirecta. Asegúrate de proporcionarle suficiente humedad y podarla según sea necesario.','6',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('7','Guía para el cuidado de la Cola de Burro','La Cola de Burro es una planta suculenta colgante que requiere riego esporádico y suelos bien drenados. Prefiere ambientes soleados y secos. Asegúrate de dejar que el sustrato se seque entre riegos.','7',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('8','Guía para el cuidado del Filodendro','El Filodendro es una planta trepadora de interior que requiere riego moderado y suelos bien drenados. Prefiere ambientes cálidos y con luz indirecta. Asegúrate de proporcionarle un soporte para trepar y podarla según sea necesario.','8',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('9','Guía para el cuidado de la Begonia del rey','La Begonia del rey es una planta suculenta apreciada por sus hojas decorativas y vistosas. Requiere riego moderado y suelos bien drenados. Prefiere ambientes cálidos y húmedos. ','10',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('10','Guía para el cuidado del Aloe vera','El Aloe vera es una planta suculenta conocida por sus propiedades medicinales. Requiere riego esporádico y suelos bien drenados. Prefiere ambientes soleados y secos. Asegúrate de dejar que el sustrato se seque completamente entre riegos.','10',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('11','Guía para el cuidado del Ficus Lira','El Ficus Lira es una planta de interior que requiere riego moderado y luz indirecta. Asegúrate de proporcionarle suficiente humedad y podarla para mantener su forma.','11',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('12','Guía para el cuidado de la Cinta o Lazo de amor','La Cinta o Lazo de amor es una planta popular de interior con hojas arqueadas y rayadas. Requiere riego moderado y suelos bien drenados. Tolera la luz indirecta. Asegúrate de proporcionarle la cantidad adecuada de agua y podarla según sea necesario.','12',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('13','Guía para el cuidado de la Calatea','La Calatea es una planta de interior conocida por sus hojas grandes y ovaladas. Requiere riego moderado y suelos bien drenados. Prefiere ambientes cálidos y húmedos. Asegúrate de proporcionarle suficiente humedad y evitar la luz directa del sol.','13',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('14','Guía para el cuidado del Collar de Perlas','El Collar de Perlas es una planta suculenta colgante con hojas redondas que se asemejan a perlas. Requiere riego esporádico y suelos bien drenados. Tolera la luz indirecta. Asegúrate de proporcionarle suficiente luz y dejar que el sustrato se seque entre riegos.','14',null,'1');
Insert into BOTANICA.GUIA (GUIAID,TITULO,CONTENIDO,PLANTAPLANTAID,CALIFICACIONMEDIA,USUARIOUSUARIOID) values ('15','Guía para el cuidado de la Diefenbachia','La Diefenbachia es una planta de interior conocida por sus hojas grandes y moteadas. Requiere riego moderado y suelos bien drenados. Tolera la luz indirecta. Asegúrate de mantener el sustrato húmedo pero no saturado y evitar el exceso de luz solar directa.','15',null,'1');
REM INSERTING into BOTANICA.INSECTO
SET DEFINE OFF;
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('1','Apis mellifera','Abeja europea','Insecto polinizador clave, conocido por su producción de miel.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('2','Danaus plexippus','Mariposa monarca','Conocida por su migración masiva y sus alas naranjas y negras.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('3','Coccinella septempunctata','Mariquita','Pequeño escarabajo rojo con manchas negras, beneficioso para el control de plagas.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('4','Gryllus campestris','Grillo campestre','Insecto conocido por su canto nocturno, producido al frotar sus alas.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('5','Tenebrio molitor','Gusano de la harina','Larva de un escarabajo oscuro, comúnmente utilizada como alimento para mascotas.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('6','Bombyx mori','Gusano de seda','Oruga que produce seda utilizada en la industria textil.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('7','Anopheles gambiae','Mosquito','Vector principal de la malaria en humanos.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('8','Drosophila melanogaster','Mosca de la fruta','Insecto comúnmente utilizado en estudios genéticos.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('9','Formica rufa','Hormiga roja','Conocida por su estructura social compleja y comportamiento trabajador.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('10','Aedes aegypti','Mosquito de la fiebre amarilla','Transmisor de enfermedades como el dengue, zika y fiebre amarilla.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('11','Periplaneta americana','Cucaracha americana','Insecto de gran tamaño, común en áreas urbanas y conocido por su resistencia.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('12','Acheta domesticus','Grillo doméstico','Insecto conocido por su canto y utilizado como alimento para mascotas.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('13','Manduca sexta','Gusano del tabaco','Larva de una polilla grande, conocida por alimentarse de plantas de tabaco.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('14','Vespa crabro','Avispón europeo','Insecto grande y agresivo, conocido por su dolorosa picadura.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('15','Locusta migratoria','Langosta migratoria','Insecto conocido por formar enjambres que pueden devastar cultivos.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('16','Papilio machaon','Macaón','Mariposa diurna conocida por su tamaño y colores vibrantes.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('17','Harmonia axyridis','Mariquita asiática','Insecto coleóptero utilizado en control biológico.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('20','Bombus terrestris','Abejorro común','Insecto polinizador importante en ecosistemas y agricultura.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('21','Pieris brassicae','Mariposa de la col','Mariposa blanca que se alimenta de plantas crucíferas.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('22','Vanessa atalanta','Almirante rojo','Mariposa conocida por su coloración roja y negra.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('24','Musca domestica','Mosca doméstica','Insecto comúnmente encontrado en ambientes urbanos.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('27','Atta cephalotes','Hormiga cortadora de hojas','Insecto que corta hojas para cultivar hongos en sus nidos.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('28','Blattella germanica','Cucaracha alemana','Insecto nocturno que se encuentra comúnmente en áreas urbanas.');
Insert into BOTANICA.INSECTO (INSECTOID,NOMBRECIENTIFICOINSECTO,NOMBRECOMUNINSECTO,DESCRIPCION) values ('32','Tettigonia viridissima','Saltamontes verde','Insecto grande y verde conocido por sus saltos y su capacidad de camuflaje.');
REM INSERTING into BOTANICA.INTERESBOTANICO
SET DEFINE OFF;
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('1','Por Defecto');
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('2','Tropical');
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('3','Desértico');
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('4','Bosque Templado');
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('5','Pradera');
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('6','Selva Lluviosa');
Insert into BOTANICA.INTERESBOTANICO (INTERESID,NOMBREINTERES) values ('7','Montaña');
REM INSERTING into BOTANICA.PLANTA
SET DEFINE OFF;
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('1','Eucalyptus globulus','Eucalipto','arbol','Requiere suelos bien drenados y riego moderado. Prefiere climas templados.','https://docs.google.com/uc?id=1sJNmFcsJCRCXM7DeZptjNrqGd_dkYO0e','El eucalipto es un árbol de hoja perenne originario de Australia...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('2','Rosa gallica','Rosa de Gallica','flor','Necesita riego regular y suelos bien drenados. Prefiere la luz solar directa.','https://docs.google.com/uc?id=1lFQeTmv6Z2cLMRTE_73WrqSaga8GJav4','La rosa de Gallica es una especie de rosa originaria de Europa...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('3','Lavandula angustifolia','Lavanda','arbusto','Prefiere suelos secos y soleados. Requiere poda regular para mantenerse compacta.','https://docs.google.com/uc?id=1Doht4sXHHS_qzd-lgeg4_Uv7NISCQQDs','La lavanda es una planta aromática conocida por sus flores de color morado...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('4','Ficus elastica','Ficus','arbol','Necesita riego moderado y luz indirecta. Requiere poda para mantener su forma.','https://docs.google.com/uc?id=1IdaK1Lseb-fLMcfOvvNIjJBpemkgAi9e','El ficus es una planta de interior muy popular debido a su follaje denso y brillante...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('5','Spathiphyllum wallisii','Espatifilo','plantas suculentas','Requiere riego regular y suelos bien drenados. Prefiere ambientes húmedos.','https://docs.google.com/uc?id=1fgShC_eTq7lBOjhyV7BEm2uANY-QlDP6','El espatifilo es una planta de interior apreciada por sus hojas verdes brillantes y flores blancas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('6','Monstera deliciosa','Costilla de Adán','plantas trepadoras','Necesita riego regular y suelos bien drenados. Prefiere ambientes cálidos y con luz indirecta.','https://docs.google.com/uc?id=1dIjyabRCy-dnZWpq5CbAC8-pfHxrjkxG','La Monstera deliciosa es una planta tropical conocida por sus hojas grandes y perforadas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('7','Sedum morganianum','Cola de Burro','plantas suculentas','Requiere riego esporádico y suelos bien drenados. Prefiere ambientes soleados y secos.','https://docs.google.com/uc?id=1iULGQmM8hEZUuvuBMdeM01G9vC7Th7KN','La Sedum morganianum es una planta suculenta colgante con hojas carnosas y redondeadas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('8','Philodendron hederaceum','Filodendro','plantas trepadoras','Requiere riego moderado y suelos bien drenados. Prefiere ambientes cálidos y con luz indirecta.','https://docs.google.com/uc?id=11985V-crLdeP-vbFZUEVp4EwNlb4J_n0','El Philodendron hederaceum es una planta trepadora de interior con hojas verdes brillantes...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('9','Begonia rex','Begonia del rey','plantas suculentas','Necesita riego moderado y suelos bien drenados. Prefiere ambientes cálidos y húmedos.','https://docs.google.com/uc?id=1ts6LGJ6p0sorZJ4PNz84GoWCUIF_-yag','La Begonia rex es una planta suculenta apreciada por sus hojas decorativas y vistosas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('10','Aloe vera','Sábila','plantas suculentas','Requiere riego esporádico y suelos bien drenados. Prefiere ambientes soleados y secos.','https://docs.google.com/uc?id=1tQiLba9Qu3Y2Fodgki2FPZh7DbdrDDtJ','El Aloe vera es una planta suculenta conocida por sus propiedades medicinales...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('11','Ficus lyrata','Ficus Lira','arbol','Necesita riego moderado y luz indirecta. Requiere poda para mantener su forma.','https://docs.google.com/uc?id=1hF56fVm0zXs_sI61tes-eJqaRfcAjwYb','El Ficus lyrata es una planta de interior conocida por sus grandes hojas violinistas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('12','Chlorophytum comosum','Cinta o Lazo de amor','plantas trepadoras','Requiere riego moderado y suelos bien drenados. Tolera la luz indirecta.','https://docs.google.com/uc?id=1epXcp1hSGo2NMsVHp3UMUYETtPFW5tVL','El Chlorophytum comosum es una planta popular de interior con hojas arqueadas y rayadas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('13','Calathea orbifolia','Calatea','plantas suculentas','Requiere riego moderado y suelos bien drenados. Prefiere ambientes cálidos y húmedos.','https://docs.google.com/uc?id=1s8bbNvQ91Q4ND5rPuLOnzzzGi8myplbw','La Calathea orbifolia es una planta de interior conocida por sus hojas grandes y ovaladas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('14','Senecio rowleyanus','Collar de Perlas','plantas suculentas','Requiere riego esporádico y suelos bien drenados. Tolera la luz indirecta.','https://docs.google.com/uc?id=13q28abo6iS98sKKeQMw2MUFhaHSXh7Rd','El Senecio rowleyanus es una planta suculenta colgante con hojas redondas que se asemejan a perlas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('15','Dieffenbachia seguine','Diefenbachia','plantas suculentas','Necesita riego moderado y suelos bien drenados. Tolera la luz indirecta.','https://docs.google.com/uc?id=1DE2flNxojgrw9Cyj7b1zP5yEjGiBAw9-','La Dieffenbachia seguine es una planta de interior conocida por sus hojas grandes y moteadas...');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('16','Mentha spicata','Hierbabuena','hierba','Requiere suelo húmedo y bien drenado, con exposición al sol parcial.','https://docs.google.com/uc?id=1GZsZ3ev5vOH-pWfPZGuDt4KFgqtcFh8S','Planta aromática utilizada en cocina y medicina.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('17','Ocimum basilicum','Albahaca','hierba','Necesita sol pleno y riego regular, no tolera las heladas.','https://docs.google.com/uc?id=1PL4q_03wyx8aVi3SREZwNDfBkKZsY6M_','Planta aromática popular en la cocina mediterránea.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('18','Rosmarinus officinalis','Romero','arbusto','Prefiere sol pleno y suelo bien drenado, riego moderado.','https://docs.google.com/uc?id=1doAB7aCjp-bVoajvDNs76Vzih8g_5Rrc','Arbusto aromático utilizado en cocina y como planta ornamental.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('19','Thymus vulgaris','Tomillo','arbusto','Requiere sol pleno y suelo bien drenado, riego escaso.','https://docs.google.com/uc?id=1rbpWRB1ihwg4s6TwzQhM9jXSUFLH4PpP','Planta aromática utilizada en cocina y medicina.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('20','Salvia officinalis','Salvia','arbusto','Prefiere sol pleno y suelo bien drenado, riego moderado.','https://docs.google.com/uc?id=17ZfhIu8i8R6-DEpaMI-69lyebnwhRgR8','Planta aromática y medicinal, utilizada también como ornamental.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('21','Chamaemelum nobile','Manzanilla','hierba','Requiere suelo bien drenado y pleno sol, riego moderado.','https://docs.google.com/uc?id=13XRB5U7j9F6A-Fe4l8kuTiFaXmAn-vPK','Planta medicinal conocida por sus propiedades calmantes y digestivas.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('22','Calendula officinalis','Caléndula','hierba','Prefiere sol pleno y suelo bien drenado, riego moderado.','https://docs.google.com/uc?id=1PfNoNaUj6cASOwijgWW5DUo5S9k5DMSO
','Planta ornamental y medicinal, utilizada en cremas y ungüentos.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('23','Lavandula stoechas','Lavanda Francesa','arbusto','Necesita sol pleno y suelo bien drenado, riego moderado.','https://docs.google.com/uc?id=11ort5BmR0RP7DB9B2e_djrSTPOY_2h00
','Arbusto aromático utilizado en perfumería y como planta ornamental.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('24','Pelargonium graveolens','Geranio','arbusto','Prefiere sol parcial y riego regular, suelo bien drenado.','https://docs.google.com/uc?id=1nELAG_AjE2TRH0Ds3ecWZ81--yGXn02v','Planta ornamental con flores vistosas y fragancia agradable.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('25','Artemisia absinthium','Ajenjo','arbusto','Requiere sol pleno y suelo bien drenado, riego moderado.','https://docs.google.com/uc?id=1H2yxmdvcSI9uLsmPYRY81-HRJ2O4hMyu','Planta medicinal conocida por su uso en la elaboración de absenta.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('26','Hedera helix','Hiedra','plantas trepadoras','Prefiere sombra parcial y suelo húmedo, riego moderado.','https://docs.google.com/uc?id=1vWxnbdwMl4gFHBrUZidUTk_YuZ2KV67Y','Planta trepadora utilizada para cobertura de muros y jardines.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('27','Lavandula dentata','Lavanda Rizada','arbusto','Necesita sol pleno y suelo bien drenado, riego moderado.','https://docs.google.com/uc?id=1KWwopJbTDBSa4cY1fTiULHcAOCbFaOpG
','Arbusto aromático con flores de color púrpura, utilizada en jardines.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('28','Plectranthus scutellarioides','Coleus','hierba','Prefiere sombra parcial y riego regular, suelo bien drenado.','https://docs.google.com/uc?id=1sjGOIUG1PBOzj5w6Q-W8LLPDUJ7g6nOV
','Planta ornamental con hojas coloridas y vistosas.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('29','Nerium oleander','Adelfa','arbusto','Requiere sol pleno y riego moderado, suelo bien drenado.','https://docs.google.com/uc?id=1WxUfXxKy6xdTWN7n6IvUmmAktb6XqZA2','Arbusto ornamental con flores vistosas, tóxico si se ingiere.');
Insert into BOTANICA.PLANTA (PLANTAID,NOMBRECIENTIFICOPLANTA,NOMBRECOMUNPLANTA,TIPOPLANTA,CUIDADOSESPECIFICOS,IMAGEN,DESCRIPCION) values ('30','Sansevieria trifasciata','Lengua de Suegra','plantas suculentas','Prefiere sol indirecto y riego moderado, suelo bien drenado.','https://docs.google.com/uc?id=1_lQ5yrn04qQel0oj8-12QoC9ZnnWpkc6','Planta suculenta resistente y de fácil cuidado, utilizada en interiores.');
REM INSERTING into BOTANICA.PLANTA_INSECTO
SET DEFINE OFF;
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('1','1');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('1','3');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('1','6');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('1','7');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('2','1');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('2','2');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('2','8');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('3','3');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('3','5');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('3','9');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('3','10');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('4','4');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('4','7');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('4','10');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('4','12');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('5','5');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('5','10');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('5','11');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('5','14');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('6','1');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('6','6');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('6','12');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('7','3');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('7','7');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('7','8');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('7','13');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('8','2');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('8','5');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('8','8');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('8','14');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('9','6');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('9','7');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('9','9');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('9','15');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('10','4');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('10','9');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('10','10');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('10','16');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('11','9');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('11','11');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('11','17');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('12','11');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('12','12');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('12','13');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('12','20');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('13','13');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('13','15');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('13','21');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('14','2');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('14','14');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('14','22');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('15','4');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('15','15');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('15','24');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('16','16');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('16','27');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('17','17');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('17','28');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('18','20');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('18','32');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('19','21');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('20','22');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('21','24');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('22','27');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('23','28');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('24','32');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('25','1');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('26','2');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('27','3');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('28','4');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('29','5');
Insert into BOTANICA.PLANTA_INSECTO (PLANTAPLANTAID,INSECTOINSECTOID) values ('30','6');
REM INSERTING into BOTANICA."RESEÑA"
SET DEFINE OFF;
REM INSERTING into BOTANICA.USUARIO
SET DEFINE OFF;
Insert into BOTANICA.USUARIO (USUARIOID,NOMBREUSUARIO,"CONTRASEÑA",NOMBRE,APELLIDO1,APELLIDO2,DNI,CORREO,INTERESBOTANICOINTERESID) values ('1','Administrador','FiP1gmPsJILGSaHtFFv7dw==','admin','admin','admin','21141790Y','alejandro.palplants@gmail.com','1');
REM INSERTING into BOTANICA.USUARIO_PLANTA
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index SYS_C007406
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007406" ON "BOTANICA"."INTERESBOTANICO" ("INTERESID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007407
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007407" ON "BOTANICA"."INTERESBOTANICO" ("NOMBREINTERES") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007410
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007410" ON "BOTANICA"."INSECTO" ("INSECTOID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007411
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007411" ON "BOTANICA"."INSECTO" ("NOMBRECIENTIFICOINSECTO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007412
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007412" ON "BOTANICA"."INSECTO" ("NOMBRECOMUNINSECTO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007418
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007418" ON "BOTANICA"."PLANTA" ("PLANTAID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007419
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007419" ON "BOTANICA"."PLANTA" ("NOMBRECIENTIFICOPLANTA") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007420
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007420" ON "BOTANICA"."PLANTA" ("NOMBRECOMUNPLANTA") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007421
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007421" ON "BOTANICA"."PLANTA" ("IMAGEN") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007427
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007427" ON "BOTANICA"."USUARIO" ("USUARIOID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007428
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007428" ON "BOTANICA"."USUARIO" ("NOMBREUSUARIO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007429
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007429" ON "BOTANICA"."USUARIO" ("DNI") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007430
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007430" ON "BOTANICA"."USUARIO" ("CORREO") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007435
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007435" ON "BOTANICA"."GUIA" ("GUIAID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007442
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007442" ON "BOTANICA"."RESEÑA" ("RESEÑAID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007447
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007447" ON "BOTANICA"."USUARIO_PLANTA" ("USUARIOUSUARIOID", "PLANTAPLANTAID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SYS_C007452
--------------------------------------------------------

  CREATE UNIQUE INDEX "BOTANICA"."SYS_C007452" ON "BOTANICA"."PLANTA_INSECTO" ("PLANTAPLANTAID", "INSECTOINSECTOID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  Constraints for Table GUIA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."GUIA" MODIFY ("TITULO" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."GUIA" MODIFY ("CONTENIDO" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."GUIA" MODIFY ("PLANTAPLANTAID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."GUIA" ADD PRIMARY KEY ("GUIAID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."GUIA" ADD CONSTRAINT "CHK_CALIFICACIONMEDIA" CHECK ("CALIFICACIONMEDIA" BETWEEN 1 AND 5) ENABLE;
--------------------------------------------------------
--  Constraints for Table INSECTO
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."INSECTO" MODIFY ("NOMBRECIENTIFICOINSECTO" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."INSECTO" MODIFY ("NOMBRECOMUNINSECTO" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."INSECTO" ADD PRIMARY KEY ("INSECTOID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."INSECTO" ADD UNIQUE ("NOMBRECIENTIFICOINSECTO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."INSECTO" ADD UNIQUE ("NOMBRECOMUNINSECTO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table INTERESBOTANICO
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."INTERESBOTANICO" MODIFY ("NOMBREINTERES" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."INTERESBOTANICO" ADD PRIMARY KEY ("INTERESID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."INTERESBOTANICO" ADD UNIQUE ("NOMBREINTERES")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table PLANTA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."PLANTA" MODIFY ("NOMBRECIENTIFICOPLANTA" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."PLANTA" MODIFY ("NOMBRECOMUNPLANTA" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."PLANTA" MODIFY ("TIPOPLANTA" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."PLANTA" MODIFY ("IMAGEN" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."PLANTA" ADD CONSTRAINT "TIPOPLANTAVALID" CHECK (
    LOWER(TIPOPLANTA) IN (
        'arbol', 'arbusto', 'hierba', 'plantas suculentas', 
        'plantas trepadoras', 'higuerones', 'helechos', 
        'musgos', 'hepaticas', 'plantas insectivoras', 'flor'
    )
) ENABLE;
  ALTER TABLE "BOTANICA"."PLANTA" ADD PRIMARY KEY ("PLANTAID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."PLANTA" ADD UNIQUE ("NOMBRECIENTIFICOPLANTA")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."PLANTA" ADD UNIQUE ("NOMBRECOMUNPLANTA")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."PLANTA" ADD UNIQUE ("IMAGEN")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table PLANTA_INSECTO
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."PLANTA_INSECTO" MODIFY ("PLANTAPLANTAID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."PLANTA_INSECTO" MODIFY ("INSECTOINSECTOID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."PLANTA_INSECTO" ADD PRIMARY KEY ("PLANTAPLANTAID", "INSECTOINSECTOID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table RESEÑA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."RESEÑA" MODIFY ("CALIFICACION" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."RESEÑA" MODIFY ("FECHARESEÑA" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."RESEÑA" MODIFY ("USUARIOUSUARIOID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."RESEÑA" MODIFY ("GUIAGUIAID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."RESEÑA" ADD CONSTRAINT "CALIFICACIONVALIDA" CHECK (calificacion >= 1 AND calificacion <= 5) ENABLE;
  ALTER TABLE "BOTANICA"."RESEÑA" ADD PRIMARY KEY ("RESEÑAID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table USUARIO
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."USUARIO" MODIFY ("NOMBREUSUARIO" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."USUARIO" MODIFY ("CORREO" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."USUARIO" MODIFY ("INTERESBOTANICOINTERESID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."USUARIO" ADD CONSTRAINT "EMAILVALIDO" CHECK (REGEXP_LIKE(correo, '^[^@]+@[^@]+\.(com|es)$') ) ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO" ADD CONSTRAINT "USUARIONOCARESPECIALES" CHECK (REGEXP_LIKE(nombreUsuario, '^[a-zA-Z0-9]+$')) ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO" ADD PRIMARY KEY ("USUARIOID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO" ADD UNIQUE ("NOMBREUSUARIO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO" ADD UNIQUE ("DNI")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO" ADD UNIQUE ("CORREO")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO" MODIFY ("CONTRASEÑA" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USUARIO_PLANTA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."USUARIO_PLANTA" MODIFY ("USUARIOUSUARIOID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."USUARIO_PLANTA" MODIFY ("PLANTAPLANTAID" NOT NULL ENABLE);
  ALTER TABLE "BOTANICA"."USUARIO_PLANTA" ADD PRIMARY KEY ("USUARIOUSUARIOID", "PLANTAPLANTAID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GUIA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."GUIA" ADD CONSTRAINT "FK_PLANTA" FOREIGN KEY ("PLANTAPLANTAID")
	  REFERENCES "BOTANICA"."PLANTA" ("PLANTAID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "BOTANICA"."GUIA" ADD CONSTRAINT "FK_USUARIO_GUIA" FOREIGN KEY ("USUARIOUSUARIOID")
	  REFERENCES "BOTANICA"."USUARIO" ("USUARIOID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PLANTA_INSECTO
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."PLANTA_INSECTO" ADD CONSTRAINT "FK_PLANTA_INSECTO_INSECTO" FOREIGN KEY ("INSECTOINSECTOID")
	  REFERENCES "BOTANICA"."INSECTO" ("INSECTOID") ENABLE;
  ALTER TABLE "BOTANICA"."PLANTA_INSECTO" ADD CONSTRAINT "FK_PLANTA_INSECTO_PLANTA" FOREIGN KEY ("PLANTAPLANTAID")
	  REFERENCES "BOTANICA"."PLANTA" ("PLANTAID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table RESEÑA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."RESEÑA" ADD CONSTRAINT "FK_GUIA" FOREIGN KEY ("GUIAGUIAID")
	  REFERENCES "BOTANICA"."GUIA" ("GUIAID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "BOTANICA"."RESEÑA" ADD CONSTRAINT "FK_USUARIO" FOREIGN KEY ("USUARIOUSUARIOID")
	  REFERENCES "BOTANICA"."USUARIO" ("USUARIOID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USUARIO
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."USUARIO" ADD CONSTRAINT "FK_INTERES_BOTANICO" FOREIGN KEY ("INTERESBOTANICOINTERESID")
	  REFERENCES "BOTANICA"."INTERESBOTANICO" ("INTERESID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table USUARIO_PLANTA
--------------------------------------------------------

  ALTER TABLE "BOTANICA"."USUARIO_PLANTA" ADD CONSTRAINT "FK_USUARIO_PLANTA_PLANTA" FOREIGN KEY ("PLANTAPLANTAID")
	  REFERENCES "BOTANICA"."PLANTA" ("PLANTAID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "BOTANICA"."USUARIO_PLANTA" ADD CONSTRAINT "FK_USUARIO_PLANTA_USUARIO" FOREIGN KEY ("USUARIOUSUARIOID")
	  REFERENCES "BOTANICA"."USUARIO" ("USUARIOID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  DDL for Trigger CALCULAR_MEDIA_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."CALCULAR_MEDIA_TRG" 
AFTER INSERT OR UPDATE ON "BOTANICA"."RESEÑA"
DECLARE
    media NUMBER;
BEGIN
    MERGE INTO GUIA g
    USING (
        SELECT guiaguiaid, AVG(calificacion) AS avg_calificacion
        FROM RESEÑA
        GROUP BY guiaguiaid
    ) r ON (g.GUIAID = r.guiaguiaid)
    WHEN MATCHED THEN
        UPDATE SET g.CALIFICACIONMEDIA = r.avg_calificacion;
END calcular_media_trg;
/
ALTER TRIGGER "BOTANICA"."CALCULAR_MEDIA_TRG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERTAR_GUIA
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."INSERTAR_GUIA" 
BEFORE INSERT ON "BOTANICA"."GUIA"
FOR EACH ROW
BEGIN
    SELECT guia_seq.nextval INTO :NEW.GUIAID FROM dual;
END;
/
ALTER TRIGGER "BOTANICA"."INSERTAR_GUIA" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERTAR_INSECTO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."INSERTAR_INSECTO" 
BEFORE INSERT ON "BOTANICA"."INSECTO"
FOR EACH ROW
BEGIN
    SELECT insecto_seq.nextval INTO :NEW.INSECTOID FROM dual;
END;
/
ALTER TRIGGER "BOTANICA"."INSERTAR_INSECTO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERTAR_INTERES_BOTANICO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."INSERTAR_INTERES_BOTANICO" 
BEFORE INSERT ON "BOTANICA"."INTERESBOTANICO"
FOR EACH ROW
BEGIN
    SELECT interesbotanico_seq.nextval INTO :NEW.INTERESID FROM dual;
END;
/
ALTER TRIGGER "BOTANICA"."INSERTAR_INTERES_BOTANICO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERTAR_PLANTA
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."INSERTAR_PLANTA" 
BEFORE INSERT ON "BOTANICA"."PLANTA"
FOR EACH ROW
BEGIN
    SELECT planta_seq.nextval INTO :NEW.PLANTAID FROM dual;
END;
/
ALTER TRIGGER "BOTANICA"."INSERTAR_PLANTA" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERTAR_RESEÑA
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."INSERTAR_RESEÑA" 
BEFORE INSERT ON "BOTANICA"."RESEÑA"
FOR EACH ROW
BEGIN
    SELECT resena_seq.nextval INTO :NEW.RESEÑAID FROM dual;
END;
/
ALTER TRIGGER "BOTANICA"."INSERTAR_RESEÑA" ENABLE;
--------------------------------------------------------
--  DDL for Trigger INSERTAR_USUARIO
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."INSERTAR_USUARIO" 
BEFORE INSERT ON "BOTANICA"."USUARIO"
FOR EACH ROW
BEGIN
    SELECT usuario_seq.nextval INTO :NEW.USUARIOID FROM dual;
END;
/
ALTER TRIGGER "BOTANICA"."INSERTAR_USUARIO" ENABLE;
--------------------------------------------------------
--  DDL for Trigger VALIDAR_DNI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."VALIDAR_DNI" 
BEFORE INSERT ON "BOTANICA"."USUARIO"
FOR EACH ROW
DECLARE
    dni_valido BOOLEAN;
    letra_esperada VARCHAR2(1);
BEGIN
    letra_esperada := SUBSTR('TRWAGMYFPDXBNJZSQVHLCKET', MOD(TO_NUMBER(SUBSTR(:NEW.dni, 1, 8)), 23) + 1, 1);
    
    dni_valido := REGEXP_LIKE(:NEW.dni, '^\d{8}[a-zA-Z]$') AND 
                  SUBSTR(:NEW.dni, 9, 1) = letra_esperada;
    
    IF NOT dni_valido THEN
        RAISE_APPLICATION_ERROR(-20001, 'El formato del DNI no es válido.');
    END IF;
END;
/
ALTER TRIGGER "BOTANICA"."VALIDAR_DNI" ENABLE;

--------------------------------------------------------
--  DDL for Trigger ACTUALIZAR_FECHA_RESENA_TRG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "BOTANICA"."ACTUALIZAR_FECHA_RESENA_TRG"
BEFORE INSERT OR UPDATE ON "BOTANICA"."RESEÑA"
FOR EACH ROW
BEGIN
	:NEW.FECHARESEÑA := SYSDATE;
END;
/
ALTER TRIGGER "BOTANICA"."ACTUALIZAR_FECHA_RESENA_TRG" ENABLE;

--------------------------------------------------------
--  DDL for Synonymn DUAL
--------------------------------------------------------

  CREATE OR REPLACE PUBLIC SYNONYM "DUAL" FOR "SYS"."DUAL";