select *
from category
where category_id = '2cfe7550-2311-4867-a406-a7caa85e37fb';

select *
from article_tb
where article_id = '2cfe7550-2311-4867-a406-a7caa85e37fb';


select count(*)
from article_tb as article_count
where user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0';



update article_tb
set user_id = 'd0e1bd19-d4bc-45d5-9beb-32538d16b769'
where user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0';


-- select coalesce((nullif(article_tb.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image from article_tb where article_id = '3a6dfa5a-6c76-475a-808b-e26476fd8935';


update article_tb
set image = 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80';



select ab.user_id
from article_tb ab
         inner join react_tb rb on ab.article_id = 'e8912fd9-9673-46a3-9c50-5be67aadd137';


select article_tb.user_id
from article_tb
where article_tb.article_id = 'e8912fd9-9673-46a3-9c50-5be67aadd137';

select max(ub.user_id)
from user_tb ub
         inner join article_tb a on ub.user_id = a.user_id
where ub.user_id = 'd0e1bd19-d4bc-45d5-9beb-32538d16b769';



update article_tb
set user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'
where article_id = '786fde51-57b3-4764-acdc-ce8e329a90aa';


-- SELECT a.article_id, a.user_id, a.category_id, a.title FROM article_tb a WHERE 1=1 AND a.title LIKE CONCAT('%', #{title}, '%')

SELECT a.article_id, a.user_id, a.category_id, a.title
FROM article_tb a
WHERE 1 = 1
  AND a.title = 'Oral Mountain';

SELECT a.article_id, a.user_id, a.category_id, a.title, a.sub_title, a.description, publish_date
FROM article_tb a
ORDER BY a.count_view desc;



SELECT a.article_id,
       a.user_id,
       a.category_id,
       a.title,
       a.sub_title,
       a.description,
       a.publish_date,
       a.count_view
FROM article_tb a
WHERE (DATE(a.publish_date) = '2023-06-06');



SELECT a.article_id,
       a.title,
       a.sub_title,
       a.publish_date,
       a.description,
       a.updatedat                                                         as updateat,
       a.image,
       a.count_view,
       a.isban,
       a.hero_card_in,
       a.user_id,
       a.category_id,
       ub.photo_url,
       ub.username                                                         as author_name,
       c.category_name,
       (select count(*) from react_tb r where r.article_id = a.article_id) as react_count
FROM article_tb a
         INNER JOIN user_tb ub on ub.user_id = a.user_id
         INNER JOIN category c on c.category_id = a.category_id
WHERE (1 = 1 AND a.isban = false AND DATE(a.publish_date) = '2023-06-10');


select *
from article_tb
where date(article_tb.publish_date) = '2023-06-10';


select a.article_id, a.title
from article_tb a
         left join user_tb u on a.user_id = u.user_id
         left join bookmark_tb bt on bt.user_id = u.user_id;



select count(*)
from article_tb a
         inner join category c on a.category_id = c.category_id
         inner join user_tb u on u.user_id = a.user_id and a.isban = false;


SELECT a.article_id,
       a.title,
       a.sub_title,
       a.publish_date,
       a.description,
       a.updatedat                                                                           AS updateat,
       a.image,
       a.count_view,
       a.isban,
       a.hero_card_in,
       a.user_id,
       a.category_id,
       ub.photo_url,
       ub.username                                                                           AS author_name,
       c.category_name,
       (SELECT COUNT(*) FROM react_tb r WHERE r.article_id = a.article_id)                   AS react_count,
       CASE WHEN b.user_id = 'f27491a7-346c-4a4d-9db7-882daab7284c' THEN true ELSE false END AS bookmarked,

FROM article_tb a
         INNER JOIN user_tb ub ON ub.user_id = a.user_id
         INNER JOIN category c ON c.category_id = a.category_id
         LEFT OUTER JOIN bookmark_tb b ON b.article_id = a.article_id;



update user_tb
set data_of_birth = '2023-06-06'
where user_id = 'c7c13f75-feae-4fd6-99b2-cd59c85306b5';


LEFT OUTER JOIN bookmark_tb b ON b.article_id = a.article_id;



SELECT art.author_request_name,
       ut.email,
       ut.photo_url,
       art.user_id,
       art.createat,
       art.reason,
       q_name,
       e_name,
       wet.w_name
FROM author_request_tb as art
         INNER JOIN quote_tb qt on art.user_id = qt.user_id
         INNER JOIN education e on art.user_id = e.user_id
         INNER JOIN working_experience_tb wet on art.user_id = wet.user_id
         INNER JOIN user_tb ut on art.user_id = ut.user_id
WHERE art.user_id = 'c7c13f75-feae-4fd6-99b2-cd59c85306b5'
  AND is_author_accepted = 'PENDING';



SELECT art.*
FROM author_request_tb as art
WHERE art.user_id = 'c7c13f75-feae-4fd6-99b2-cd59c85306b5';


SELECT art.*, ut.email, ut.photo_url
FROM author_request_tb as art
         INNER JOIN user_tb ut on ut.user_id = art.user_id
WHERE art.user_id = 'c7c13f75-feae-4fd6-99b2-cd59c85306b5'
  AND is_author_accepted = 'PENDING';


-- get total view all records for author by author_id
select sum(count_view) as total_view
from article_tb a
where user_id = 'd0e1bd19-d4bc-45d5-9beb-32538d16b769';



select *
from article_tb
where user_id = 'd0e1bd19-d4bc-45d5-9beb-32538d16b769';


-- check author role

select user_id
from user_role_tb
where user_id = '7e913fab-eb8c-4d52-aff8-266eb1185d7a';


select exists(select 1
              from author_request_tb arb
              where user_id = '0cd89dbf-0075-48a0-8be0-fd5dd786c37b'
                and is_author_accepted = 'REJECTED');


-- change url
UPDATE public.user_tb
SET photo_url = CONCAT('https://api.domrra.site', SUBSTRING(photo_url, LENGTH('http://localhost:8080') + 1))
WHERE photo_url LIKE 'http://localhost:8080%';


UPDATE public.article_tb
SET image = CONCAT('https://api.domrra.site', SUBSTRING(image, LENGTH('http://localhost:8080') + 1))
WHERE article_tb.image LIKE 'http://localhost:8080%';


-- reverse
UPDATE public.user_tb
SET photo_url = CONCAT('http://localhost:8080', SUBSTRING(photo_url, LENGTH('https://api.domrra.site') + 1))
WHERE photo_url LIKE 'https://api.domrra.site%';


UPDATE public.article_tb
SET image = CONCAT('https://http://localhost:8080', SUBSTRING(image, LENGTH('https://api.domrra.site') + 1))
WHERE article_tb.image LIKE 'https://api.domrra.sitehttps://api.domrra.site%';


select exists(select 1 from user_tb ub where email = 'eamdayan@gmail.com' and is_enable = true) as user_exist;



-- this method is only be used in register feature
create or replace function checkUserAuthentication(userEmail varchar) returns void as
$$
BEGIN
    if exists(select 1 from user_tb ut where email = userEmail and is_enable = true) and
       not exists(select 1 from otp_tb ot where email = userEmail) then
        raise exception 'User is already signed up.' using errcode = 'P0001';
    elseif exists(select 1 from user_tb ut where email = userEmail and is_enable = false) and
           not exists(select 1 from otp_tb where email = userEmail) then
        raise exception 'User is banded.' using errcode = 'P0002';
    elseif exists(select 1 from user_tb ut where email = userEmail and is_enable = false) and
           exists(select 1 from otp_tb where email = userEmail) then
        raise exception 'User is not verified yet.' using errcode = 'P0003';
    end if;
    RETURN;
END;
$$ language plpgsql;



select *
from checkUserAuthentication('eamdayan@gmail.com');
select *
from checkUserAuthentication('kongthary240@gmail.com');
select *
from checkUserAuthentication('dupsicekne@gufum.com');





