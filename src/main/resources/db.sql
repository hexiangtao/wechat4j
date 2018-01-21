/*
Navicat MySQL Data Transfer

Source Server         : localhsot_master_3306
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : webwx

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-12-24 23:24:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for accountConfig
-- ----------------------------
DROP TABLE IF EXISTS `accountConfig`;
CREATE TABLE `accountConfig` (
  `id` 			int(11)		 	NOT NULL 				AUTO_INCREMENT,
  `mobile` 		varchar(50) 	NOT NULL				DEFAULT '',
  `name` 		varchar(255) 	NOT NULL				DEFAULT NULL,
  `val` 		varchar(255)  	NOT NULL				DEFAULT NULL,
  `refreshDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` 			bigint(20) 		NOT NULL 	AUTO_INCREMENT,
  `mobile` 		varchar(30) 	NOT NULL 	DEFAULT '',
  `content` 	text ,
  `type` 		tinyint(3) 					DEFAULT '0',
  `remark` 		varchar(255)  				DEFAULT '',
  `createDate` 	datetime 					DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;




-- ----------------------------
-- Table structure for userAccount
-- ----------------------------
DROP TABLE IF EXISTS `userAccount`;
CREATE TABLE `userAccount` (
  `id` 			bigint(20) 			NOT NULL 	AUTO_INCREMENT		COMMENT '主键id',
  `mobile` 		varchar(30) 		NOT NULL 	DEFAULT '' 			COMMENT '登陆帐号',
  `pwd` 		varchar(255) 		NOT NULL 	DEFAULT ''			COMMENT '登陆密码',
  `email` 		varchar(50)  		NOT NULL	DEFAULT				COMMENT	'邮箱',
  `emailpwd` 	varchar(255) 		NOT NULL 	DEFAULT ''			COMMENT '邮箱密码',
  `status` 		tinyint(3) 			NOT NULL 	DEFAULT '0' 		COMMENT '状态',
  `type` 		tinyint(3) 			NOT NULL 	DEFAULT '0' 		COMMENT '0:试用帐号,1:VIP用户，2：终生帐户',
  `loginData` 	text,
  `createDate` 	datetime 						DEFAULT NULL,
  `refreshDate` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8_bin COLLATE=utf8_bin;
