CREATE TABLE view_tb (
view_id varchar primary key default uuid_generate_v4(),
createAt timestamp           default current_timestamp,
article_id          varchar not null references article_tb (article_id) on delete cascade,
author_id        varchar not null
);



