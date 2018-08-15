-- ----------------------------
-- create table user
-- ----------------------------
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `tel` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `wechat_number` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `active` bit(1) NOT NULL default true,
  `deleted` bit(1) NOT NULL DEFAULT false,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


create unique index UK_user_email on user (email);
create unique index UK_user_username on user (username);
ALTER TABLE `user` ADD UNIQUE (`tel`);
ALTER TABLE `user` ADD INDEX index_user_wechat_number (`wechat_number`);

-- 初始化超级管理员，密码：master12345
insert into user (id, email, tel, username, name, password, wechat_number, active, deleted, created_date) value
(1, "master@web-demo.com", "18888888888", "管理员", "administrator", "$2a$10$4FqxJAVU.hI9D2QO0SlZs.q5aBY6cagc.X8ckXmmT5oQQzXJRI9gy", "", true, false, now()),
(2, "normal@web-demo.com", "18888888887", "王小五", "王小五", "$2a$10$4FqxJAVU.hI9D2QO0SlZs.q5aBY6cagc.X8ckXmmT5oQQzXJRI9gy", "", true, false, now());


-- ----------------------------
-- create table role
-- ----------------------------
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `active` bit(1) NOT NULL DEFAULT true,
  `description` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `role` ADD UNIQUE (`name`);

insert into role (id, name, active, description) values
(1, "超级管理员", true, "超级管理员。"),
(2, "人事经理", true, "人事部门所有事务总负责人。"),
(3, "人事招聘主管", true, "主管招聘，新人入职及培训等相关事宜。"),
(4, "人事行政主管", true, "主管公司人事接待，企业文化等相关工作。");



-- ----------------------------
-- create table user_role
-- ----------------------------
CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

alter table user_role add unique (`user_id`, `role_id`);
ALTER TABLE `user_role` ADD INDEX index_userrole_userid (`user_id`);

-- userId = 1 为超管，
-- userId = 2 为普通用户，有2号角色
insert into user_role (user_id, role_id) values (1,1), (2, 2);


-- ----------------------------
-- create table user_role
-- ----------------------------
CREATE TABLE `role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `role_permission` ADD INDEX index_rolepermission_roleid (`role_id`);

--    USER_CREATE("新建人员", ScopeEnum.USER),
--    USER_READ("查看人员信息", ScopeEnum.USER),
--    USER_DELETE("注销人员", ScopeEnum.USER),
--    USER_DEPT_CRUD("部门管理", ScopeEnum.USER),
--    USER_ROLE_CRUD("角色管理", ScopeEnum.USER),
--    REVIEW_CRUD("审核流程管理", ScopeEnum.USER),
--   ARTICLE_CRUD("文稿管理", ScopeEnum.ARTICLE);
insert into role_permission (role_id, permission) values (2, "USER_CRUD"), (2, "USER_ROLE_CRUD"), (3, "ARTICLE_CRUD"),  (3, "USER_READ"), (3, "USER_DEPT_CRUD");

