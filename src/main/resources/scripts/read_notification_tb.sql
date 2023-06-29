CREATE TABLE read_notification_tb(
    read_id varchar primary key default uuid_generate_v4(),
    createAt timestamp           default current_timestamp,
    notification_id          varchar not null references notification_tb (notification_id) on delete cascade,
    status         boolean             default false
);

CREATE OR REPLACE FUNCTION insert_read_notification()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        -- Insert into read_notification_tb
        INSERT INTO read_notification_tb (notification_id)
        VALUES (NEW.notification_id);

        RETURN NEW;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER trigger_insert_read_notification
    AFTER INSERT ON notification_tb
    FOR EACH ROW
EXECUTE FUNCTION insert_read_notification();


