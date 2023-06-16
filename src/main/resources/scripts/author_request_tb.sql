CREATE TYPE status as ENUM ('PENDING', 'REJECTED', 'APPROVED');


create table public.author_request_tb
(
    author_request_id   varchar   default uuid_generate_v4() not null primary key,
    user_id             varchar references user_tb (user_id) on delete cascade ,
    author_request_name varchar                              not null,
    is_author_accepted  status    default 'PENDING'::status,
    createat            timestamp default CURRENT_TIMESTAMP,
    reason              varchar                              not null
);

-- drop table  author_request_tb;

-- accept user to be author

-- WITH inserted_user_role AS (
-- INSERT INTO user_role_tb (user_id, role_id)
-- SELECT
--     (SELECT user_id FROM user_tb WHERE user_id = '9783c07a-f598-47a5-88c0-f4428ebde84e'),
--     (SELECT role_id FROM role_tb WHERE name = 'ROLE_AUTHOR')
-- WHERE NOT EXISTS (
--     SELECT 1 FROM user_role_tb
--     WHERE user_id = (SELECT user_id FROM user_tb WHERE user_id = '9783c07a-f598-47a5-88c0-f4428ebde84e')
--   AND role_id = (SELECT role_id FROM role_tb WHERE name = 'ROLE_AUTHOR')
--     )
--     RETURNING user_id
--     )
-- UPDATE user_tb
-- SET is_author = TRUE
-- WHERE user_id = (SELECT user_id FROM inserted_user_role)
--     RETURNING user_id;
--
-- UPDATE author_request_tb
-- SET is_author_accepted = TRUE
-- WHERE user_id = '9783c07a-f598-47a5-88c0-f4428ebde84e'
-- RETURNING user_id;


-- update user request as author to be author
select update_tables_author_request_tb_and_user_tb(true, '34535646436');

-- reject user as author to be author as reject
select update_tables_author_request_tb_and_user_tb(false, '32456465764');


CREATE
OR REPLACE FUNCTION insert_or_update_user_request_as_author(
    user_id_param varchar,
    author_name_param varchar,
    reason_param varchar
)
    RETURNS TABLE (
                      author_request_id varchar,
                      user_id varchar,
                      author_request_name varchar,
                      is_author_accepted status,
                      createat timestamp,
                      reason varchar
                  )
AS $$
BEGIN
    -- Check if user_id exists in author_request_tb
    IF
EXISTS(SELECT 1 FROM author_request_tb WHERE author_request_tb.user_id = user_id_param) THEN
        RETURN QUERY
UPDATE author_request_tb
SET author_request_name = author_name_param,
    reason              = reason_param
WHERE author_request_tb.user_id = user_id_param RETURNING *;
ELSE
        RETURN QUERY INSERT INTO author_request_tb (user_id, author_request_name, reason)
            VALUES (user_id_param, author_name_param, reason_param)
            RETURNING *;
END IF;
END;
$$
LANGUAGE plpgsql;


drop function insert_or_update_user_request_as_author(user_id_param varchar, author_name_param varchar, reason_param varchar);


select *
from insert_or_update_user_request_as_author('dcc1bfec-6c61-4936-bf90-26fc6a25246a', 'raksmey koung',
                                             'i 3 want to be author');


-- validate user is already as author
select rt.name
from user_tb
         inner join user_role_tb urt on user_tb.user_id = urt.user_id
         inner join role_tb rt on urt.role_id = rt.role_id
where urt.user_id = '90fe14d8-49c8-4f52-bfc6-5a8de8a73f75'
  and rt.name = 'ROLE_AUTHOR';




