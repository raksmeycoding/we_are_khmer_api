package com.kshrd.wearekhmer.userRating;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
public class Rating {

    private String rating_Id;
    private String user_id;
    private String author_id;

    private Timestamp create_at;
    private Timestamp update_at;
    private Integer number_of_rating;
}
