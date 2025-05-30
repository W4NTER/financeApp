create table if not exists liabilities_assets(
    chat_id bigint not null unique,
    title varchar not null unique,
    type varchar not null,
    sum bigint not null
);