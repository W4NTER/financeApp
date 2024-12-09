create table if not exists chat(
    id bigint generated always as identity,
    chat_id bigint,
    created_at timestamp with time zone not null
);