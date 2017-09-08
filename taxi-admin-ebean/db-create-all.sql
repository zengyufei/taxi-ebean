create table t_sys_member (
  id                            bigint auto_increment not null,
  account                       varchar(255),
  password                      varchar(255),
  real_name                     varchar(255),
  identity                      varchar(255),
  mobile                        varchar(255),
  email                         varchar(255),
  qq                            varchar(255),
  remark                        varchar(255),
  role_id                       bigint,
  role_name                     varchar(255),
  org_no                        varchar(255),
  org_name                      varchar(255),
  flag                          tinyint(1) default 0 not null,
  create_time                   time not null,
  update_time                   time not null,
  constraint pk_t_sys_member primary key (id)
);

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

create table t_sys_role (
  id                            bigint auto_increment not null,
  role_name                     varchar(255),
  description                   varchar(255),
  org_no                        varchar(255),
  flag                          tinyint(1) default 0 not null,
  create_time                   time not null,
  update_time                   time not null,
  constraint pk_t_sys_role primary key (id)
);

