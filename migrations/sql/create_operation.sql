create table if not exists operation(
    operation_id bigint generated always as identity,
    type varchar not null,
    sum bigint not null,
    created_at timestamp with time zone,
    category_id bigint not null
);