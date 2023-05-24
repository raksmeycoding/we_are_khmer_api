-- update user request as author to be author
select update_tables_author_request_tb_and_user_tb(true, '34535646436');

-- reject user as author to be author as reject
select update_tables_author_request_tb_and_user_tb(false, '32456465764');


CREATE OR REPLACE FUNCTION insert_or_update_user_request_as_author(
    user_id_param varchar,
    author_name_param varchar,
    reason_param varchar
)
    RETURNS TABLE (
                      author_request_id varchar,
                      user_id varchar,
                      author_request_name varchar,
                      is_author_accepted boolean,
                      createat timestamp,
                      reason varchar
                  )
AS $$
BEGIN
    -- Check if user_id exists in author_request_tb
    IF EXISTS(SELECT 1 FROM author_request_tb WHERE author_request_tb.user_id = user_id_param) THEN
        RETURN QUERY UPDATE author_request_tb
            SET author_request_name = author_name_param,
                reason = reason_param
            WHERE author_request_tb.user_id = user_id_param
            RETURNING *;
    ELSE
        RETURN QUERY INSERT INTO author_request_tb (user_id, author_request_name, reason)
            VALUES (user_id_param, author_name_param, reason_param)
            RETURNING *;
    END IF;
END;
$$ LANGUAGE plpgsql;



select * from insert_or_update_user_request_as_author('dcc1bfec-6c61-4936-bf90-26fc6a25246a', 'raksmey koung', 'i 3 want to be author')