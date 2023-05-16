CREATE DATABASE weAreKhmer;

-- install uuid extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- To check if the "uuid-ossp" extension is already installed
SELECT *
FROM pg_extension
WHERE extname = 'uuid-ossp';



CREATE TYPE gender as ENUM ('male', 'female', 'other');
drop type if exists gender;

CREATE TABLE IF NOT EXISTS user_tb
(
    user_id       varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    username      varchar(100) NOT NULL,
    email         varchar(255) NOT NULL UNIQUE,
    password      text         NOT NULL,
    photo_url     text,
    data_of_birth timestamp    not null,
    is_enable     boolean      not null,
    is_author     boolean      not null,
    gender        gender       not null

);
drop table if exists user_tb CASCADE;
alter table user_tb
    alter column username drop not null;
alter table user_tb
    drop constraint user_tb_email_key;
alter table user_tb
    alter column data_of_birth drop not null;
alter table user_tb
    alter column is_author drop not null,
    alter column is_author set default false;
alter table user_tb
    alter column is_enable drop not null,
    alter column is_enable set default false;

INSERT INTO user_tb (username, email, password, photo_url, data_of_birth, is_enable, is_author, gender)
VALUES ('john_doe', 'john.doe@gmail.com', 'password123', NULL, '1990-01-01', true, false, 'male'),
       ('jane_doe', 'jane.doe@gmail.com', 'password456', NULL, '1995-01-01', true, true, 'female'),
       ('vox', 'vox@gmail.com', 'password456', NULL, '1995-01-01', true, true, 'female'),
       ('ethan', 'ethan@gmail.com', 'password456', NULL, '1995-01-01', true, true, 'female'),
       ('bob_smith', 'bob.smith@gmail.com', 'password789', NULL, '2000-01-01', false, false, 'male');

INSERT INTO user_tb (username, email, password, photo_url, data_of_birth, is_enable, is_author, gender)
VALUES ('vitoria', 'victoria@gmail.com', '123', NULL, '2000-01-01', false, false, 'male');

insert into user_tb (email, password, gender)
values ('dara1@gmail.com', '123', 'female');


select *
from user_tb
where email = 'dara@gmail.com';
select *, ut.username as myUserName
from user_tb ut
where email = 'victoria@gmail.com';

-- select uesr by email
select ut.username, ut.email, ut.gender
from user_tb ut
where email = 'ethan@example.com';



CREATE TABLE IF NOT EXISTS role_tb
(
    role_id varchar primary key DEFAULT uuid_generate_v4(),
    name    text NOT NULL UNIQUE
);
DROP TABLE role_tb CASCADE;

INSERT INTO role_tb (name)
VALUES ('ROLE_USER');
INSERT INTO role_tb (name)
VALUES ('ROLE_AUTHOR');


select rt.name as role_name
from user_tb utb
         inner join user_role_tb urt on utb.user_id = urt.user_id
         inner join role_tb rt on urt.role_id = rt.role_id
where urt.user_id = 'bcc760e8-f85d-43c7-8b41-ecc8748b027d';
;



