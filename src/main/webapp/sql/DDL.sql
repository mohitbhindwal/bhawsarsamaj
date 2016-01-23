create table users (
id serial primary key,
username character varying(300),
name character varying(300),
password  character varying(100),
created_dt timestamp without time zone DEFAULT now()
);


create table post (
id serial primary key,
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT now(),
sessionid character varying(300),
post text
);

alter table post add imageid integer REFERENCES images(id);


create table comment (
id serial primary key,
postid integer REFERENCES post(id),
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT now(),
comment text
);

create table likes (
id serial primary key,
postid integer REFERENCES post(id),
likeby   text[][]
);
--select * from likes;
--insert into likes(postid,likeby) values(3,'{mohit bhindwal}');
--update likes set likeby = likeby || '{rohit bhindwal}' 

--insert into likes(postid,likeby) values(3,'{"mohit bhindwal","sham"}');
--update likes set likeby = likeby || '{"mohitji bhindwal","shamji"}' 

CREATE TABLE images(id serial primary key , imgoid oid , imgname character varying(100), imgpath character varying(200)); 

drop table comment;
drop table users;
drop table post;

insert into users(username,name,password) values('m@gmail.com','mohit bhindwal','mb'); 