-- must execute
create table if not exists react_tb
(
    react_id   varchar primary key default uuid_generate_v4(),
    user_id    varchar references user_tb (user_id) on delete cascade not null,
    article_id varchar references article_tb (article_id) on delete cascade not null,
    status     bool                default false,
    createAt   timestamp           default current_timestamp,
    updateAt   timestamp           default current_timestamp
);
drop table react_tb;


-- must execute
-- handle user like and unlike function
create or replace function handle_user_like(
    userId varchar,
    articleId varchar,
    action varchar
) returns boolean as
$$
    declare
        status boolean;
begin
    if action = 'like' then
        insert into react_tb (user_id, article_id, status) values (userId, articleId, true);
        status = true;
    elseif action = 'unlike' then
        delete from react_tb where react_tb.article_id = articleId and react_tb.user_id = userId;
        status = false;
    else
--         invalid valid action, raise and exception and handle accordingly
        raise exception 'invalid action: %', action;
    end if;

    return status;

end;
$$
    language plpgsql;


-- test
select handle_user_like('5b1ed971-3338-4a65-be91-4979c0bbd427', '6ba98ca2-fa24-4165-a288-a54cbd12e1c2', 'like');

-- must execute
-- check user like limit, only exist one record on a table
create or replace function check_exist_only_one_like_in_a_record_per_user()
    returns trigger as
$$
declare
    count_user_Like integer;
begin
    select count(*) into count_user_Like
    from react_tb where user_id = NEW.user_id;

    if count_user_Like > 0 then
        raise exception 'one article, one user can like';
    end if;

    return NEW;
end;
$$ language plpgsql;

-- must execute
create trigger trg_check_exist_only_one_like_in_a_record_per_user
    before insert or update on react_tb for each row execute function check_exist_only_one_like_in_a_record_per_user();


-- Test
-- get count user_react_like_article
select count(*) from react_tb where article_id = '8256a9af-da04-4c25-837f-3b9ccebd443a'