package com.kshrd.wearekhmer.article.repository;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

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
            sql.append("AND a.publish_date >= \'" + publishDate +   "\' ");
        }

        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append("AND a.category_id = #{categoryId} ");
        }
        System.out.println(sql.toString());

        return sql.toString();
    }

    public static String filterArticles2(FilterArticleCriteria filter) {
        String title = (String) filter.getTitle();
        Date publishDate = (Date) filter.getPublishDate();
        Date startDate = (Date) filter.getStartDate();
        Date endDate = (Date) filter.getEndDate();
        String categoryId = (String) filter.getCategoryId();
        return new SQL() {{
            SELECT("a.article_id, a.user_id, a.category_id, a.title, a.sub_title, a.description, publish_date");
            FROM("article_tb a");

            if (title != null && !title.isEmpty()) {
                WHERE("a.title LIKE CONCAT('%', #{title}, '%')");
            }

            if (publishDate != null) {
                WHERE("DATE(a.publish_date) = #{publishDate}");
            }

            if (categoryId != null && !categoryId.isEmpty()) {
                WHERE("a.category_id = #{categoryId}");
            }

        }}.toString();
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

