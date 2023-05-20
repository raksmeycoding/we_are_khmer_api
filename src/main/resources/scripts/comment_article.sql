create table comment_tb
(
    comment_id varchar primary key default uuid_generate_v4(),
    user_id    varchar references user_tb (user_id)       not null,
    article_id varchar references article_tb (article_id) not null,
    parent_id  varchar references comment_tb (comment_id),
    comment    varchar             default null,
    createAt   timestamp           default current_timestamp,
    update     timestamp           default current_timestamp
);

-- Test
drop table comment_tb;


-- TEST USER COMMNET
INSERT INTO comment_tb (user_id, article_id, comment)
VALUES ('5b1ed971-3338-4a65-be91-4979c0bbd427', '8256a9af-da04-4c25-837f-3b9ccebd443a', 'this article so meaningful')
;

select *
from comment_tb;

-- test author reply comment
INSERT INTO comment_tb (user_id, article_id, parent_id, comment)
values ('5b1ed971-3338-4a65-be91-4979c0bbd427', '8256a9af-da04-4c25-837f-3b9ccebd443a',
        '0c22bb0c-621d-4862-a52c-48590f65edee', 'No! this is not right');

