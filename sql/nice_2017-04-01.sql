# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.15)
# Database: nice
# Generation Time: 2017-04-01 13:54:04 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_acode
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_acode`;

CREATE TABLE `t_acode` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL DEFAULT '' COMMENT '激活码',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名',
  `type` varchar(50) DEFAULT NULL COMMENT '类型',
  `used` tinyint(1) DEFAULT '0' COMMENT '是否使用',
  `created` int(10) NOT NULL COMMENT '创建时间',
  `expired` int(10) NOT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_acode` WRITE;
/*!40000 ALTER TABLE `t_acode` DISABLE KEYS */;

INSERT INTO `t_acode` (`id`, `code`, `username`, `type`, `used`, `created`, `expired`)
VALUES
	(2,'1unv8u9ob0hovrv1eimlr4kdrv','biezhi','signup',1,1487074570,0),
	(3,'777a0aaeaeh7lq150vkdk264pv','biezhi','signup',1,1487075351,0),
	(4,'3eber1ruqoj64oobs2uo04vqcl','biezhii','signup',1,1487089805,0),
	(5,'s9hql7eu02hpmq2ve5a8fusnvm','irose','signup',1,1487097430,0);

/*!40000 ALTER TABLE `t_acode` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_comment
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '主题id',
  `username` varchar(64) CHARACTER SET utf8 NOT NULL DEFAULT '' COMMENT '评论人',
  `content` varchar(5000) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '评论内容',
  `created` int(10) NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

LOCK TABLES `t_comment` WRITE;
/*!40000 ALTER TABLE `t_comment` DISABLE KEYS */;

INSERT INTO `t_comment` (`id`, `tid`, `username`, `content`, `created`)
VALUES
	(5,'eb52bb56ca04407093984be4d4c70891','biezhi','nice',1487077177);

/*!40000 ALTER TABLE `t_comment` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_follow
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_follow`;

CREATE TABLE `t_follow` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `me` varchar(64) NOT NULL DEFAULT '' COMMENT '我的用户名',
  `following` varchar(64) NOT NULL DEFAULT '' COMMENT '我关注的人',
  `created` int(10) DEFAULT NULL COMMENT '关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_stars
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_stars`;

CREATE TABLE `t_stars` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `tid` varchar(64) NOT NULL DEFAULT '' COMMENT '主题id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '点赞用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u_idx_username_tid` (`tid`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_stars` WRITE;
/*!40000 ALTER TABLE `t_stars` DISABLE KEYS */;

INSERT INTO `t_stars` (`id`, `tid`, `username`)
VALUES
	(30,'368186cbb2a54955bbf8cada6f5c940f','irose'),
	(33,'511fe542965541c5b8d962ee024adc15','irose'),
	(34,'559cad3ef35b416ca5fd27f9fe55edc8','irose'),
	(45,'a49b8596f3954231a426148f08593836','biezhi'),
	(31,'b0f0987436224b39a841ed818e696fb2','irose'),
	(36,'b13da1501d7149b48af735e0e9a6a597','irose'),
	(28,'eb52bb56ca04407093984be4d4c70891','biezhi'),
	(32,'f953d91a19da4fc5822f0bef293d44f7','irose');

/*!40000 ALTER TABLE `t_stars` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_tags
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_tags`;

CREATE TABLE `t_tags` (
  `id` varchar(64) NOT NULL,
  `name` varchar(50) NOT NULL DEFAULT '' COMMENT '标签名',
  `tid` varchar(64) DEFAULT NULL,
  `state` tinyint(4) NOT NULL COMMENT '1:正常 0:删除',
  `created` int(10) DEFAULT NULL COMMENT '标签创建时间',
  `updated` int(10) DEFAULT NULL COMMENT '最后更新的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table t_topic
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_topic`;

CREATE TABLE `t_topic` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '发布人',
  `title` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '标题',
  `content` varchar(255) NOT NULL DEFAULT '' COMMENT '图片',
  `stars` int(10) DEFAULT NULL COMMENT '点赞数',
  `comments` int(10) DEFAULT NULL COMMENT '评论数',
  `created` int(10) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_topic` WRITE;
/*!40000 ALTER TABLE `t_topic` DISABLE KEYS */;

