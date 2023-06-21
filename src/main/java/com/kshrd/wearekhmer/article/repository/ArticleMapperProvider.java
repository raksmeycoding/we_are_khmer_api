package com.kshrd.wearekhmer.article.repository;

import com.kshrd.wearekhmer.exception.ValidateException;
import lombok.*;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        String title = (String) filter.getTitle();
        String publishDate = (String) filter.getPublishDate();
        String startDate = (String) filter.getStartDate();
        String endDate = (String) filter.getEndDate();
        String categoryId = (String) filter.getCategoryId();
        String view = (String) filter.getView();
        String day = (String) filter.getDay();
        String userId = (String) filter.getUserId();
        Integer page = (Integer) filter.getPage();


        SQL scriptSql = null;
        scriptSql = new SQL() {{
//            SELECT("a.article_id, a.title, a.sub_title, a.publish_date, a.description, a.updatedat as updateat, a.image, a.count_view, a.isban, a.hero_card_in, a.user_id, a.category_id, ub.photo_url, ub.username as author_name, c.category_name, (select count(*) from react_tb r where r.article_id = a.article_id) as react_count");
//            FROM("article_tb a");
//            INNER_JOIN("user_tb ub on ub.user_id = a.user_id");
//            INNER_JOIN("category c on c.category_id = a.category_id");
            SELECT("a.article_id, a.title, a.sub_title, a.publish_date, a.updatedat as updateat, a.image, a.count_view, a.isban, a.hero_card_in, a.user_id, a.category_id, ub.photo_url, ub.username as author_name, c.category_name, (select count(*) from react_tb r where r.article_id = a.article_id) as react_count, (CASE WHEN b.user_id = #{userId} THEN true ELSE false END) AS bookmarked, (CASE WHEN r.status = true THEN true ELSE false END) AS reacted");
            FROM("article_tb a");
            INNER_JOIN("user_tb ub on ub.user_id = a.user_id");
            INNER_JOIN("category c on c.category_id = a.category_id");
            LEFT_OUTER_JOIN("bookmark_tb b on b.article_id = a.article_id AND b.user_id = #{userId}");
            LEFT_OUTER_JOIN("react_tb r on r.article_id = a.article_id AND r.user_id = #{userId}");
            WHERE(" 1=1");
            WHERE("a.isban = false");
            if (page != null) {
                OFFSET((page - 1) * 10);
                LIMIT(10);
            }

            if (title != null && !title.isEmpty()) {
                WHERE("a.title LIKE CONCAT('%', #{title}, '%')");
            }

            if (publishDate != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.setLenient(false);
                try {
                    java.util.Date javaPublishDate = dateFormat.parse(publishDate);
                    java.sql.Date sqlPublishDate = new java.sql.Date(javaPublishDate.getTime());
                    System.out.println(sqlPublishDate);
                    WHERE("DATE(a.publish_date) = \'" + sqlPublishDate + "\'");
                } catch (ParseException e) {
                    throw new ValidateException("Invalid publishDate format. It must be in the format 'yyyy-MM-dd'.", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }

            }

            if (categoryId != null && !categoryId.isEmpty()) {
                WHERE("a.category_id = #{categoryId}");
            }

            if (startDate != null) {
                if (endDate == null) {
                    throw new ValidateException("endDate must be provided", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormat.setLenient(false);
                try {
                    java.util.Date javaStartDate = simpleDateFormat.parse(startDate);
                    java.util.Date javaEndDate = simpleDateFormat.parse(endDate);

                    java.sql.Date sqlStartDate = new java.sql.Date(javaStartDate.getTime());
                    java.sql.Date sqlEndDate = new java.sql.Date(javaEndDate.getTime());
                    WHERE("DATE(a.publish_date) >= \'" + sqlStartDate + "\' AND DATE(a.publish_date) <= \'" + sqlEndDate + "\' ");
                } catch (ParseException e) {
                    throw new ValidateException("Invalid startDate and endDate format. It must be in the format 'yyyy-MM-dd'.", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }

            } else {
                if (endDate != null) {
                    throw new ValidateException("startDate haven't provided yet.", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
            }

            // Filter articles by yesterday's date
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DAY_OF_MONTH, -1);
            java.sql.Date yesterdayDate = new java.sql.Date(yesterday.getTime().getTime());
            if (day != null && !day.isEmpty()) {
                if (!day.equals("yesterday")) {
                    throw new ValidateException("Filter by day is incorrect. your query must be: ...filter?day=yesterday", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
                WHERE("DATE(a.publish_date) = " + "\'" + yesterdayDate + "\'");
            }


            if (view != null && !view.isEmpty()) {
                if (!view.equals("most-view")) {
                    throw new ValidateException("Filter by view is incorrect. your query must be: ...filter?view=most-view", HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
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

