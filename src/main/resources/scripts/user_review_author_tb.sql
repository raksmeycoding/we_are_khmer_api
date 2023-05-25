create table user_review_author_tb
(
    user_review_author_id varchar primary key default uuid_generate_v4(),
    user_id               varchar references user_tb (user_id) not null,
    author_id             varchar references user_tb (user_id) not null,
    author_name           varchar,
    create_at             timestamp           default current_timestamp,
    update_at             timestamp           default current_timestamp,
    comment               varchar                              not null,
    parent_id             varchar references user_tb (user_id)
);


-- need to validate the existence of author_id
-- need to validate the existence of user_id
insert into user_review_author_tb (user_id, author_id, author_name, comment)
values ('1a816fc1-90cb-480a-93ed-6b5e21322bd0', (select user_tb.user_id from user_tb where is_author = 'true' and user_tb.user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'),
        (select username from user_tb where is_author = true and user_tb.user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'), 'all of article of this author is so coll') returning *;



