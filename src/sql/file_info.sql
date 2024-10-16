CREATE TABLE role
(
    id              BIGSERIAL NOT NULL , 
    name       VARCHAR(255) NOT NULL 
  
);
-- ///////////////////////////////////////예약어 사용못함.
CREATE TABLE user
(
    id              BIGSERIAL NOT NULL , 
    username       VARCHAR(255) NOT NULL,
    password       VARCHAR(255) NOT NULL ,
    passwordConfirm       VARCHAR(20) NOT NULL 
    
  
);

--////////////////////////////////////////////
CREATE TABLE users
(
    id              BIGSERIAL NOT NULL , 
    username       VARCHAR(255) NOT NULL,
    password       VARCHAR(255) NOT NULL ,
    passwordConfirm       VARCHAR(20) NOT NULL 
    
  
);

-- public.file_info definition

-- Drop table

-- DROP TABLE public.file_info;

CREATE TABLE public.file_info (
    id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	file_size int8 NULL,
	update_dt timestamp(6) NULL,
	action_yn varchar(1) NULL,
	del_yn varchar(1) NULL,
	directory varchar(600) NULL,
	file_ext varchar(3) NULL,
	file_name varchar(400) NULL,
	file_type varchar(1) NULL,
	CONSTRAINT file_info_pkey PRIMARY KEY (id)
);


CREATE TABLE public.file_comment (
    id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
    file_id int8 NULL,
    content varchar(1000) NULL,
    file_type varchar(1) NULL,
    create_dt timestamp(6) null,
    CONSTRAINT file_comment_pkey PRIMARY KEY (id)
);