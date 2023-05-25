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
where category_name = 'Khmer Angkor'


