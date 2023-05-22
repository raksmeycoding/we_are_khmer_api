
CREATE TABLE bookmark_tb(
    bookmark_id varchar primary key default uuid_generate_v4(),
    user_id varchar references user_tb (user_id)  ON UPDATE CASCADE ON DELETE CASCADE   not null,
    article_id varchar references article_tb(article_id) ON UPDATE CASCADE ON DELETE CASCADE not null,
    created_at timestamp default current_timestamp
)