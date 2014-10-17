-- Copyright (c) 2013, 2014 Sacred Scripture Foundation.
-- "All scripture is given by inspiration of God, and is profitable for
-- doctrine, for reproof, for correction, for instruction in righteousness:
-- That the man of God may be perfect, throughly furnished unto all good
-- works." (2 Tim 3:16-17)
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

CREATE TABLE 
bible 
(
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created       DATETIME NOT NULL,
    updated       DATETIME,
    locale_lang   CHAR(2) NOT NULL,
    rtol          BIT NOT NULL,
    PRIMARY KEY (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE 
bible_loc 
(
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created       DATETIME NOT NULL,
    updated       DATETIME,
    bible_id      INT UNSIGNED NOT NULL,
    locale_lang   CHAR(2) NOT NULL,
    name          VARCHAR(50) NOT NULL,
    title         VARCHAR(50) NOT NULL,
    abbreviation  VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT bible_loc_ix01fk FOREIGN KEY (bible_id) REFERENCES bible (id),
    CONSTRAINT bible_loc_ix02uq UNIQUE (bible_id, locale_lang)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE 
book_type_group 
(
    id            INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created       DATETIME NOT NULL,
    updated       DATETIME,
    list_position INT UNSIGNED NOT NULL,
    parent_id     INT UNSIGNED,
    PRIMARY KEY (id),
    CONSTRAINT book_type_group_ix01fk FOREIGN KEY (parent_id) REFERENCES book_type_group (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
book_type_group_loc
(
    id                  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created             DATETIME NOT NULL,
    updated             DATETIME,
    book_type_group_id  INT UNSIGNED NOT NULL,
    locale_lang         CHAR(2) NOT NULL,
    name                VARCHAR(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT book_type_group_loc_ix01fk FOREIGN KEY (book_type_group_id) REFERENCES book_type_group (id),
    CONSTRAINT book_type_group_loc_ix02uq UNIQUE (book_type_group_id, locale_lang)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
book_type 
(
    id                  INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created             DATETIME NOT NULL,
    updated             DATETIME,
    type_code           CHAR(3) NOT NULL,
    book_type_group_id  INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT book_type_ix01uq UNIQUE (type_code)
    CONSTRAINT book_type_ix02fk FOREIGN KEY (book_type_group_id) REFERENCES book_type_group (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
book_type_loc
(
    id              INT UNSIGNED NOT NULL AUTO_INCREMENT,
    created         DATETIME NOT NULL,
    updated         DATETIME,
    book_type_id    INT UNSIGNED NOT NULL,
    locale_lang     CHAR(2) NOT NULL,
    name            VARCHAR(50) NOT NULL,
    title           VARCHAR(250) NOT NULL,
    abbreviation1   VARCHAR(10) NOT NULL,
    abbreviation2   VARCHAR(10),
    abbreviation3   VARCHAR(10),
    PRIMARY KEY (id),
    CONSTRAINT book_type_loc_ix01fk FOREIGN KEY (book_type_id) REFERENCES book_type (id),
    CONSTRAINT book_type_loc_ix02uq UNIQUE (book_type_id, locale_lang)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE 
book
(
    id                      INT UNSIGNED AUTO_INCREMENT,
    created                 DATETIME NOT NULL,
    updated                 DATETIME,
    bible_id                INT UNSIGNED NOT NULL,
    book_type_id            INT UNSIGNED NOT NULL,
    list_position           INT UNSIGNED NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT book_ix01fk FOREIGN KEY (bible_id) REFERENCES bible (id),
    CONSTRAINT book_ix02fk FOREIGN KEY (book_type_id) REFERENCES book_type (id),
    CONSTRAINT book_ix03uq UNIQUE (bible_id, book_type_id),
    CONSTRAINT book_ix04uq UNIQUE (bible_id, book_type_id, list_position)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
content_type
(
    id                  INT UNSIGNED AUTO_INCREMENT,
    created             DATETIME NOT NULL,
    updated             DATETIME,
    code                VARCHAR(10),
    PRIMARY KEY (id),
    CONSTRAINT content_type_ix01uq UNIQUE (code)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
content 
(
    id                    INT UNSIGNED AUTO_INCREMENT,
    created               DATETIME NOT NULL,
    updated               DATETIME,
    book_id               INT UNSIGNED NOT NULL,
    list_position         INT UNSIGNED NOT NULL,
    kind_code             INT(1) NOT NULL,
    content_type_id       INT UNSIGNED,
    chapter_name          VARCHAR(10),
    verse_chapter_id      INT UNSIGNED,
    verse_type_id         INT UNSIGNED,
    verse_name            VARCHAR(10),
    verse_alt_name        VARCHAR(10),
    verse_omit            BOOLEAN,
    PRIMARY KEY (id),
    CONSTRAINT content_ix01fk FOREIGN KEY (book_id) REFERENCES book (id),
    CONSTRAINT content_ix02uq UNIQUE (book_id, list_position),    
    CONSTRAINT content_ix03fk FOREIGN KEY (content_type_id) REFERENCES content_type (id),
    CONSTRAINT content_ix04fk FOREIGN KEY (verse_chapter_id) REFERENCES content (id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
content_verse_text (
    id                INT UNSIGNED AUTO_INCREMENT,
    created           DATETIME NOT NULL,
    updated           DATETIME,
    verse_content_id  INT UNSIGNED NOT NULL,
    txt               VARCHAR(2000) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT content_text_ix01fk FOREIGN KEY (verse_content_id) REFERENCES content (id),
    FULLTEXT content_text_ix02tx (txt)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;
