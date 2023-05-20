select ab.article_id,
       ab.user_id,
       ab.category_id,
       ab.title,
       ab.sub_title,
       ab.publish_date,
       ab.description,
       ab.updatedat,
       ab.image,
       ab.count_view,
       ab.isban,
       ab.hero_card_in,
       ub.username as author_name,
       c.category_name
from article_tb ab inner join user_tb ub on ab.user_id = ub.user_id inner join category c on c.category_id = ab.category_id
where ab.user_id = '5b1ed971-3338-4a65-be91-4979c0bbd427';