INSERT INTO `t_topic` (`id`, `username`, `title`, `content`, `stars`, `comments`, `created`)
VALUES
	('0dde5d8b80814a02b0d03dca2a8a5812','biezhi',NULL,'topic/biezhi/5op86viqc4iiapm5jdclhb5rvf.jpg',0,0,1487095497),
	('22c869dfe5784739af14b717aeff8eef','irose',NULL,'topic/irose/7ek81qjlbminrrbf831uehhnfv.jpg',0,0,1487097693),
	('2ca397f121b64ee489b2decb3ec4bcc6','irose',NULL,'topic/irose/3242n23b6shvdrs31s2vutm9qh.jpg',0,0,1487097957),
	('368186cbb2a54955bbf8cada6f5c940f','irose',NULL,'topic/irose/v93ga5usi6h4rrdq504hqk7bhn.jpg',1,0,1487097996),
	('511fe542965541c5b8d962ee024adc15','biezhii',NULL,'topic/biezhii/vd118qleqog79phqcc6i0pc7b8.jpg',1,0,1487097404),
	('513b32df55a04ff3b30875ef8a0ce038','biezhii',NULL,'topic/biezhii/s1j1u4l5magnpr1drkphl5o8v0.jpg',0,0,1487097273),
	('559cad3ef35b416ca5fd27f9fe55edc8','biezhii',NULL,'topic/biezhii/6nja04kuoejfhqr4per2buqomj.jpg',1,0,1487097853),
	('58fc10153ae949af999ace6f2105408e','biezhi',NULL,'topic/biezhi/5akcas05u0gdqpef6nsuupagd2.png',0,0,1487095481),
	('5aa7667f77d04520896a40bf590b81a0','irose',NULL,'topic/irose/4u1o5cjdkcivfosf1jebct7eq7.jpg',0,0,1487097553),
	('69a39325e99e44abb1feb18c9bfd141b','irose',NULL,'topic/irose/3bfdi4aquggikq2chq5trt4fvo.jpg',0,0,1487097585),
	('82b51817ac3f4c81a794f2732eba4a84','biezhi',NULL,'topic/biezhi/vihdj6j8o4ik6p05amgb5p6vqd.png',0,0,1487095643),
	('869390733fb644d9bdd261b4d8dffd1e','biezhi',NULL,'topic/biezhi/39v32vuiu2jtkol9lhh5am57bd.jpg',0,0,1487095520),
	('93260a07d1834c6098e4a9eb1b91eec2','biezhi',NULL,'topic/biezhi/6vtl06rcs6gefojj3he7svo65r.jpg',0,0,1487095591),
	('a49b8596f3954231a426148f08593836','biezhi',NULL,'topic/biezhi/vh1b5l9a8ajubrr1v7dh9elube.png',1,0,1487179571),
	('b0f0987436224b39a841ed818e696fb2','biezhii',NULL,'topic/biezhii/3c0rtj29ruhpdq1v3jnjvoro4i.jpg',1,0,1487097891),
	('b13da1501d7149b48af735e0e9a6a597','biezhii',NULL,'topic/biezhii/6og2vfo1h6gqip5f8d44n300m2.jpg',1,0,1487097351),
	('b45934c4a6ca498ea98c42c802548f42','biezhii',NULL,'topic/biezhii/qq8s7k2qtqi4fob2o9fklrkt11.jpg',0,0,1487096102),
	('bfd488d665fe40d494563cf08679b304','biezhi',NULL,'topic/biezhi/oh65rn2h38imdo9qdetplksjpr.png',0,0,1487095468),
	('d004f2912f9b4d04ba867f0fb2924851','biezhi',NULL,'topic/biezhi/0puk35itiegp7qhf97nl173rdt.jpg',0,0,1487095509),
	('d2d9a53158fc4e77badb0cdcc2642580','biezhi',NULL,'topic/biezhi/sd6k50mb2shu0o1l57gg5rmq2d.jpg',0,0,1487095561),
	('eb52bb56ca04407093984be4d4c70891','biezhi',NULL,'topic/biezhi/6rlvponvvoji0qie37dq1ehj1t.png',1,1,1487075517),
	('f953d91a19da4fc5822f0bef293d44f7','irose',NULL,'topic/irose/1v1217pqjohc3ptuno6ds40f6e.jpg',1,0,1487097729);

/*!40000 ALTER TABLE `t_topic` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `username` varchar(30) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(30) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '昵称',
  `email` varchar(100) NOT NULL DEFAULT '' COMMENT '邮箱',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像',
  `signature` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '个性签名',
  `followers` int(10) DEFAULT '0' COMMENT '粉丝数',
  `following` int(10) DEFAULT '0' COMMENT '关注人数',
  `topics` int(10) DEFAULT '0' COMMENT '发帖数',
  `privated` tinyint(1) DEFAULT '0' COMMENT '是否是私密账户,关注可见',
  `state` tinyint(4) DEFAULT '1' COMMENT '1:正常 0:禁用 2:未激活',
  `created` int(10) NOT NULL COMMENT '创建时间',
  `logined` int(10) DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`username`, `password`, `nickname`, `email`, `avatar`, `signature`, `followers`, `following`, `topics`, `privated`, `state`, `created`, `logined`)
VALUES
	('biezhi','26b2010cd2028d742b283da54a304584','biezhi','921293209@qq.com','avatar/users/biezhi.jpg?t_1487083386458','hhehee111',0,0,10,0,1,1487075351,NULL),
	('biezhii','f3beb755b5fb35a216dc5de7173c00c0','biezhii','i@biezhi.me','avatar/users/biezhii.jpeg?t_1487095715972',NULL,0,0,6,0,1,1487089805,NULL),
	('irose','3b5c8903adbb971b63664166dd335c8e','irose','rose@qq.com','avatar/random/6.png',NULL,0,0,6,0,1,1487097430,NULL);

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
