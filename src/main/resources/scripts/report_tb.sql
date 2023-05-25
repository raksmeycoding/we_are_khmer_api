create table report_tb
(
    report_id   varchar primary key default uuid_generate_v4(),
    article_id  varchar references article_tb (article_id) on delete cascade not null,
    crate_at    timestamp           default current_timestamp,
    reason      varchar                                    not null,
    reciever_id varchar references user_tb (user_id)       not null,
    sender_id   varchar references user_tb (user_id)       not null
);
alter table report_tb rename column sender_id to user_id;

-- v1
-- this query only work when exist admin in your application
insert into report_tb(article_id, reason, reciever_id, sender_id)
values ('773066fb-bd6c-4a75-94d6-9c8b3c24e31c', 'this article is not bad to read', (select user_tb.user_id
                                                                                    from user_tb
                                                                                             inner join user_role_tb on user_tb.user_id = user_role_tb.user_id
                                                                                             inner join role_tb on role_tb.role_id = user_role_tb.role_id
                                                                                    where role_tb.name = 'ROLE_ADMIN'),
        '096a84cc-4aac-4e32-a0e9-d2a611bede72');

-- V2
with report_table_return as (
    insert into report_tb (article_id, reason, reciever_id, sender_id)
        values ('773066fb-bd6c-4a75-94d6-9c8b3c24e31c', 'this article is not bad to read', (select user_tb.user_id
                                                                                            from user_tb
                                                                                                     inner join user_role_tb on user_tb.user_id = user_role_tb.user_id
                                                                                                     inner join role_tb on role_tb.role_id = user_role_tb.role_id
                                                                                            where role_tb.name = 'ROLE_ADMIN'),
                '096a84cc-4aac-4e32-a0e9-d2a611bede72') returning *)
select *
from report_table_return;



-- validate report id
select exists(select 1 from report_tb where report_tb.report_id = '773066fb-bd6c-4a75-94d6-9c8b3c24e31c');


-- delete report by id
delete from report_tb where report_id = '' returning *


