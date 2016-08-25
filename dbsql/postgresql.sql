CREATE DATABASE ssm_demo
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.UTF-8'
       LC_CTYPE = 'en_US.UTF-8'
       CONNECTION LIMIT = -1;
       
CREATE TABLE demo2
(
  username character varying NOT NULL,
  passwd character varying,
  CONSTRAINT demo2_pkey PRIMARY KEY (username)
)
WITH (
  OIDS=FALSE
);