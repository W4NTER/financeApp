-- alter table chat add column enable boolean not null default true;
--
-- alter table category add column "limit" bigint;

-- alter table chat add column id bigint generated always as identity;

-- alter table chat drop column id;
--
-- ALTER TABLE chat
--     ADD CONSTRAINT unique_chat_id UNIQUE (chat_id);


-- ALTER TABLE category
--     ADD CONSTRAINT fk_category_chat_id
--         FOREIGN KEY (chat_id)
--             REFERENCES chat(chat_id);


-- alter table category add constraint unique_category_id unique (category_id);
--
-- alter table operation add constraint fk_operation_category_id foreign key (category_id) references category(category_id);

-- alter table operation add column title varchar;

-- alter table chat drop column enabled;

-- ALTER TABLE chat ADD PRIMARY KEY (chat_id);

-- CREATE SEQUENCE liabilities_assets_id_seq START 1 INCREMENT 1;
-- ALTER TABLE liabilities_assets ALTER COLUMN id SET DEFAULT nextval('liabilities_assets_id_seq');






