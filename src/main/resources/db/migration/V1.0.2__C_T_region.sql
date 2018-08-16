-- ----------------------------
-- create table user
-- ----------------------------
CREATE TABLE `region` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `province_id` bigint(20)  DEFAULT NULL,
  `province_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `city_id` bigint(20)  DEFAULT NULL,
  `city_name` varchar(255) CHARACTER SET utf8 NOT NULL,
  `area_id` bigint(20)  DEFAULT NULL,
  `area_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `street_id` bigint(20)  DEFAULT NULL,
  `street_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `deleted` bit(1) NOT NULL DEFAULT false,
  `created_date` datetime DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

