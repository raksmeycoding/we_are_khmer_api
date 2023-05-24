insert into user_role_tb (user_id, role_id)
values ((select user_id from user_tb where user_tb.user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'),
        (select role_id from role_tb where name = 'ROLE_AUTHOR'))
returning user_id;






create or replace function auto_assign_role_AUTHOR_to_user()
    returns trigger as
$$
begin
    insert into user_role_tb (user_id, role_id)
    values (new."user_id", (select role_id from role_tb where name = 'ROLE_AUTHOR'));
    return new;
end;
$$
    language 'plpgsql';


create trigger assign_role_user_to_normal_user_trigger
    after insert
    on author_request_tb
    for each row
execute procedure auto_assign_role_AUTHOR_to_user();


drop trigger assign_role_user_to_normal_user_trigger on author_request_tb;





