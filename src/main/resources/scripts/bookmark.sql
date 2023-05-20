
CREATE TABLE bookmark_tb(
    bookmark_id varchar primary key default uuid_generate_v4(),
    user_id varchar references user_tb (user_id)      not null,
    article_id varchar references article_tb(article_id) not null,
    created_at timestamp default current_timestamp
)