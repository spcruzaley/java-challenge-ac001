DROP TABLE IF EXISTS route;

create table route
(
   id integer auto_increment not null,
   idRouteGroup integer not null,
   source char(1) not null,
   target char(1) not null,
   distance integer not null,
   primary key(id)
);