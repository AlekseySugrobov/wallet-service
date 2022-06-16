create table Players
(
    id         bigserial      not null primary key,
    first_name varchar(255)   not null,
    last_name  varchar(255)   not null,
    balance    numeric(19, 2) not null default 0.0,
    created_at timestamp      not null default now(),
    updated_at timestamp      not null default now()
);

insert into Players (first_name, last_name, balance)
values ('Jhon', 'Travolta', 2000.0),
       ('Viktor', 'Viktyuk', 100.0),
       ('Yuirij', 'Shatunov', 0.0),
       ('Rodion', 'Gazmanov', 100.0);