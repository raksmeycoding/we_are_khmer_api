-- Article
-- must run
create table article_tb
(
    article_id   varchar primary key default uuid_generate_v4(),
    title        varchar                                   not null,
    sub_title    varchar,
    publish_date timestamp           default current_timestamp,
    description  varchar                                   not null,
    updatedAt    timestamp           default current_timestamp,
    image        varchar,
    count_view   integer             default 0,
    isBan        boolean             default false,
    hero_card_in varchar,
    user_id      varchar references user_tb (user_id)      not null,
    category_id  varchar references category (category_id) not null
);

alter table article_tb
    add constraint fk_user_author
        foreign key (user_id)
            references user_tb (user_id)
            deferrable initially deferred;


create or replace function check_user_author()
    returns trigger as
$$
BEGIN
    if not exists(
            select 1
            from user_tb
            where user_id = NEW.user_id
              and is_author = true
        ) then
        raise exception 'Only authors can be associated with a category';
    end if;

    return NEW;
end;
$$
    language plpgsql;


create trigger trg_check_user_author
    before insert or update
    on
        article_tb
    for each row
execute function check_user_author();



-- end must run;




-- check if article exist by id
select exists(select 1 from article_tb where article_tb.article_id = '773066fb-bd6c-4a75-94d6-9c8b3c24e31c');

-- work, but not satisfied
select ab.article_id,
       ab.user_id,
       ab.category_id,
       ab.title,
       ab.sub_title,
       ab.publish_date,
       ab.description,
       ab.updatedat,
       ab.image,
       ab.count_view,
       ab.isban,
       ab.hero_card_in,
       ub.username as author_name,
       c.category_name,
       (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count
from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
where ab.article_id = '8256a9af-da04-4c25-837f-3b9ccebd443a';


-- work
select ab.*, (select count(*) from react_tb where react_tb.article_id = ab.article_id) as react_count from article_tb ab where user_id = '5b1ed971-3338-4a65-be91-4979c0bbd427';


-- get article by category name
select ab.article_id,
       ab.user_id,
       ab.category_id,
       ab.title,
       ab.sub_title,
       ab.publish_date,
       ab.description,
       ab.updatedat,
       concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
       ab.count_view,
       ab.isban,
       ab.hero_card_in,
       ub.username                                                                as author_name,
       c.category_name,
       (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
from article_tb ab
         inner join user_tb ub on ab.user_id = ub.user_id
         inner join category c on c.category_id = ab.category_id
where category_name = 'Khmer Angkor';








select ab.article_id,
       ab.user_id,
       ab.category_id,
       ab.title,
       ab.sub_title,
       ab.publish_date,
       ab.description,
       ab.updatedat,
       concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
       ab.count_view,
       ab.isban,
       ab.hero_card_in,
       ub.username                                                                as author_name,
       c.category_name,
       (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
from article_tb ab
         inner join user_tb ub on ab.user_id = ub.user_id
         inner join category c on c.category_id = ab.category_id
where category_name = 'Khmer Angkor';




-- getArticle with the most view
select ab.article_id,
       ab.user_id,
       ab.category_id,
       ab.title,
       ab.sub_title,
       ab.publish_date,
       ab.description,
       ab.updatedat,
       concat('http://localhost:8080/api/v1/files/file/filename?name=', ab.image) as image,
       ab.count_view,
       ab.isban,
       ab.hero_card_in,
       ub.username                                                                as author_name,
       c.category_name,
       (select count(*) from react_tb where react_tb.article_id = ab.article_id)  as react_count
from article_tb ab
         inner join user_tb ub on ab.user_id = ub.user_id
         inner join category c on c.category_id = ab.category_id
where isBan = false order by count_view desc ;




-- increase article view count
-- update article_tb set count_view = '3' where article_id = '' returning article_id;
update article_tb set count_view = count_view + 1 where article_id = '2014f4dd-d5ae-4b92-867c-922a75cb2503' returning article_id;






