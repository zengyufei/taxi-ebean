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
  community_id                  bigint comment '所属小区编号',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_building primary key (id)
) comment='楼栋';

create table t_card (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  community_id                  bigint comment '所属小区编号',
  card_no                       varchar(255) comment '卡号',
  card_enum                     integer comment '卡类型',
  user_id                       bigint comment '持卡人，与 realName 互斥',
  real_name                     varchar(255) comment '持卡人，与 userId 互斥',
  id_card                       varchar(255) comment '身份证，与 userId 互斥',
  phone                         varchar(255) comment '电话，与 userId 互斥',
  room_no                       varchar(255) comment '房间号，与 userId 互斥',
  valid_date                    datetime(6) comment '有效期',
  valid_time                    datetime(6) comment '有效时段',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint ck_t_card_card_enum check ( card_enum in (0,1)),
  constraint pk_t_card primary key (id)
) comment='刷卡开门的卡';

create table t_catch_photo (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  community_id                  bigint comment '所属小区编号',
  catch_photo_enum              integer comment '留影类型',
  device_id                     bigint comment '主叫设备',
  card_id                       bigint comment '房间/卡号',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint ck_t_catch_photo_catch_photo_enum check ( catch_photo_enum in (0,1,2,3,4,5,6)),
  constraint pk_t_catch_photo primary key (id)
) comment='留影记录';

create table t_community (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  name                          varchar(255) comment '小区名称',
  build_max                     integer comment '小区楼栋数',
  unit_max                      integer comment '每栋单元数',
  floor_max                     integer comment '每单元楼层最大值',
  room_max                      integer comment '每楼层房间数最大值',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_community primary key (id)
) comment='小区';

create table t_complain (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  user_id                       bigint comment '业主',
  content                       varchar(255) comment '内容',
  complain_enum                 integer comment '投诉类型',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint ck_t_complain_complain_enum check ( complain_enum in (0,1)),
  constraint pk_t_complain primary key (id)
) comment='投诉';

create table t_device (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  equ_no                        varchar(255) comment '设备号',
  name                          varchar(255) comment '设备名称',
  equ_enum                      integer comment '设备类型',
  ip                            varchar(255) comment 'Ip地址',
  mac                           varchar(255) comment 'Mac地址',
  password                      varchar(255) comment '密码',
  online_enum                   integer comment '在线情况',
  times                         bigint comment 'IOS开门延时',
  keen_numb                     integer comment '蓝牙开门灵敏度',
  community_id                  bigint comment '所属小区编号',
  building_id                   bigint comment '所属楼栋编号',
  unit_id                       bigint comment '所属单元编号',
  room_id                       bigint comment '所属房间编号，如果有',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint ck_t_device_equ_enum check ( equ_enum in (0,1,2,3)),
  constraint ck_t_device_online_enum check ( online_enum in (0,1)),
  constraint pk_t_device primary key (id)
) comment='设备';

create table t_open_door_log (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  community_id                  bigint comment '所属小区编号',
  device_id                     bigint comment '门口机',
  card_enum                     integer comment '类型',
  user_id                       bigint comment '用户',
  success_fail_enum             integer comment '结果',
  open_door_time                datetime(6) comment '开门时间',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint ck_t_open_door_log_card_enum check ( card_enum in (0,1)),
  constraint ck_t_open_door_log_success_fail_enum check ( success_fail_enum in (0,1)),
  constraint pk_t_open_door_log primary key (id)
) comment='开门记录';

create table t_repair (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  repair_enum                   integer comment '报修类型',
  repair_type                   varchar(255) comment '如报修类型不够，则字符串辅助',
  user_id                       bigint comment '报修人',
  promise_time                  datetime(6) comment '业主约定处理时间',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint ck_t_repair_repair_enum check ( repair_enum in (0,1,2,3,4,5,6,7,8,9)),
  constraint pk_t_repair primary key (id)
) comment='报修';

create table t_room (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  name                          varchar(255) comment '房间名称，罗马数字',
  alias                         varchar(255) comment '房间别名，中文名',
  unit_id                       bigint comment '所属单元编号',
  building_id                   bigint comment '所属楼栋编号',
  community_id                  bigint comment '所属小区编号',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_room primary key (id)
) comment='房间';

create table t_sms (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  content                       varchar(255) comment '短信内容',
  community_id                  bigint comment '所属小区编号',
  user_id                       bigint comment '所属用户，如果有',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_sms primary key (id)
) comment='短信下发';

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
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
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
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_sys_org primary key (id)
) comment='组织机构';

create table t_sys_resource (
  id                            bigint auto_increment not null,
  name                          varchar(255) comment '权限名称',
  level                         integer comment '菜单等级',
  permission                    varchar(255) comment '权限标识',
  parent_id                     bigint comment '父id',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  constraint pk_t_sys_resource primary key (id)
) comment='资源权限';

create table t_sys_role (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  role_name                     varchar(255) comment '角色名称',
  description                   varchar(255) comment '描述',
  org_no                        varchar(255) comment '组织机构编号',
  resource_list                 varchar(1000) comment '权限集合',
  parent_id                     bigint comment '父id',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_sys_role primary key (id)
) comment='角色';

create table t_unit (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  name                          varchar(255) comment '单元名称，罗马数字',
  alias                         varchar(255) comment '单元别名，中文名',
  building_id                   bigint comment '所属楼栋编号',
  community_id                  bigint comment '所属小区编号',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_unit primary key (id)
) comment='单元';

create table t_user (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  user_name                     varchar(255) comment '用户名',
  password                      varchar(255) comment '密码',
  phone                         varchar(255) comment '电话',
  phone_mac                     varchar(255) comment '手机 mac 地址',
  active_flag                   tinyint(1) default 0 comment '是否通过短信验证',
  real_name                     varchar(255) comment '姓名',
  id_card                       varchar(255) comment '身份证',
  id_card_img                   varchar(255) comment '身份证照片',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint pk_t_user primary key (id)
) comment='普通用户';

create table t_user_info (
  id                            bigint auto_increment not null,
  features                      longtext comment '额外字段',
  community_id                  bigint comment '所属小区编号',
  building_id                   bigint comment '所属楼栋编号',
  unit_id                       bigint comment '所属单元编号',
  room_no                       varchar(255) comment '已绑定房间号',
  user_id                       bigint comment '关联的用户编号',
  auth_flag                     tinyint(1) default 0 comment '认证',
  bind_call_flag                tinyint(1) default 0 comment '绑定电话呼叫',
  mark                          varchar(255) comment '详情',
  stay_enum                     integer comment '常住',
  auth_enum                     integer comment '状态',
  application_time              datetime(6) comment '申请时间',
  create_time                   timestamp DEFAULT CURRENT_TIMESTAMP not null comment '创建时间',
  update_time                   TIMESTAMP DEFAULT '1970-01-01 00:00:01.000000' ON UPDATE CURRENT_TIMESTAMP not null comment '修改时间',
  deleted                       BOOLEAN DEFAULT FALSE not null comment '数据状态',
  who_created                   varchar(255) not null comment '创建者',
  who_modified                  varchar(255) not null comment '修改者',
  constraint ck_t_user_info_stay_enum check ( stay_enum in (0,1)),
  constraint ck_t_user_info_auth_enum check ( auth_enum in (0,1,2)),
  constraint pk_t_user_info primary key (id)
) comment='住户信息';

