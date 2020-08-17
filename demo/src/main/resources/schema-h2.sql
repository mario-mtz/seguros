DROP TABLE PRODUCT;

CREATE TABLE USUARIO (
ID NUMBER(10,0) NOT NULL AUTO_INCREMENT,
USERNAME VARCHAR2(100) DEFAULT NULL,
PASSWORD VARCHAR2(100) DEFAULT NULL,
NOMBRE VARCHAR2(250) DEFAULT NULL,
DIRECCION VARCHAR2(250) DEFAULT NULL,
EMAIL VARCHAR2(250) DEFAULT NULL,
PRIMARY KEY (ID));

DROP SEQUENCE USUARIO_ID_SEQ;

CREATE SEQUENCE USUARIO_ID_SEQ
  MINVALUE 1
  MAXVALUE 9999999999999999
  START WITH 1
  INCREMENT BY 1
  CACHE 1;