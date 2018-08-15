
-- ----------------------------
-- create table oauth2_client
-- ----------------------------
CREATE TABLE `oauth2_client` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(255) NOT NULL,
  `access_token_validity_seconds` int(10) DEFAULT NULL,
  `refresh_token_validity_seconds` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 初始化oauth2_client，创建web-demo的client_id
insert into oauth2_client (id, client_id, access_token_validity_seconds, refresh_token_validity_seconds)
values (1, "web-demo", 3600, 7200);

-- ----------------------------
-- create table oauth2_client
-- ----------------------------
CREATE TABLE `oauth2_client_grant_type` (
  `oauth2_client_id` bigint(20) NOT NULL,
  `grant_type` varchar(255) NOT NULL,
  PRIMARY KEY (oauth2_client_id, grant_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 初始化grant_type，web-demo类型为password, refresh_token。
insert into oauth2_client_grant_type (oauth2_client_id, grant_type)
values (1, "password"), (1, "refresh_token");



