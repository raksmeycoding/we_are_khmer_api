-- select author profile

select u.user_id, u.username, u.email, u.gender, u.data_of_birth from user_tb u inner join user_role_tb urb on urb.user_id = u.user_id inner join  role_tb r on urb.role_id = r.role_id where u.user_id = 'ea51e79f-962d-493d-bb82-c7cfc1125169' and r.name = 'ROLE_USER'