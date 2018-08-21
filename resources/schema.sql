DROP TABLE IF EXISTS town;
DROP TABLE IF EXISTS route;

create table town
(
   id integer auto_increment not null,
   label varchar(1) not null,
   primary key(id)
);

create table route
(
   id integer auto_increment not null,
   source varchar(1) not null,
   target varchar(1) not null,
   distance integer not null,
   primary key(id)
);