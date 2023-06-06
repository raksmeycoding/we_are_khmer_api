CREATE TYPE status as ENUM ('PENDING', 'REJECTED', 'APPROVED');

create table user_report_author_tb
(
    user_report_author_id varchar primary key default uuid_generate_v4(),
    user_id               varchar references user_tb (user_id) on delete cascade not null,
    author_id             varchar references user_tb (user_id) on delete cascade not null,
    reciever_id           varchar                                                not null,
    reason                varchar                                                not null,
    create_at             timestamp           default current_timestamp,
    status                status              default 'PENDING',
    one_signal_id         varchar
);
drop table user_report_author_tb;



create or replace function insert_user_user_report_to_author(user_id_param varchar, author_id_param varchar,
                                                             reason_param varchar)
    returns table
            (
                user_report_author_id varchar,
                user_id               varchar,
                author_id             varchar,
                reciever_id           varchar,
                reason                varchar,
                create_at             timestamp,
                status                status,
                one_signal_id         varchar
            )
as
$$
BEGIN
    if (select exists(select 1 from user_tb where is_author = true and user_tb.user_id = author_id_param)) then
        return query insert into user_report_author_tb (user_id, author_id, reciever_id, reason)
            values (user_id_param, author_id_param, (select ub.user_id
                                                     from user_tb ub
                                                              inner join user_role_tb urb on ub.user_id = urb.user_id
                                                              inner join role_tb rb on urb.role_id = rb.role_id
                                                     where rb.name = 'ROLE_ADMIN'), reason_param) returning *;
    elseif (select exists(select 1 from user_tb where is_author = false and user_tb.user_id = author_id_param)) then
        raise exception 'This user is not author.';
    end if;

end;
$$ language plpgsql;

-- drop function insert_user_user_report_to_author cascade;

insert into user_report_author_tb (user_id, author_id, reciever_id, reason)
values ('089d5b48-6f86-49a8-9d3d-c9573bf02c1f', '1a816fc1-90cb-480a-93ed-6b5e21322bd0', (select ub.user_id
                                                                                         from user_tb ub
                                                                                                  inner join user_role_tb urb on ub.user_id = urb.user_id
                                                                                                  inner join role_tb rb on urb.role_id = rb.role_id
                                                                                         where rb.name = 'ROLE_ADMIN'),
        'This user, all posts mostly are bad contents')
returning *;



select *
from insert_user_user_report_to_author('089d5b48-6f86-49a8-9d3d-c9573bf02c1f', '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75',
                                       'This user, all posts mostly are bad contents');


-- get all notification from
select *
from user_report_author_tb
order by create_at desc;



-- create user band author
create function check_status_on_table_user_report_author() returns trigger as
$$
begin
    if NEW.status = 'APPROVED' then
        update user_tb set is_enable = false where user_tb.user_id = NEW.author_id;
    elseif NEW.status = 'REJECTED' then
        update user_report_author_tb set status = NEW.status where user_report_author_tb.author_id = NEW.author_id;
    end if;
    return NEW;
end;
$$
    language plpgsql;


DROP function check_status_on_table_user_report_author cascade;
update user_report_author_tb
set status = 'APPROVED'
where user_report_author_tb.author_id = '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75';


create trigger trigger_check_status_on_table_user_report_author
    before insert or update
    on user_report_author_tb
    FOR EACH ROW
execute function check_status_on_table_user_report_author();
DROP TRIGGER trigger_check_status_on_table_user_report_author ON user_report_author_tb;



-- trigger v2
-- CREATE FUNCTION update_user_tb_status() RETURNS TRIGGER AS
-- $$
-- BEGIN
--     start transaction ;
--     IF NEW.status = 'APPROVED' THEN
--         UPDATE user_tb SET is_enable = false WHERE user_id = NEW.author_id;
--     END IF;
--     UPDATE user_report_author_tb SET status = NEW.status WHERE author_id = NEW.author_id;
--     RETURN NEW;
-- END;
-- $$ LANGUAGE plpgsql;
--
--
--
-- drop function update_user_tb_status cascade;
--
--
-- CREATE TRIGGER user_report_author_status_trigger
--     AFTER INSERT OR UPDATE
--     ON user_report_author_tb
--     FOR EACH ROW
-- EXECUTE FUNCTION update_user_tb_status();
--
--
-- drop trigger user_report_author_status_trigger on user_report_author_tb;


-- ⭐ Create index to order table automatically
create index user_report_author_tb_default_order_idx on user_report_author_tb (create_at desc);
drop index user_report_author_tb_default_order_idx;



-- create trigger function to trigger a user can report user only one time
create OR REPLACE function check_one_user_can_report_author_once_time() returns trigger as
$$
declare
    count_user integer;
begin
    select count(*)
    into count_user
    from user_report_author_tb urab
    where urab.user_id = NEW.user_id
      and urab.author_id = NEW.author_id;
    if count_user > 0 then
        raise exception 'You already report this author.';
    end if;
    return NEW;
end;
$$
    language plpgsql;

drop function check_one_user_can_report_author_once_time cascade;



create trigger trigger_check_one_user_can_report_author_once_time
    BEFORE insert
    on user_report_author_tb
    for each row
execute function
    check_one_user_can_report_author_once_time();


drop trigger trigger_check_one_user_can_report_author_once_time on user_report_author_tb;



select count(*)
from user_report_author_tb
where user_id = '089d5b48-6f86-49a8-9d3d-c9573bf02c1f'
  and author_id = '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75'


