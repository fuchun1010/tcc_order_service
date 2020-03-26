```sql
use accountCenter;

create table if not exists tab_account(
	id  int primary key auto_increment,
    name varchar(20) not null,
    money int default 100000 not null
);

create table if not  exists tab_account_tmp(
   id   int primary key,
   xid  varchar(20) not null,
   app  varchar(20) not null,
   tmpMoney int default 0 not null,
   createDateTime datetime default now(),
   accountId int  not null
);

insert into tab_account(name,money) values('lisi',10000);


```


```sql

use scoreCenter;


create table if not exists tab_score(
	id  int primary key,
    name varchar(20) not null,
    score int default 0 not null
);

create table if not  exists tab_score_tmp(
   id   int primary key,
   xid  varchar(20) not null,
   app  varchar(20) not null,
   tmpScore int default 0 not null,
   createDateTime datetime default now()
);

```
