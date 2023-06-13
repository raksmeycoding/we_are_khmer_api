create table rating_tb
(
    rating_id        varchar primary key default uuid_generate_v4(),
    user_id          varchar not null references user_tb (user_id) on delete cascade on update cascade,
    author_id        varchar not null references user_tb (user_id) on delete cascade on update cascade,
    create_at        timestamp           default current_timestamp,
    update_at        timestamp           default current_timestamp,
    number_of_rating integer not null check ( number_of_rating between 1 and 5 )
);

drop table rating_tb;

-- validate number_of_rating <= 5
-- validate to checkout author id first

insert into rating_tb (user_id, author_id, number_of_rating)
VALUES ('1a816fc1-90cb-480a-93ed-6b5e21322bd0', (select user_tb.user_id
                                                 from user_tb
                                                 where user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'
                                                   and is_author = true), 5)
returning *;

select *
from rating_tb;


select round(sum(number_of_rating)::numeric / count(*)::numeric, 2) as sum_rating_number,
       ub.username,
       ub.photo_url
from rating_tb rb
         inner join user_tb ub on rb.author_id = ub.user_id
where rb.author_id = '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75'
group by ub.photo_url, ub.username;


-- find exelecne or good
select case number_of_rating
           when 1 then 'Poor'
           when 2 then 'Below_Average'
           when 3 then 'Average'
           when 4 then 'Good'
           when 5 then 'Excellence'
           else 'Unknown'
           end  as rating_name,
       number_of_rating as rating_number,
       count(*) as rating_count
from rating_tb
where author_id = '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75'
group by number_of_rating;




