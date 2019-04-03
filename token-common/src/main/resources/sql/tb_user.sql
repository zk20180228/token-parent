/*
Navicat MySQL Data Transfer

Source Server         : taotao
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : token

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2019-04-01 16:50:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '密码，加密存储',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `created` datetime DEFAULT NULL,
  `updated` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('37', 'honry', '63a9f0ea7bb98050796b649e85481845', '13333xxx', '111xx@qq.com', '2018-07-09 14:01:27', '2018-07-09 14:02:25');
INSERT INTO `tb_user` VALUES ('38', '老七', '63a9f0ea7bb98050796b649e85481845', '13520118564', '1589022273@qq.com', '2018-07-16 14:25:36', '2018-07-16 14:41:12');
INSERT INTO `tb_user` VALUES ('39', '李四', '63a9f0ea7bb98050796b649e85481845', '13333432803', '13333432803@163.com', '2018-07-16 14:35:03', '2018-07-16 14:35:03');
