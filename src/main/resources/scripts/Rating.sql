create table rating_tb
(
    rating_id        varchar primary key default uuid_generate_v4(),
    user_id          varchar not null references user_tb (user_id) on delete cascade on update cascade,
    author_id        varchar not null references user_tb (user_id) on delete cascade on update cascade,
    create_at        timestamp           default current_timestamp,
    update_at        timestamp           default current_timestamp,
    number_of_rating integer not null check ( number_of_rating <= 5 )
);

drop table rating_tb;

-- validate number_of_rating <= 5
-- validate to checkout author id first

insert into rating_tb (user_id, author_id, number_of_rating)
VALUES ('1a816fc1-90cb-480a-93ed-6b5e21322bd0', (select user_tb.user_id
                                                 from user_tb
                                                 where user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'
                                                   and is_author = true), 5) returning *;

select *
from rating_tb;


select sum(number_of_rating) / count(*) as sum_rating_number,
       ub.username,
       ub.photo_url
from rating_tb rb
         inner join user_tb ub on rb.author_id = ub.user_id
where rb.author_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'
group by ub.photo_url, ub.username;
