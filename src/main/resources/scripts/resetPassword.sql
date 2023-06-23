
CREATE TABLE reset_password_tb(
reset_password_id  varchar primary key default uuid_generate_v4(),
token     varchar,
email varchar,
is_verified boolean default false
                    );




CREATE OR REPLACE FUNCTION reset_password_trigger_function()
    RETURNS TRIGGER AS $$
BEGIN
    DELETE FROM public.reset_password_tb
    WHERE email = NEW.email;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER reset_password_trigger
    BEFORE INSERT ON public.reset_password_tb
    FOR EACH ROW
EXECUTE FUNCTION reset_password_trigger_function();
