create table if not exists balance(
    chat_id bigint unique not null,
    sum bigint
);