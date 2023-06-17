update user_tb
set is_enable = true
where user_id = (select user_id from otp2 where otp2.token = '980487')
returning *;


-- Create OTP table

create table otp2
(
    token_id      varchar primary key default uuid_generate_v4() not null,
    token         varchar                                        not null unique,
    email         varchar,
    temp_password varchar,
    createAt      timestamp           default current_timestamp,
    expiredAt     timestamp                                      not null,
    isExpired     boolean,
    user_id       varchar references user_tb (user_id) on update cascade on delete cascade
);
drop table otp2;


select *
from otp2;


select *
from otp2
where token = 'tokenw9reujewr9jdffdfsd';

-- create
insert into otp2(token, email, temp_password, expiredAt, user_id)
values ('tokenw9reujewr9jdffdfsd', '123@gmail.com', '435', '2023-5-15', 'e57bd610-ee40-457d-9b3f-34cf456617d7')
returning *;

-- delete
delete
from otp2
where token = 'tokenw9reujewr9jdfdfsd'
returning *;

-- update column
update user_tb
set is_enable = true
where user_id = (select user_id from otp2 where otp2.token = '353598')
returning *;


WITH updated_users AS (
    UPDATE user_tb
        SET is_enable = true
        WHERE user_id = (SELECT user_id FROM otp2 WHERE otp2.token = '353598')
        RETURNING *)
SELECT *
FROM otp2;



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
        RETURNING *)
SELECT otp2.*
FROM otp2
         JOIN updated_users ON otp2.user_id = updated_users.user_id;


-- create to author motomaically delete token
delete
from otp_tb
where email = 'vongraksmey0809@gmail.com';

-- Check if the row exists
CREATE OR REPLACE FUNCTION handle_resend_otp_tb(emailForResendVerificationCode varchar)
    RETURNS SETOF otp_tb AS
$$
DECLARE
    returned_row otp_tb%ROWTYPE;
    otp_number   varchar;
BEGIN
    IF EXISTS(select 1 from user_tb u where u.email = emailForResendVerificationCode and is_enable = false) and
       exists(select 1 from otp_tb o where o.email = emailForResendVerificationCode) THEN
        SELECT * INTO returned_row FROM otp_tb WHERE email = emailForResendVerificationCode;
        RAISE NOTICE 'Email exists: %', returned_row.email;
        otp_number := LPAD(FLOOR(RANDOM() * 1000000)::text, 6, '0');
        raise notice 'Generate otp number: %', otp_number;
        --      update the token row
--         returned_row.token := otp_number;
--         returned_row.isexpired := false;
--         returned_row.createat := current_timestamp;
--         returned_row.expiredat := current_timestamp + interval '2 minutes'::interval;
--         instead of dircetly assignment, you can user row constructor
        returned_row :=
                ROW (returned_row.token_id, otp_number, returned_row.email, returned_row.temp_password, returned_row.createat, returned_row.expiredat, false, returned_row.user_id);
--         after modify the record we got from our table we insert into our system back
        delete from otp_tb where email = emailForResendVerificationCode;
        insert into otp_tb
        values (returned_row.token_id, returned_row.token, returned_row.email, returned_row.temp_password,
                returned_row.createat, returned_row.expiredat, returned_row.isexpired, returned_row.user_id);
        RETURN NEXT returned_row;
    elseif exists(select 1 from user_tb u where u.email = emailForResendVerificationCode and is_enable = false) and
           not exists(select 1 from otp_tb o where o.email = emailForResendVerificationCode) then
        raise exception 'This user had already banded from our system. your are not able to get a resend verification';
    else
        raise exception 'In our system detected that no user with email % needing get verification to verify email...', emailForResendVerificationCode using errcode = 'P0001';
    end if;
    RETURN;
END;
$$ LANGUAGE plpgsql;

-- select not exists(select 1 from user_tb u where u.email = 'vongraksmey0809@gmail.com' and is_enable = false);


drop function handle_resend_otp_tb ();


-- Assume you have a table called "users" with columns "username" and "password"


select *
from handle_resend_otp_tb('vongraksmey0809@gmail.com');


CREATE OR REPLACE FUNCTION validUserInputPassword(userInputEmail varchar)
    RETURNS varchar AS
$$
DECLARE
    userRawPassword varchar;
BEGIN
    IF EXISTS(SELECT 1 FROM user_tb u WHERE u.email = userInputEmail) THEN
        SELECT u.password INTO userRawPassword FROM user_tb u WHERE email = userInputEmail;
        RETURN userRawPassword;
    ELSE
        RAISE EXCEPTION 'No user with this email % within our system.', userInputEmail;
    END IF;
END;
$$ LANGUAGE plpgsql;


select *
from validUserInputPassword('vongraksmey0809@gmail.com');








