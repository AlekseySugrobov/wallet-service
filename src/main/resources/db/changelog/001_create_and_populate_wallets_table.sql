create table wallets
(
    id         bigserial      not null primary key,
    user_id    bigint         not null,
    balance    numeric(19, 2) not null default 0.0,
    created_at timestamp      not null default now(),
    updated_at timestamp      not null default now()
);

insert into wallets (user_id, balance)
values (1, 2000.0),
       (2, 100.0),
       (3, 0.0),
       (4, 100.0);