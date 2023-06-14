-- select author profile

select u.user_id, u.username, u.email, u.gender, u.data_of_birth
from user_tb u
         inner join user_role_tb urb on urb.user_id = u.user_id
         inner join role_tb r on urb.role_id = r.role_id
where u.user_id = 'ea51e79f-962d-493d-bb82-c7cfc1125169'
  and r.name = 'ROLE_USER';



-- crate  trigger function when is_author change to either ture or false
create or replace function check_user_is_author_status_change() returns trigger as
$$
begin
    if NEW.is_author is false then
        raise notice 'Message: is author false: %', NEW.is_author;
        delete
        from user_role_tb
        where user_id = NEW.user_id
          and user_role_tb.role_id = (select r.role_id from role_tb r where r.name = 'ROLE_AUTHOR');
    end if;
    if NEW.is_author is true then
        raise notice 'Message: is author true: %', NEW.is_author;
        insert into user_role_tb(user_id, role_id) values (NEW.user_id, (select r.role_id from role_tb r where r.name = 'ROLE_AUTHOR'));
    end if;
    return NEW;
end;
$$
    language plpgsql;


drop function check_user_is_author_status_change cascade;


create trigger trg_check_user_is_author_status_change
    before update
    on user_tb
    for each row
execute function check_user_is_author_status_change();

DROP trigger trg_check_user_is_author_status_change on user_tb;



SELECT name
from role_tb
         inner join user_role_tb urt on role_tb.role_id = urt.role_id
         inner join user_tb ut on urt.user_id = ut.user_id
where ut.user_id = 'c7c13f75-feae-4fd6-99b2-cd59c85306b5';

insert into user_role_tb(user_id, role_id)
values ('c7c13f75-feae-4fd6-99b2-cd59c85306b5', (select r.role_id from role_tb r where r.name = 'ROLE_AUTHOR'));

select exists(select 1 from user_tb where is_author = false and user_id = 'c7c13f75-feae-4fd6-99b2-cd59c85306b5')