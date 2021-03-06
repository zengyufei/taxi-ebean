insert into t_sys_member (id, features, account, password, real_name, identity, mobile, email, qq, remark, sex_enum, role_id, org_no, deleted, create_time, who_created, who_modified) values (1, "{}","admin","admin","管理员","","","","","",1,1,"sz-001",0,now(), "", "") ON DUPLICATE KEY UPDATE account='admin', real_name='管理员';

INSERT INTO t_sys_role (`id`, `role_name`, `description`, `org_no`, `resource_list`, `deleted`, `create_time`, who_created, who_modified) VALUES ('1', '管理员', '管理角色', 'sz-001', '[\"all\"]', '0', now(), "", "") ON DUPLICATE KEY UPDATE org_no='sz-001', role_name='管理员';

INSERT INTO t_sys_org (`id`, `features`, `parent_org_no`, `org_no`, `org_name`, `province`, `city`, `address`, `description`, `deleted`, `create_time`, who_created, who_modified) VALUES ('1', '{}', '-1', 'sz-001', '云哨智能', '44', '4401', '广仁大楼', '云哨', '0', now(), "", "") ON DUPLICATE KEY UPDATE parent_org_no='-1', org_no='sz-001', org_name='云哨智能';


INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('1', '系统管理', '1', 'sys:*', '-1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('2', '组织机构', '2', 'sys:sysOrg:*', '1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('3', '查看', '3', 'sys:sysOrg:query', '2');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('4', '添加', '3', 'sys:sysOrg:insert', '2');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('5', '修改', '3', 'sys:sysOrg:update', '2');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('6', '删除', '3', 'sys:sysOrg:delete', '2');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('7', '用户管理', '2', 'sys:sysMember:*', '1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('8', '查看', '3', 'sys:sysMember:query', '7');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('9', '添加', '3', 'sys:sysMember:insert', '7');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('10', '修改', '3', 'sys:sysMember:update', '7');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('11', '删除', '3', 'sys:sysMember:delete', '7');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('12', '角色管理', '2', 'sys:sysRole:*', '1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('13', '查看', '3', 'sys:sysRole:query', '12');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('14', '添加', '3', 'sys:sysRole:insert', '12');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('15', '修改', '3', 'sys:sysRole:update', '12');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('16', '删除', '3', 'sys:sysRole:delete', '12');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('17', '资源管理', '2', 'sys:sysResource:*', '1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('18', '查看', '3', 'sys:sysResource:query', '17');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('19', '添加', '3', 'sys:sysResource:insert', '17');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('20', '修改', '3', 'sys:sysResource:update', '17');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('21', '删除', '3', 'sys:sysResource:delete', '17');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('22', '小区管理', '1', 'community:*', '-1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('23', '小区信息管理', '2', 'community:community:*', '22');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('24', '查看', '3', 'community:community:query', '23');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('25', '添加', '3', 'community:community:insert', '23');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('26', '修改', '3', 'community:community:update', '23');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('27', '删除', '3', 'community:community:delete', '23');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('28', '楼栋信息管理', '2', 'community:building:*', '22');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('29', '查看', '3', 'community:building:query', '28');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('30', '添加', '3', 'community:building:insert', '28');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('31', '修改', '3', 'community:building:update', '28');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('32', '删除', '3', 'community:building:delete', '28');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('33', '单元信息管理', '2', 'community:unit:*', '22');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('34', '查看', '3', 'community:unit:query', '33');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('35', '添加', '3', 'community:unit:insert', '33');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('36', '修改', '3', 'community:unit:update', '33');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('37', '删除', '3', 'community:unit:delete', '33');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('38', '房间信息管理', '2', 'community:room:*', '22');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('39', '查看', '3', 'community:room:query', '38');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('40', '添加', '3', 'community:room:insert', '38');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('41', '修改', '3', 'community:room:update', '38');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('42', '删除', '3', 'community:room:delete', '38');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('43', '设备管理', '1', 'device:*', '-1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('44', '设备信息', '2', 'device:device:*', '43');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('45', '查看', '3', 'device:device:query', '44');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('46', '添加', '3', 'device:device:insert', '44');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('47', '修改', '3', 'device:device:update', '44');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('48', '删除', '3', 'device:device:delete', '44');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('49', '开门记录', '2', 'device:openDoorLog:*', '43');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('50', '查看', '3', 'device:openDoorLog:query', '49');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('51', '添加', '3', 'device:openDoorLog:insert', '49');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('52', '修改', '3', 'device:openDoorLog:update', '49');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('53', '删除', '3', 'device:openDoorLog:delete', '49');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('54', '留影记录', '2', 'device:catchPhoto:*', '43');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('55', '查看', '3', 'device:catchPhoto:query', '54');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('56', '添加', '3', 'device:catchPhoto:insert', '54');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('57', '修改', '3', 'device:catchPhoto:update', '54');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('58', '删除', '3', 'device:catchPhoto:delete', '54');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('59', '物理卡信息', '2', 'device:card:*', '43');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('60', '查看', '3', 'device:card:query', '59');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('61', '添加', '3', 'device:card:insert', '59');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('62', '修改', '3', 'device:card:update', '59');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('63', '删除', '3', 'device:card:delete', '59');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('64', '住户管理', '1', 'house:*', '-1');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('65', '住户信息', '2', 'house:userInfo:*', '64');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('66', '查看', '3', 'house:userInfo:query', '65');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('67', '添加', '3', 'house:userInfo:insert', '65');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('68', '修改', '3', 'house:userInfo:update', '65');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('69', '删除', '3', 'house:userInfo:delete', '65');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('70', '报修信息', '2', 'house:repair:*', '64');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('71', '查看', '3', 'house:repair:query', '70');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('72', '添加', '3', 'house:repair:insert', '70');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('73', '修改', '3', 'house:repair:update', '70');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('74', '删除', '3', 'house:repair:delete', '70');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('75', '投诉信息', '2', 'house:complain:*', '64');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('76', '查看', '3', 'house:complain:query', '75');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('77', '添加', '3', 'house:complain:insert', '75');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('78', '修改', '3', 'house:complain:update', '75');
INSERT INTO `t_sys_resource` (`id`, `name`, `level`, `permission`, `parent_id`) VALUES ('79', '删除', '3', 'house:complain:delete', '75');



INSERT INTO `t_community` (`id`, `name`, `build_max`, `unit_max`, `floor_max`, `room_max`, `deleted`, `who_created`, `who_modified`) VALUES ('1', '碧桂园', '2', '2', '3', '2', '0', 'admin', 'admin');
INSERT INTO `t_building` (`id`, `name`, `alias`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('1', '1 栋', '一栋', '1', '0', 'admin', 'admin');
INSERT INTO `t_building` (`id`, `name`, `alias`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('2', '2 栋', '二栋', '1', '0', 'admin', 'admin');
INSERT INTO `t_unit` (`id`, `name`, `alias`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('1', '1 单元', '一单元', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_unit` (`id`, `name`, `alias`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('2', '2 单元', '二单元', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_unit` (`id`, `name`, `alias`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('3', '1 单元', '一单元', '2', '1', '0', 'admin', 'admin');
INSERT INTO `t_unit` (`id`, `name`, `alias`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('4', '2 单元', '二单元', '2', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('1', '101 房', '一零一房', '1', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('2', '102 房', '一零二房', '1', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('3', '201 房', '二零一房', '1', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('4', '202 房', '二零二房', '1', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('5', '301 房', '三零一房', '1', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('6', '302 房', '三零二房', '1', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('7', '101 房', '一零一房', '2', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('8', '102 房', '一零二房', '2', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('9', '201 房', '二零一房', '2', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('10', '202 房', '二零二房', '2', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('11', '301 房', '三零一房', '2', '1', '1', '0', 'admin', 'admin');
INSERT INTO `t_room` (`id`, `name`, `alias`, `unit_id`, `building_id`, `community_id`, `deleted`, `who_created`, `who_modified`) VALUES ('12', '302 房', '三零二房', '2', '1', '1', '0', 'admin', 'admin');





INSERT INTO `t_user` (`id`, `user_name`, `password`, `phone`, `phone_mac`, `active_flag`, `real_name`, `id_card`, `deleted`, `who_created`, `who_modified`)
VALUES ('1', '13480555111', '13480555111', '13480555111', '00:00:00:00:00', '0', '测试住户 1', '441381199101122411', '0', 'admin', 'admin');
INSERT INTO `t_user_info` (`id`, `community_id`, `building_id`, `unit_id`, `room_id`, `auth_flag`, `bind_call_flag`, `mark`, `unit_ids`, `stay_enum`, `auth_enum`, `application_time`, `user_id`, `deleted`, `who_created`, `who_modified`)
VALUES ('1', '1', '1', '1', '1', '0', '0', 'test', NULL, '1', '1', '2017-10-12 15:21:01.000000', '1', '0', 'admin', 'admin');




INSERT INTO `t_complain` (`id`, `content`, `complain_enum`, `user_mark`, `user_info_id`, `deleted`, `who_created`, `who_modified`)
VALUES ('1', '修马桶', '1', '快点来', '1',  '0', 'admin', 'admin');
INSERT INTO `t_repair` (`id`, `repair_enum`, `repair_type`, `mark`, `user_info_id`, `promise_time`, `deleted`, `who_created`, `who_modified`)
VALUES ('1', '1', NULL, NULL, '1', '2017-10-12 15:42:09.000000', '0', 'admin', 'admin');

