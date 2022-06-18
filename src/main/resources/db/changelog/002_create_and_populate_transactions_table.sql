create table transactions
(
    id             bigserial      not null primary key,
    transaction_id varchar(255)   not null,
    wallet_id      bigint,
    type           varchar(6)     not null check (type in ('DEBIT', 'CREDIT')),
    amount         numeric(19, 2) not null,
    comment        varchar(1000),
    status         varchar(7)     not null check ( status in
                                                   ('SUCCESS', 'FAILED')),
    created_at     timestamp      not null default now(),
    updated_at     timestamp      not null default now(),
    foreign key (wallet_id) references wallets (id),
    unique (transaction_id)
);

insert into transactions (transaction_id, wallet_id, type, amount, comment, status)
values ('asd123', 1, 'CREDIT', 1000.0, 'Insufficient funds', 'FAILED'),
       ('asd124', 1, 'DEBIT', 100.0, null, 'SUCCESS'),
       ('asd125', 1, 'CREDIT', 100.0, null, 'SUCCESS');