-- create function that only 3 the foreign key number can exists
-- Create the trigger function
CREATE OR REPLACE FUNCTION limit_user_working_experience()
    RETURNS TRIGGER AS $$
    DECLARE
user_count INTEGER;
BEGIN
        -- Count the number of records with the same user_id
SELECT COUNT(*) INTO user_count
FROM public.working_experience_tb
WHERE user_id = NEW.user_id;

-- If the count is greater than or equal to 3, raise an exception
IF user_count >= 3 THEN
            RAISE EXCEPTION 'The user already has 3 working experiences in the table.';
END IF;

        -- If the count is less than 3, allow the insert
RETURN NEW;
END;
$$ LANGUAGE plpgsql;


-- drop function limit_user_working_experience cascade ;
--
-- Create the trigger
CREATE TRIGGER limit_user_id
    BEFORE INSERT ON public.working_experience_tb
    FOR EACH ROW
    EXECUTE FUNCTION limit_user_working_experience();



select count(*) from working_experience_tb where user_id = 'afec3af8-f178-4430-b33d-1022b3025f2c';

drop function validate_user_working_experience cascade ;