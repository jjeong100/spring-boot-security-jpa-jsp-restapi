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