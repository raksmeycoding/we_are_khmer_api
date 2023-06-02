create table navbar_tb
(
    navbar_id    varchar primary key default uuid_generate_v4(),
    category_id  varchar references category (category_id) on delete cascade not null unique ,
    real_name    varchar                                                     not null ,
    navbar_name  varchar                                                     not null  ,
    order_number integer                                                     not null unique
);

-- drop table navbar_tb;


-- first record
INSERT INTO navbar_tb (navbar_id, real_name, navbar_name, category_id, order_number)
SELECT uuid_generate_v4(), category_name, 'Navbar Name Value', '4671d16e-d470-4b0d-8989-551306143866', 1
FROM category
WHERE category_id = '4671d16e-d470-4b0d-8989-551306143866';

update navbar_tb
set order_number = '3'
where navbar_id = '67e1199e-29ac-4d70-88f6-0cadccebd210';


-- second record
INSERT INTO navbar_tb (navbar_id, real_name, navbar_name, category_id, order_number)
SELECT uuid_generate_v4(), category_name, 'Khmer New Year', '97c6d328-be4d-4c36-93fc-d96a4af4893c', 2
FROM category
WHERE category_id = '97c6d328-be4d-4c36-93fc-d96a4af4893c';



select *
from navbar_tb;



-- create trigger function, automatically insert to navbar_tb with the latest or the unique number and
create or replace function auto_insert_into_navbar_after_category_tb_inserted() returns trigger as
$$
begin
    if tg_op = 'INSERT' then
        insert into navbar_tb (navbar_id, category_id, real_name, navbar_name, order_number)
        select uuid_generate_v4(),
               NEW.category_id,
               NEW.category_name,
               NEW.category_name,
               (select coalesce(MAX(order_number) + 1, 1)  from navbar_tb)
        from category where category.category_id = NEW.category_id;
        return NEW;
    end if;
    return null;
end;

$$
    language plpgsql;

-- drop function auto_insert_into_navbar_after_category_tb_inserted cascade ;

create trigger trg_auto_insert_into_navbar_after_category_tb_inserted
    AFTER insert
    on category
    for each row
execute function auto_insert_into_navbar_after_category_tb_inserted();

-- drop trigger trg_auto_insert_into_navbar_after_category_tb_inserted on category CASCADE ;


select * from navbar_tb order by order_number;
select coalesce(max(order_number) + 1, 1) from navbar_tb;


-- update navbar if exist
update navbar_tb set navbar_name = 'Khmer brashat', order_number = 5 where category_id = '67a5da27-71b6-4cca-a33f-38a156008263';
select * from navbar_tb;



