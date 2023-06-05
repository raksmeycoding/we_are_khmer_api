CREATE TYPE notification_type as ENUM ('COMMENT', 'LIKE', 'REPORT', 'USER_REQUEST_AS_AUTHOR');
drop type if exists notification_type cascade;

create table notification_tb
(
    notification_id      varchar primary key default uuid_generate_v4(),
    createAt             timestamp           default current_timestamp,
    sender_id            varchar not null references user_tb (user_id) on delete cascade,
    sender_image_url     varchar,
    sender_name          varchar,
    receiver_id          varchar not null references user_tb (user_id) on delete cascade,
    notification_type    notification_type,
    notification_type_id varchar
);



drop table notification_tb;



CREATE
    OR REPLACE FUNCTION insert_notification()
    RETURNS TRIGGER AS
$$
BEGIN
    IF
        TG_OP = 'INSERT' THEN
        -- Determine the notification type based on the table
        DECLARE
            notification_type_value notification_type;
            notification_type_id    varchar;
        BEGIN
            IF
                (TG_TABLE_NAME = 'react_tb') THEN
                notification_type_value := 'LIKE';
                SELECT rb.article_id INTO notification_type_id FROM react_tb rb WHERE rb.article_id = NEW.article_id;
            ELSIF
                (TG_TABLE_NAME = 'comment_tb') THEN
                notification_type_value := 'COMMENT';
                SELECT cb.article_id INTO notification_type_id FROM comment_tb cb WHERE cb.article_id = NEW.article_id;
            ELSIF
                (TG_TABLE_NAME = 'report_tb') THEN
                notification_type_value := 'REPORT';
                SELECT rb.article_id INTO notification_type_id FROM report_tb rb WHERE rb.article_id = NEW.article_id;
            ELSIF
                (TG_TABLE_NAME = 'author_request_tb') THEN
                notification_type_value := 'USER_REQUEST_AS_AUTHOR';
                SELECT arb.user_id INTO notification_type_id FROM author_request_tb arb WHERE arb.user_id = NEW.user_id;
            ELSE
                -- Invalid table name, do nothing
                RETURN NULL;
            END IF;

            -- Insert the notification record
            INSERT INTO notification_tb (createAt, sender_id, sender_image_url, sender_name, receiver_id,
                                         notification_type, notification_type_id)
            VALUES (current_timestamp, NEW.user_id,
                    (select ub.photo_url from user_tb ub where ub.user_id = NEW.user_id),
                    (select ub.username from user_tb ub where ub.user_id = NEW.user_id), (select user_tb.user_id
                                                                                          from user_tb
                                                                                                   inner join user_role_tb on user_tb.user_id = user_role_tb.user_id
                                                                                                   inner join role_tb on role_tb.role_id = user_role_tb.role_id
                                                                                          where role_tb.name = 'ROLE_ADMIN'),
                    notification_type_value, notification_type_id);

            RETURN NEW;
        END;
    END IF;

    RETURN NULL;
END;
$$
    LANGUAGE plpgsql;

-- drop function insert_notification cascade;


-- Trigger for react_tb
CREATE TRIGGER react_notification_trigger
    AFTER INSERT
    ON react_tb
    FOR EACH ROW
EXECUTE FUNCTION insert_notification();
-- drop trigger react_notification_trigger on report_tb;

-- Trigger for comment_tb
CREATE TRIGGER comment_notification_trigger
    AFTER INSERT
    ON comment_tb
    FOR EACH ROW
EXECUTE FUNCTION insert_notification();
-- drop trigger comment_notification_trigger on comment_tb;

-- Trigger for report_tb
CREATE TRIGGER report_notification_trigger
    AFTER INSERT
    ON report_tb
    FOR EACH ROW
EXECUTE FUNCTION insert_notification();
-- drop trigger report_notification_trigger on report_tb;



-- create trigger for author_request_table;
CREATE TRIGGER author_request_tb_trigger
    AFTER INSERT
    ON author_request_tb
    FOR EACH ROW
EXECUTE FUNCTION insert_notification();
-- drop trigger author_request_tb_trigger on author_request_tb;

