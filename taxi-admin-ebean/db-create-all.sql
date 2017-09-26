create table t_area (
  id                            bigint auto_increment not null,
  code                          integer comment '编号',
  name                          varchar(255) comment '名称',
  parent_code                   integer comment '上级编号',
  parent_name                   varchar(255) comment '上级名称',
  full_name                     varchar(255) comment '全称',
  constraint pk_t_area primary key (id)
) comment='省市区域';

create table t_building (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  name                          varchar(255) comment '楼栋名称，罗马数字',
  alias                         varchar(255) comment '楼栋别名，中文名',
  community_id                  bigint comment '小区',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_building primary key (id)
) comment='楼栋';

create table t_community (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  name                          varchar(255) comment '小区名称',
  build_max                     integer comment '小区楼栋数',
  unit_max                      integer comment '每栋单元数',
  floor_max                     integer comment '每单元楼层最大值',
  room_max                      integer comment '每楼层房间数最大值',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_community primary key (id)
) comment='小区';

create table t_sys_member (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  account                       varchar(255) comment '账号',
  password                      varchar(255) comment '密码',
  real_name                     varchar(255) comment '真实姓名',
  identity                      varchar(255) comment '身份证',
  mobile                        varchar(255) comment '手机号',
  email                         varchar(255) comment '邮箱',
  qq                            varchar(255) comment 'QQ',
  remark                        varchar(255) comment '备注',
  sex_enum                      integer comment '性别',
  role_id                       bigint comment '角色 id',
  org_no                        varchar(255) comment '组织机构编号',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint ck_t_sys_member_sex_enum check ( sex_enum in (0,1,2)),
  constraint pk_t_sys_member primary key (id)
) comment='后台用户';

create table t_sys_org (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  parent_org_no                 varchar(255) comment '上级组织机构',
  org_no                        varchar(255) comment '组织机构',
  org_name                      varchar(255) comment '组织机构名称',
  province                      integer comment '省份',
  city                          integer comment '城市',
  address                       varchar(255) comment '地址',
  description                   varchar(255) comment '描述',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_sys_org primary key (id)
) comment='组织机构';

create table t_sys_resource (
  id                            bigint auto_increment not null,
  parent_id                     bigint comment '父id',
  name                          varchar(255) comment '权限名称',
  level                         integer comment '菜单等级',
  permission                    varchar(255) comment '权限标识',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_sys_resource primary key (id)
) comment='资源权限';

create table t_sys_role (
  id                            bigint auto_increment not null,
  parent_id                     bigint comment '父id',
  role_name                     varchar(255) comment '角色名称',
  description                   varchar(255) comment '描述',
  org_no                        varchar(255) comment '组织机构编号',
  resource_list                 varchar(1000) comment '权限集合',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_sys_role primary key (id)
) comment='角色';

create table t_unit (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  name                          varchar(255) comment '单元名称，罗马数字',
  alias                         varchar(255) comment '单元别名，中文名',
  building_id                   bigint comment '楼栋',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  create_time                   datetime DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   datetime DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_unit primary key (id)
) comment='单元';

