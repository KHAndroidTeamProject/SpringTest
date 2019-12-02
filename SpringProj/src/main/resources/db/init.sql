create schema musicdb;

create table singer (
                        ID SERIAL PRIMARY KEY,
                        FIRST_NAME VARCHAR(60) NOT NULL,
                        LAST_NAME VARCHAR(40) NOT NULL,
                        BIRTH_DATE date,
                        CONSTRAINT UQ_SINGER_1 UNIQUE(FIRST_NAME, LAST_NAME)
);

create table album (
                       ID SERIAL PRIMARY KEY,
                       SINGER_ID INT NOT NULL,
                       TITLE VARCHAR(100) NOT NULL,
                       RELEASE_DATE date,
                       CONSTRAINT UQ_SINGER_ALBUM_1 UNIQUE(SINGER_ID, TITLE),
                       CONSTRAINT FK_ALBUM FOREIGN KEY(SINGER_ID) REFERENCES singer(ID)
);

select * from singer;
select * from album;

drop table album;
drop table singer;