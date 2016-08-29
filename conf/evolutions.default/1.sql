# --- !Ups


create table "markets" ("id" BIGINT auto_increment NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL,"desc" VARCHAR NOT NULL,"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

create table "product_types" ("id" BIGINT auto_increment NOT NULL PRIMARY KEY,"name" VARCHAR NOT NULL,"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);


create table "products" ("id" BIGINT auto_increment NOT NULL PRIMARY KEY,"user_id" BIGINT NOT NULL,"product_type_id" BIGINT NOT NULL,"product_quantity" BIGINT NOT NULL,"product_constant_" DOUBLE NOT NULL,"product_exponential" DOUBLE NOT NULL,"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

create table "offers" ("id" BIGINT auto_increment NOT NULL PRIMARY KEY,"market_id" BIGINT NOT NULL,"off_product_id" BIGINT NOT NULL,"off_amount" BIGINT NOT NULL,"wanted_user_id" BIGINT NOT NULL,"wanted_product_id" BIGINT NOT NULL,"wanted_amount" BIGINT NOT NULL,"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

create table "transactions" ("id" BIGINT auto_increment NOT NULL PRIMARY KEY,"desc" VARCHAR NOT NULL,"off_user_id" BIGINT NOT NULL,"off_product_id" BIGINT NOT NULL,"off_amount" BIGINT NOT NULL,"off_marginal" DOUBLE NOT NULL,"wanted_user_id" BIGINT NOT NULL,"wanted_product_id" BIGINT NOT NULL,"wanted_amount" BIGINT NOT NULL,"wanted_marginal" DOUBLE NOT NULL,"created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

create table "users"(
    "user_id" UUID PRIMARY KEY,
 "provider_id" VARCHAR,
  "provider_key" VARCHAR,
  "first_name" VARCHAR,
  "last_name"  VARCHAR,
  "full_name" VARCHAR,
  "email" VARCHAR,
  "avatar_url" VARCHAR,
  "activated" BOOLEAN,
  "rut" VARCHAR,
  "market_id" BIGINT,
  "created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);


create table "users"(
    "user_id" UUID PRIMARY KEY,
 "provider_id" VARCHAR,
  "provider_key" VARCHAR,
  "first_name" VARCHAR,
  "last_name"  VARCHAR,
  "full_name" VARCHAR,
  "email" VARCHAR,
  "avatar_url" VARCHAR,
  "activated" BOOLEAN,
  "rut" VARCHAR,
  "market_id" BIGINT,
  "created" TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
  create table "passwordinfo"(
  "hasher" VARCHAR,
    "password" VARCHAR,
    "salt" VARCHAR,
    "provider_id" VARCHAR,
    "provider_key" VARCHAR);

INSERT INTO "markets" ("name","desc") VALUES
    ('marketname1', 'marketdesc1'),
    ('marketname2', 'marketdesc2');

INSERT INTO "product_types" ("name") VALUES
    ('name1'),
    ('name2');
INSERT INTO "products" ("user_id","product_type_id","product_quantity","product_constant_","product_exponential") VALUES
    (1, 1,100,1.2,0.5),
    (1, 2,2,2.2,0.1),
    (2, 1,3,3.2,0.1),
    (2, 2,1,1.2,0.5),
    (4, 2,4,4.2,0.1),
    (5, 2,5,5.2,0.2),
    (6, 2,6,6.2,0.2);

INSERT INTO "offers" ("market_id","off_product_id","off_amount","wanted_user_id","wanted_product_id","wanted_amount") VALUES
    (1, 1,100,2,2,1),
    (1, 1,2,2,1,1),
    (1, 1,3,2,1,1),
    (2, 2,4,4,1,1),
    (2, 2,5,5,2,1),
    (2, 2,6,6,2,1);





# --- !Downs
;
drop table "transactions";
drop table "offers";
drop table "products";
drop table "users";
drop table "product_types";
drop table "markets";