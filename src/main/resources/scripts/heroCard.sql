CREATE TABLE hero_card_tb
(
    hero_card_id VARCHAR PRIMARY KEY DEFAULT uuid_generate_v4(),
    index        INTEGER                                                                        NOT NULL,
    category_id  VARCHAR REFERENCES category (category_id) ON UPDATE CASCADE ON DELETE CASCADE  NOT NULL,
    article_id   VARCHAR REFERENCES article_tb (article_id) ON UPDATE CASCADE ON DELETE CASCADE NOT NULL,
    type         heroType                                                                       NOT NULL,
    CONSTRAINT index_range_check CHECK (index BETWEEN 1 AND 3)
);

CREATE TYPE heroType AS ENUM ('category', 'home');





