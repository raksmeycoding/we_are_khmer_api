create or replace function limit_user_quote() returns trigger as
$$
declare
    user_id_exist INTEGER;
BEGIN
    select count(*) into user_id_exist from quote_tb where quote_tb.user_id = NEW.user_id;
    if user_id_exist >= 3 then
        raise exception 'The user already has 3 quotes in the table.';
    end if;

    return NEW;
end;
$$ language plpgsql;

drop function limit_user_quote cascade;


create trigger trg_limit_user_quote before insert on public.quote_tb for each row execute function limit_user_quote();
drop trigger trg_limit_user_quote on quote_tb;




-- validate when user trying to register again and again
delete from quote_tb where user_id = '759fed9b-edd7-4b23-b019-5e6858ee8a34';
delete from quote_tb where user_id = '759fed9b-edd7-4b23-b019-5e6858ee8a34';