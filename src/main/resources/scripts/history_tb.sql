create table history_tb
(
    history_id varchar primary key default uuid_generate_v4() unique,
    user_id    varchar references user_tb (user_id) ON DELETE CASCADE       not null,
    article_id varchar references article_tb (article_id) ON DELETE CASCADE not null,
    created_at timestamp default current_timestamp
);