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

create table comment (
id serial primary key,
postid integer REFERENCES post(id),
username character varying(300),
userid integer REFERENCES users(id),
fdatetime timestamp without time zone DEFAULT now(),
comment text
);



drop table comment;
drop table users;
drop table post;

insert into users(username,name,password) values('m@gmail.com','mohit bhindwal','mb'); 