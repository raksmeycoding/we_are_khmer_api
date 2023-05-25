-- create or
create or replace function upload_image_to_specific_table_User_tb(imageName varchar, userId varchar) returns text as
$$
declare
    return_message text;
begin
    update user_tb set photo_url = imageName where user_tb.user_id = userId;
    return 'Update image success';
end;
$$ language plpgsql;

select *
from upload_image_to_specific_table_User_tb('sddffsdfs', '1a816fc1-90cb-480a-93ed-6b5e21322bd0');



-- create or
create or replace function upload_image_to_specific_table_article_tb(imageName varchar, articleId varchar) returns text as
$$
declare
    return_message text;
begin
    update article_tb set image = imageName where article_tb.article_id = articleId;
    return 'Update image success';
end;
$$ language plpgsql;


select *
from upload_image_to_specific_table_article_tb('my aritlce image', '8a401096-06b7-4d76-b942-76d80bf17828')



-- upload to category tb
-- create or
create or replace function upload_image_to_specific_table_category_tb(imageName varchar, categoryId varchar) returns text as
$$
declare
    return_message text;
begin
    update category set category_image = imageName where category.category_id = categoryId;
    return 'Update image success';
end;
$$ language plpgsql;


select *
from upload_image_to_specific_table_category_tb('my aritlce image', '514da5f4-4ca3-4d5d-89ef-42635dfa9d23');


-- test
-- select *, concat('http://localhost:8080/api/v1/files/file/filename?name=', user_tb.photo_url) as photo_url
-- from user_tb;


DELETE FROM article_tb WHERE article_id = '8a401096-06b7-4d76-b942-76d80bf1782' and user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0' returning *

select *
from article_tb where article_id = '8a401096-06b7-4d76-b942-76d80bf1782';
