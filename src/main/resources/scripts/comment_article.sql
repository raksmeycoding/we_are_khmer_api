create table comment_tb
(
    comment_id varchar primary key default uuid_generate_v4(),
    user_id    varchar references user_tb (user_id) on delete cascade not null,
    article_id varchar references article_tb (article_id) on delete cascade not null,
    parent_id  varchar references comment_tb (comment_id),
    comment    varchar             default null,
    createAt   timestamp           default current_timestamp,
    update     timestamp           default current_timestamp
);

-- Test
drop table comment_tb;


-- TEST USER COMMNET
INSERT INTO comment_tb (user_id, article_id, comment)
VALUES ('5b1ed971-3338-4a65-be91-4979c0bbd427', '8256a9af-da04-4c25-837f-3b9ccebd443a', 'this article so meaningful') returning *
;


-- test author reply comment
INSERT INTO comment_tb (user_id, article_id, parent_id, comment)
values ('5b1ed971-3338-4a65-be91-4979c0bbd427', '8256a9af-da04-4c25-837f-3b9ccebd443a',
        '0c22bb0c-621d-4862-a52c-48590f65edee', 'No! this is not right');


-- only author can reply to user comment it his article
-- author can reply only one the to one user's comment

-- let create function handling user comment to any article
-- didn't used in repository
-- create or replace function user_comment_to_article(article_id_param varchar)
--     returns table
--             (
--                 comment_id varchar,
--                 user_id    varchar,
--                 article_id varchar,
--                 parent_id varchar,
--                 comment    varchar,
--                 createAt timestamp,
--                 updateAt timestamp
--             )
--     language plpgsql
-- as
-- $$
-- begin
--     return query select cb.* from comment_tb cb where cb.article_id = cast(article_id_param as varchar);
--     return;
-- end;
-- $$;

-- v1
-- select cb.*, (select comment from comment_tb where comment_tb.parent_id is not null and comment_tb.parent_id = cb.comment_id) as author_replay from comment_tb cb where cb.parent_id is null and article_id = '8256a9af-da04-4c25-837f-3b9ccebd443a';
-- v1.1 (used in repository)
select cb.*,
       (select username  from comment_tb inner join user_tb on comment_tb.user_id = user_tb.user_id where comment_tb.parent_id is not null and comment_tb.parent_id = cb.comment_id ) as author_replay_name,
       (select comment from comment_tb where comment_tb.parent_id is not null and comment_tb.parent_id = cb.comment_id) as author_replay_comment
from comment_tb cb where cb.parent_id is null and article_id = '8256a9af-da04-4c25-837f-3b9ccebd443a';

-- select comment from comment_tb where comment_tb.parent_id is not null and comment_tb.parent_id = '0c22bb0c-621d-4862-a52c-48590f65edee';
--
-- drop function user_comment_to_article(article_id_param varchar);
--
-- select * from user_comment_to_article('8256a9af-da04-4c25-837f-3b9ccebd443a');




-- select comment_id, user_id, article_id, comment
-- from comment_tb
-- where article_id = '8256a9af-da04-4c25-837f-3b9ccebd443a';

-- test script
-- select *
-- from comment_tb;











