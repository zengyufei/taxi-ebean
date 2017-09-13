create table t_sys_member (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  account                       varchar(255),
  password                      varchar(255),
  real_name                     varchar(255),
  identity                      varchar(255),
  mobile                        varchar(255),
  email                         varchar(255),
  qq                            varchar(255),
  remark                        varchar(255),
  sex                           integer,
  role_id                       bigint,
  org_no                        varchar(255),
  flag                          tinyint(1) default 0 not null comment '数据状态',
  create_time                   datetime not null not null comment '创建时间',
  update_time                   datetime not null not null comment '修改时间',
  constraint ck_t_sys_member_sex check ( sex in (0,1,2)),
  constraint pk_t_sys_member primary key (id)
);

create table t_sys_org (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  org_no                        varchar(255) comment '组织机构',
  parent_org_no                 varchar(255) comment '上级组织机构',
  org_name                      varchar(255) comment '组织机构名称',
  province                      integer comment '省份',
  city                          integer comment '城市',
  address                       varchar(255) comment '地址',
  description                   varchar(255) comment '描述',
  flag                          tinyint(1) default 0 not null comment '数据状态',
  create_time                   datetime not null not null comment '创建时间',
  update_time                   datetime not null not null comment '修改时间',
  constraint pk_t_sys_org primary key (id)
);

create table t_sys_role (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  role_name                     varchar(255) comment '角色名称',
  description                   varchar(255) comment '描述',
  org_no                        varchar(255) comment '组织机构编号',
  resource_list                 varchar(255) comment '权限集合',
  flag                          tinyint(1) default 0 not null comment '数据状态',
  create_time                   datetime not null not null comment '创建时间',
  update_time                   datetime not null not null comment '修改时间',
  constraint pk_t_sys_role primary key (id)
);

