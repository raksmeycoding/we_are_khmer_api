select * from category where category_id = '2cfe7550-2311-4867-a406-a7caa85e37fb';

select * from article_tb where article_id = '2cfe7550-2311-4867-a406-a7caa85e37fb';


select count(*) from article_tb  as article_count where user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0';






update article_tb set user_id = 'd0e1bd19-d4bc-45d5-9beb-32538d16b769' where user_id = '1a816fc1-90cb-480a-93ed-6b5e21322bd0';



-- select coalesce((nullif(article_tb.image, '')), 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80' ) as image from article_tb where article_id = '3a6dfa5a-6c76-475a-808b-e26476fd8935';


update article_tb set image = 'https://images.unsplash.com/photo-1599283415392-c1ad8110a147?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1170&q=80';




select ab.user_id
from article_tb ab inner join react_tb rb on ab.article_id = 'e8912fd9-9673-46a3-9c50-5be67aadd137';


select article_tb.user_id from article_tb where article_tb.article_id = 'e8912fd9-9673-46a3-9c50-5be67aadd137';

select max(ub.user_id) from user_tb ub inner join article_tb a on ub.user_id = a.user_id where ub.user_id = 'd0e1bd19-d4bc-45d5-9beb-32538d16b769';