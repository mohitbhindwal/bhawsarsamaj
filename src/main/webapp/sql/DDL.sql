create table users (
id serial primary key,
username character varying(300),
name character varying(300),
password  character varying(100),
created_dt timestamp without time zone DEFAULT now(),
avtar integer default 1 ,
pic1 integer default 1 ,
pic2 integer default 1 ,
pic3 integer default 1 ,
friends text[],
rememberme character varying(5) default 'false',
dob timestamp  without time zone ,
pob text ,
address text ,
gender character varying(6),
pendingrequest text[][]
);


CREATE TABLE images(id serial primary key , imgoid oid , imgname character varying(100), imgpath character varying(200)); 


create table post (
id serial primary key,
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT DATE_TRUNC('second', NOW()),
sessionid character varying(300),
post text,
imageid integer REFERENCES images(id),
likeby text[][],
url text
);

create table comment (
id serial primary key,
postid integer REFERENCES post(id),
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT DATE_TRUNC('second', NOW()),
comment text ,
likeby text[][]
 );
 

create table friendrequest(
id serial primary key,
requestsentbyid integer REFERENCES users(id),
requestsentbyname character varying(300),
requestsenttoid integer REFERENCES users(id),
requestsenttoname character varying(300),
status character varying(30),
createtime timestamp without time zone DEFAULT DATE_TRUNC('second', NOW()),
UNIQUE (requestsentbyid,requestsenttoid)
);

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



drop table comment;
drop table users;
drop table post;

insert into users(username,name,password) values('m@gmail.com','mohit bhindwal','mb'); 

--insert into  images(imgoid,imgname,imgpath) values (  lo_import('E:\mohit\bhawsarsamaj\src\main\webapp\img\default_user.png'),'default_user.png','E:\mohit\bhawsarsamaj\src\main\webapp\img\default_user.png')    ;