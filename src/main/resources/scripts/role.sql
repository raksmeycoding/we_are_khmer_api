-- v1
-- insert into user_role_tb (user_id, role_id)
-- values ((select user_id from user_tb where user_tb.user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'),
--         (select role_id from role_tb where name = 'ROLE_AUTHOR'))
-- returning user_id;


-- v2
WITH inserted_user AS (
    INSERT INTO user_role_tb (user_id, role_id)
        VALUES (
                   (SELECT user_id FROM user_tb WHERE user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'),
                   (SELECT role_id FROM role_tb WHERE name = 'ROLE_AUTHOR')
               )
        RETURNING user_id
)
UPDATE user_tb
SET is_author = true
FROM inserted_user
WHERE user_tb.user_id = inserted_user.user_id
RETURNING user_tb.user_id;



-- version 3
WITH inserted_user_role AS (
    INSERT INTO user_role_tb (user_id, role_id)
        VALUES (
                   (SELECT user_id FROM user_tb WHERE user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'),
                   (SELECT role_id FROM role_tb WHERE name = 'ROLE_AUTHOR')
               )
        RETURNING user_id
)
UPDATE user_tb
SET is_author = TRUE
WHERE user_id = (SELECT user_id FROM inserted_user_role);

UPDATE author_request_tb
SET is_author_accepted = TRUE
WHERE author_request_tb.user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0'
RETURNING user_id;






-- create or replace function auto_assign_role_AUTHOR_to_user()
--     returns trigger as
-- $$
-- begin
--     insert into user_role_tb (user_id, role_id)
--     values (new."user_id", (select role_id from role_tb where name = 'ROLE_AUTHOR'));
--     return new;
-- end;
-- $$
--     language 'plpgsql';
--
--
-- create trigger assign_role_user_to_normal_user_trigger
--     after insert
--     on author_request_tb
--     for each row
-- execute procedure auto_assign_role_AUTHOR_to_user();
--
--
-- drop trigger assign_role_user_to_normal_user_trigger on author_request_tb;





