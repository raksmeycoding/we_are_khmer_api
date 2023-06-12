create or replace function limit_user_education() returns trigger
as
$$
declare
    user_id_exist INTEGER;
BEGIN
    SELECT count(*) into user_id_exist from education where education.user_id = NEW.user_id;

    if user_id_exist >= 3 then
        raise exception 'A user can have maximum 3 education.';
    end if;

    return NEW;


end;
$$ language plpgsql;

create trigger trg_limit_user_education before insert or update on education for each row execute function limit_user_education();

drop trigger trg_limit_user_education ON education;



-- validate delete old record when user register again and again
delete from education where user_id = 'f27491a7-346c-4a4d-9db7-882daab7284c';
-- delete from quote_tb where user_id = '759fed9b-edd7-4b23-b019-5e6858ee8a34';
