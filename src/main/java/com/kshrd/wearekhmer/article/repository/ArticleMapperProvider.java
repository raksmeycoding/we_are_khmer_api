package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.exception.ValidateException;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class ArticleMapperProvider {

    public static String getArticleByTitle(String title) {
        return """
                select * from article_tb
                """;
    }

    public static String filterArticles(String title, Date publishDate, String categoryId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.article_id, a.user_id, a.category_id, a.title ");
        sql.append("FROM article_tb a ");
        sql.append("WHERE 1=1 ");

        if (title != null && !title.isEmpty()) {
            sql.append("AND a.title =  \'" + title + "\'");
        }

        if (publishDate != null) {
            sql.append("AND a.publish_date >= \'" + publishDate + "\' ");
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append("AND a.category_id = #{categoryId} ");
        }
        System.out.println(sql.toString());

        return sql.toString();
    }

    public static String filterArticles2(FilterArticleCriteria filter) {
        if(!filter.getView().equals("most-view")) {
            throw new ValidateException("Your query by view, the value of view must be (most-view), not (" + filter.getView() + ").", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
        }
        String title = (String) filter.getTitle();
        java.sql.Date publishDate = (java.sql.Date) filter.getPublishDate();
        Date startDate = (Date) filter.getStartDate();
        Date endDate = (Date) filter.getEndDate();
        String categoryId = (String) filter.getCategoryId();
        String view = (String) filter.getView();

        SQL scriptSql = null;
        scriptSql = new SQL() {{
            SELECT("a.article_id, a.title, a.sub_title, a.publish_date, a.description, a.updatedat as updateat, a.image, a.count_view, a.isban, a.hero_card_in, a.user_id, a.category_id, ub.photo_url, ub.username as author_name, c.category_name, (select count(*) from react_tb r where r.article_id = a.article_id) as react_count");
            FROM("article_tb a");
            INNER_JOIN("user_tb ub on ub.user_id = a.user_id");
            INNER_JOIN("category c on c.category_id = a.category_id");
            WHERE(" 1=1");
            WHERE("a.isban = false");

            if (title != null && !title.isEmpty()) {
                WHERE("a.title LIKE CONCAT('%', #{title}, '%')");
            }

            if (publishDate != null) {
                WHERE("DATE(a.publish_date) = #{publishDate}");
            }

            if (categoryId != null && !categoryId.isEmpty()) {
                WHERE("a.category_id = #{categoryId}");
            }

            if (startDate != null) {
                if (endDate == null) {
                    throw new ValidateException("endDate must be provided", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
                WHERE("DATE(a.publish_date) >= #{startDate} AND DATE(a.publish_date) <= #{endDate}");
            } else {
                if (endDate != null) {
                    throw new ValidateException("startDate haven't provided yet.", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
            }


            if (view != null && !view.isEmpty() && view.equals("most-view")) {
                System.out.println("most-view: " + view);
                ORDER_BY("a.count_view desc");
            }

        }};
        System.out.println(scriptSql);
        return scriptSql.toString();
    }
}


//public class ArticleMapperProvider {
//    public static String filterArticles(Map<String, Object> parameters) {
//        return new SQL() {{
//            SELECT("a.article_id, a.user_id, a.category_id, a.title, a.sub_title, a.publish_date, a.description, a.updatedat, a.image, a.count_view, a.isban, a.hero_card_in, u.photo_url, u.author_name, c.category_name, r.react_count");
//            FROM("article_tb a");
//            JOIN("user_tb u ON a.user_id = u.user_id");
//            JOIN("category_tb c ON a.category_id = c.category_id");
//            JOIN("react_tb r ON a.article_id = r.article_id");
//            WHERE("");
//
//            if (parameters.containsKey("title")) {
//                String title = (String) parameters.get("title");
//                if (title != null && !title.isEmpty()) {
//                    WHERE("a.title LIKE CONCAT('%', #{title}, '%')");
//                }
//            }
//
//            if (parameters.containsKey("publishDate")) {
//                LocalDate publishDate = (LocalDate) parameters.get("publishDate");
//                if (publishDate != null) {
//                    WHERE("a.publish_date = #{publishDate}");
//                }
//            }
//
//            if (parameters.containsKey("categoryId")) {
//                String categoryId = (String) parameters.get("categoryId");
//                if (categoryId != null && !categoryId.isEmpty()) {
//                    WHERE("a.category_id = #{categoryId}");
//                }
//            }
//        }}.toString();
//    }
//}

