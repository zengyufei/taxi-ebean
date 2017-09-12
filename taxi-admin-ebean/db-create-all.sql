create table t_sys_org (
  id                            bigint auto_increment not null,
  org_no                        varchar(255),
  parent_org_no                 varchar(255),
  org_name                      varchar(255),
  province                      integer,
  city                          integer,
  address                       varchar(255),
  description                   varchar(255),
  flag                          tinyint(1) default 0 not null,
  create_time                   time not null,
  update_time                   time not null,
  constraint pk_t_sys_org primary key (id)
);

