create table users (
id serial primary key,
username character varying(300),
name character varying(300),
password  character varying(100),
created_dt timestamp without time zone DEFAULT now()
);

alter table users add avtar integer default 1 ;
alter table users add pic1 integer default 1 ;
alter table users add pic2 integer default 1 ;
alter table users add pic3 integer default 1 ;
alter table users add friends text[];
alter table users add pic3 oid ;
alter table users add rememberme character varying(5) default 'false';
alter table users add dob timestamp  without time zone ;
alter table users add pob text ;
alter table users add address text ;
alter table users add gender character varying(6);
alter table users add pendingrequest text[][];



create table post (
id serial primary key,
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT now(),
sessionid character varying(300),
post text
);

alter table post add imageid integer REFERENCES images(id);
alter table post add likeby text[][];
alter table post drop column fdatetime;
alter table post add COLUMN fdatetime timestamp without time zone DEFAULT DATE_TRUNC('second', NOW());




create table comment (
id serial primary key,
postid integer REFERENCES post(id),
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT now(),
comment text
);

alter table comment add likeby text[][];
alter table comment drop column fdatetime;
alter table comment add COLUMN fdatetime timestamp without time zone DEFAULT DATE_TRUNC('second', NOW());


create table share (
id serial primary key,
sharebyid integer REFERENCES users(id),
sharebyname character varying(300),
sharepostid integer REFERENCES post(id),
sharefromid integer REFERENCES users(id),
sharefromname character varying(300),
sharetime timestamp without time zone DEFAULT DATE_TRUNC('second', NOW())
);


--create table likes (
---postid integer REFERENCES post(id),
---ispost character varying(5) default 'true',
---likeby   text[][]
--);
--select * from likes;
--insert into likes(postid,likeby) values(3,'{mohit bhindwal}');
--update likes set likeby = likeby || '{rohit bhindwal}' 

--insert into likes(postid,likeby) values(3,'{"mohit bhindwal","sham"}');
--update likes set likeby = likeby || '{"mohitji bhindwal","shamji"}' 
--update users set friends = '{{2},{3}}' where id = 1 ;

CREATE TABLE images(id serial primary key , imgoid oid , imgname character varying(100), imgpath character varying(200)); 

drop table comment;
drop table users;
drop table post;

insert into users(username,name,password) values('m@gmail.com','mohit bhindwal','mb'); 

