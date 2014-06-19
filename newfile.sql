select  *  from news order by news_id desc;
delete from news;




drop table news_img;
drop table news;



create table news(
news_id int primary key auto_increment
,title varchar(255) not null
,content text		 not null
,sourcedate timestamp 
,collectdate timestamp  not null
,news_from varchar(20)  not null
,sourceUrl VARCHAR(2083)  not null
,status int default 0 not null
)character set=utf8;

create table news_img(
news_id int not null
,imgPath varchar(255) not null
,primary key(news_id,imgPath)
,foreign key(news_id) references news(news_id)
)character set=utf8;

select * from news_img;
delete from news_img;
delete from news;
