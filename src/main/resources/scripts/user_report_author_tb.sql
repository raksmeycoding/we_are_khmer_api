create table user_report_author_tb
(
    user_report_author_id varchar primary key default uuid_generate_v4(),
    user_id               varchar references user_tb (user_id) on delete cascade not null,
    author_id             varchar references user_tb (user_id) on delete cascade not null,
    reciever_id           varchar                                                not null,
    reason                varchar                                                not null,
    create_at             timestamp           default current_timestamp,
    one_signal_id         varchar
);
drop table user_report_author_tb;



create or replace function insert_user_user_report_to_author(user_id_param varchar, author_id_param varchar,
                                                             reciever_id_param varchar,
                                                             reason_param varchar)
    returns table
            (
                user_report_author_id varchar,
                user_id               varchar,
                author_id             varchar,
                reciever_id           varchar,
                reason                varchar,
                create_at             timestamp,
                one_signal_id         varchar
            )
as
$$
BEGIN
    return query insert into user_report_author_tb (user_id, author_id, reciever_id, reason)
        values (user_id_param, author_id_param, reciever_id_param, reason_param) returning *;
end;
$$ language plpgsql;


drop function insert_user_user_report_to_author cascade ;


select *
from insert_user_user_report_to_author('089d5b48-6f86-49a8-9d3d-c9573bf02c1f', '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75',
                                       '1a816fc1-90cb-480a-93ed-6b5e21322bd0',
                                       'This user, all posts mostly are bad contents');


-- get all notification from
select *
from user_report_author_tb;