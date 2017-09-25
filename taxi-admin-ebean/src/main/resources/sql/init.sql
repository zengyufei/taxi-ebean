insert into t_sys_member (id, features, account, password, real_name, identity, mobile, email, qq, remark, sex, role_id, org_no, deleted, create_time, who_created, who_modified) values (1, "{}","admin","admin","管理员","","","","","",1,1,"sz-001",0,now(), "", "") ON DUPLICATE KEY UPDATE account='admin', real_name='管理员';

INSERT INTO t_sys_role (`id`, `role_name`, `description`, `org_no`, `resource_list`, `deleted`, `create_time`, who_created, who_modified) VALUES ('1', '管理员', '管理角色', 'sz-001', '[\"all\"]', '0', now(), "", "") ON DUPLICATE KEY UPDATE org_no='sz-001', role_name='管理员';

INSERT INTO t_sys_org (`id`, `features`, `parent_org_no`, `org_no`, `org_name`, `province`, `city`, `address`, `description`, `deleted`, `create_time`, who_created, who_modified) VALUES ('1', '{}', '-1', 'sz-001', '云哨智能', '44', '4401', '广仁大楼', '云哨', '0', now(), "", "") ON DUPLICATE KEY UPDATE parent_org_no='-1', org_no='sz-001', org_name='云哨智能';

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (1, -1, '系统管理', 1, 'sys:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (2, 1, '组织机构', 2, 'sys:sysOrg:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (3, 2, '查看', 3, 'sys:sysOrg:query');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (4, 2, '添加', 3, 'sys:sysOrg:insert');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (5, 2, '修改', 3, 'sys:sysOrg:update');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (6, 2, '删除', 3, 'sys:sysOrg:delete');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (7, 1, '用户管理', 2, 'sys:sysMember:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (8, 7, '查看', 3, 'sys:sysMember:query');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (9, 7, '添加', 3, 'sys:sysMember:insert');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (10, 7, '修改', 3, 'sys:sysMember:update');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (11, 7, '删除', 3, 'sys:sysMember:delete');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (12, 1, '角色管理', 2, 'sys:sysRole:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (13, 12, '查看', 3, 'sys:sysRole:query');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (14, 12, '添加', 3, 'sys:sysRole:insert');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (15, 12, '修改', 3, 'sys:sysRole:update');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (16, 12, '删除', 3, 'sys:sysRole:delete');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (17, 1, '资源管理', 2, 'sys:sysResource:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (18, 17, '查看', 3, 'sys:sysResource:query');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (19, 17, '添加', 3, 'sys:sysResource:insert');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (20, 17, '修改', 3, 'sys:sysResource:update');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (21, 17, '删除', 3, 'sys:sysResource:delete');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (22, -1, '小区管理', 1, 'community:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (23, 22, '小区信息管理', 2, 'community:community:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (24, 22, '查看', 3, 'community:community:query');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (25, 22, '添加', 3, 'community:community:insert');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (26, 22, '修改', 3, 'community:community:update');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (27, 22, '删除', 3, 'community:community:delete');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (28, 22, '楼栋信息管理', 2, 'community:building:*');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (29, 28, '查看', 3, 'community:building:query');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (30, 28, '添加', 3, 'community:building:insert');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (31, 28, '修改', 3, 'community:building:update');

INSERT IGNORE INTO `t_sys_resource` (`id`,  `parent_id`, `name`, `level`, `permission`) VALUES (32, 28, '删除', 3, 'community:building:delete');