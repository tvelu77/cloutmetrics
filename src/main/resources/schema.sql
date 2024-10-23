create table git
(
   git_id BIGINT not null auto_increment,
   git_name varchar(255) not null,
   git_path varchar(255),
   git_date time,
   primary key(git_id)
);

create table contributor
(
   id BIGINT not null auto_increment,
   name varchar(255) not null,
   email varchar(255),
   primary key(id)
);

create table metric
(
   metric_id BIGINT not null auto_increment,
   git_id BIGINT not null auto_increment,
   languages_ratio varchar(255),
   number_commits varchar(255),
   primary key(metric_id)
);

