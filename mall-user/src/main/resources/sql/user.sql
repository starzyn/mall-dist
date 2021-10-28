/*
 Navicat Premium Data Transfer

 Source Server         : 104
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : 39.102.83.104:23306
 Source Schema         : mall-user

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 26/10/2021 16:26:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `invite_code` varchar(255) DEFAULT NULL COMMENT '邀请码',
  `vip_level` varchar(255) DEFAULT NULL COMMENT '特权等级',
  PRIMARY KEY (`id`),
  KEY `phone` (`phone_number`),
  KEY `name` (`username`,`nickname`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
