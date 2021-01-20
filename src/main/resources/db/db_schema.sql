create extension "uuid-ossp";

create table rooms (
    id          uuid primary key,
    number      varchar(32) not null,
    seat_number integer not null,
    type        varchar(128) not null,
    status      varchar(128) not null,
    price       float not null
);

create table requests (
    id          uuid primary key,
    seat_number integer not null,
    type        varchar(32) not null,
    date_from   timestamp not null,
    date_to     timestamp not null
);

create table orders (
    id            uuid primary key,
    creation_date timestamp not null,
    price         float not null,
    room_number   varchar(32) not null,
    type          varchar(128) not null,
    date_from     timestamp not null,
    date_to       timestamp not null
);

alter table requests add column phone varchar(10);
alter table orders add column phone varchar(10);
alter table orders add column paid boolean;

create table users (
    id uuid primary key,
    username varchar(255),
    password varchar(255),
    full_name varchar(255),
    email varchar(255),
    role varchar(255)
);

create table sessions (
    id varchar(255),
    user_id uuid,
    role varchar(255),
    valid_until timestamp
);

alter table sessions add constraint f1 foreign key (user_id) references users(id);