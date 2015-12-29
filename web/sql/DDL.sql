create table post (
id serial primary key,
username character varying(100),
fdatetime timestamp without time zone DEFAULT now(),
sessionid character varying(300),
post text
);

create table comment (
id serial primary key,
postid integer REFERENCES post(id),
username character varying(100),
fdatetime timestamp without time zone DEFAULT now(),
comment text
);

create table profile (
id serial primary key,
username character varying(200),
password  character varying(100),
created_dt timestamp without time zone DEFAULT now()
);