CREATE TABLE IF NOT EXISTS user_role_tb
(
    user_rold_id varchar PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id      varchar references user_tb (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    role_id      varchar references role_tb (role_id) ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE user_role_tb cascade;
alter table user_role_tb
    add constraint unique_user_id_fk unique (user_id, role_id);

INSERT INTO user_role_tb(user_id, role_id)
VALUES ('3beb389a-2a88-47fa-8bbb-35a82d88f1cb', '677a67bd-686d-4298-b2a3-cdd0c729f155');
INSERT INTO user_role_tb(user_id, role_id)
VALUES ('6035135d-7294-487d-8035-2d7befb94834', 'e50dfe6f-a928-4fa6-be70-508dc896a7ca');


select utb.user_id, utb.username, utb.email, rt.name as role_name
from user_tb utb
         inner join user_role_tb urt on utb.user_id = urt.user_id
         inner join role_tb rt on urt.role_id = rt.role_id;

-- get user by email
select *
from user_tb
where email = 'victoria@gmail.com';


select utb.user_id, utb.username, utb.email, rt.name as role_name
from user_tb utb
         inner join user_role_tb urt on utb.user_id = urt.user_id
         inner join role_tb rt on urt.role_id = rt.role_id
where email = 'victoria@gmail.com';

select utb.user_id, utb.username, utb.email, rt.name as role_name
from user_tb utb
         inner join user_role_tb urt on utb.user_id = urt.user_id
         inner join role_tb rt on urt.role_id = rt.role_id
where email = 'victoria@gmail.com';



-- insert author to existing user
-- *** this's not working
insert into user_role_tb (user_id, role_id)
values ('6035135d-7294-487d-8035-2d7befb94834', (select role_id from role_tb where name = 'ROLE_AUTHOR'))
returning user_id;
-- *** this's  working
insert into user_role_tb (user_id, role_id)
values ((select user_id from user_tb where user_tb.user_id = '6035135d-7294-487d-8035-2d7befb94834'),
        (select role_id from role_tb where name = 'ROLE_AUTHOR'))
returning user_id;

select *
from user_tb
where user_id = '6035135d-7294-487d-8035-2d7befb94834';

select *
from user_tb
where user_id = '6035135d-7294-487d-8035-2d7befb94834';
select utb.user_id, utb.username, utb.email, rt.name as role_name
from user_tb utb
         inner join user_role_tb urt on utb.user_id = urt.user_id
         inner join role_tb rt on urt.role_id = rt.role_id
where urt.user_id = 'bcc760e8-f85d-43c7-8b41-ecc8748b027d';



-- trigger function to automatically assign role to normal user
create or replace function assign_role_user_to_normal_user()
    returns trigger as
$$
begin
    insert into user_role_tb (user_id, role_id)
    values (new."user_id", (select role_id from role_tb where name = 'ROLE_USER'));
    return new;
end;
$$
    language 'plpgsql';


create trigger assign_role_user_to_normal_user_trigger
    after insert
    on user_tb
    for each row
execute procedure assign_role_user_to_normal_user();

select role_id
from role_tb
where name = 'ROLE_USER';


-- Category --
create table if not exists category
(
    category_id   varchar primary key default uuid_generate_v4(),
    category_name varchar not null,
    category_url  varchar
);

drop table category;
select *
from category;

alter table category
    alter category_url set default null;


select *
from category
where category_id = '801c45f7-0d95-4dcf-b168-23713407058c';
insert into category(category_name)
values ('temple');
delete
from category
where category_id = '34c9a369-1604-4624-b182-defb34840eab'
returning *;
update category
set category_name = 'PP'
where category_id = '801c45f7-0d95-4dcf-b168-23713407058c'
returning *;


-- Create OTP table

create table otp2
(
    token_id  varchar primary key default uuid_generate_v4() not null,
    token     varchar                                        not null unique,
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
insert into otp2(token, expiredAt, user_id)
values ('tokenw9reujewr9jdffdfsd', '2023-5-15', 'e5058c06-b40a-41a8-98fd-46f1c7768268')
returning *;

-- delete
delete
from otp2
where token = 'tokenw9reujewr9jdfdfsd'
returning *;

-- update column
update user_tb
set is_enable = true
where user_id = (select user_id from otp2 where otp2.token = '9de06d91-2d36-49cb-a384-1a41d8b83236');


-- user profile feature table
create table profile_tb
(
    profile_id         varchar primary key default uuid_generate_v4(),
    user_id            varchar unique references user_tb (user_id) on delete cascade on update cascade,
    working_experience jsonb not null,
    education          jsonb not null,
    reason             varchar
);

drop table profile_tb;


INSERT INTO profile_tb (user_id, working_experience, education, reason)
VALUES ('e5058c06-b40a-41a8-98fd-46f1c7768268', '[
  {
    "title": "Software Developer"
  },
  {
    "title": "Network Developer"
  }
]', '{
  "degree": "Bachelor of Science"
}', 'Career advancement');


select *
from profile_tb;



create table working_experience_tb
(
    wid varchar primary key default uuid_generate_v4(),
    w_name  varchar not null,
    user_id varchar references user_tb(user_id) on update cascade on delete cascade
);

