create table history_tb
(
    history_id varchar primary key default uuid_generate_v4() unique,
    user_id    varchar references user_tb (user_id) ON DELETE CASCADE       not null,
    article_id varchar references article_tb (article_id) ON DELETE CASCADE not null,
    created_at timestamp default current_timestamp
);

-- Create the trigger function
CREATE OR REPLACE FUNCTION delete_history()
    RETURNS TRIGGER AS $$
BEGIN
    -- Delete history records for the current user after 2 minutes
    DELETE FROM history_tb
    WHERE user_id = NEW.user_id
      AND created_at < current_timestamp - interval '2 minutes';
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

-- Create the trigger
CREATE TRIGGER delete_history_trigger
    AFTER INSERT ON history_tb
    FOR EACH ROW
EXECUTE FUNCTION delete_history();
