create table test_sys_member (
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
  org_no                        bigint,
  org_name                      varchar(255),
  flag                          tinyint(1) default 0 not null,
  create_time                   time not null,
  update_time                   time not null,
  constraint pk_test_sys_member primary key (id)
);

