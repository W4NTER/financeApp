create table if not exists category(
    category_id bigint generated always as identity,
    title varchar not null,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    chat_id bigint not null
);