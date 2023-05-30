update user_tb set is_enable = true where user_id = (select user_id from otp2 where otp2.token = '980487') returning *;


-- Create OTP table

create table otp2
(
    token_id  varchar primary key default uuid_generate_v4() not null,
    token     varchar                                        not null unique,
    email varchar,
    temp_password varchar,
    createAt  timestamp           default current_timestamp,
    expiredAt timestamp                                      not null,
    isExpired boolean,
    user_id   varchar references user_tb (user_id) on update cascade on delete cascade
);
drop table otp2;


select *
from otp2;


select *
from otp2
where token = 'tokenw9reujewr9jdffdfsd';

-- create
insert into otp2(token, email, temp_password, expiredAt, user_id)
values ('tokenw9reujewr9jdffdfsd','123@gmail.com','435', '2023-5-15', 'e57bd610-ee40-457d-9b3f-34cf456617d7')
returning *;

-- delete
delete
from otp2
where token = 'tokenw9reujewr9jdfdfsd'
returning *;

-- update column
update user_tb set is_enable = true where user_id = (select user_id from otp2 where otp2.token = '353598') returning *;


WITH updated_users AS (
    UPDATE user_tb
        SET is_enable = true
        WHERE user_id = (SELECT user_id FROM otp2 WHERE otp2.token = '353598')
        RETURNING *
)
SELECT * FROM otp2;



-- bug error
-- WITH updated_users AS (
-- UPDATE user_tb
-- SET is_enable = true
-- WHERE user_id = (SELECT user_id FROM otp2 WHERE otp2.token = '248521')
--     RETURNING *
--     )
-- SELECT * from otp2 where otp2.user_id = updated_users.user_id;



WITH updated_users AS (
    UPDATE user_tb
        SET is_enable = true
        WHERE user_id = (SELECT user_id FROM otp2 WHERE otp2.token = '248521')
        RETURNING *
)
SELECT otp2.*
FROM otp2
         JOIN updated_users ON otp2.user_id = updated_users.user_id;





