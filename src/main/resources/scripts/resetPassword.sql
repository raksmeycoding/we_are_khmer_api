
CREATE TABLE reset_password_tb(
reset_password_id  varchar primary key default uuid_generate_v4(),
token     varchar,
email varchar,
is_verified boolean default false
                    )