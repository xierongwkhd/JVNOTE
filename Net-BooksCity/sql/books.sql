/*
SQLyog Ultimate v8.32 
MySQL - 5.5.36 : Database - bookstore
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore`;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `bid` char(32) NOT NULL,
  `bname` varchar(100) DEFAULT NULL,
  `price` decimal(5,1) DEFAULT NULL,
  `author` varchar(20) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `cid` char(32) DEFAULT NULL,
  `del` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`bid`),
  KEY `cid` (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `book` */

insert  into `book`(`bid`,`bname`,`price`,`author`,`image`,`cid`,`del`) values ('1','Java编程思想（第4版）','75.6','qdmmy6','book_img/9317290-1_l.jpg','1',0),('2','Java核心技术卷1','68.5','qdmmy6','book_img/20285763-1_l.jpg','1',0),('3','Java就业培训教程','39.9','张孝祥','book_img/8758723-1_l.jpg','1',0),('4','Head First java','47.5','（美）塞若','book_img/9265169-1_l.jpg','1',0),('5','JavaWeb开发详解','83.3','孙鑫','book_img/22788412-1_l.jpg','2',0),('6','Struts2深入详解','63.2','孙鑫','book_img/20385925-1_l.jpg','2',0),('7','精通Hibernate','30.0','孙卫琴','book_img/8991366-1_l.jpg','2',0),('8','精通Spring2.x','63.2','陈华雄','book_img/20029394-1_l.jpg','2',0),('9','Javascript权威指南','93.6','（美）弗兰纳根','book_img/22722790-1_l.jpg','3',0),('B6AC480F0B074B28A582C9C8AF7E6CDC','测试','123.4','moke','book_img/D9C923372B4749E59C4C75C10B6C5807_8758723-1_l.jpg','2',1),('B769AFE4BEEA4476B2E6E7DCBDCEECDF','测试3','123.0','moke2','book_img/C561FCCBF9724FB9BF9E02F4F47A2CCE_8758723-1_l.jpg','782ED15DE57F4E838661820008FE77D0',0);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cid` char(32) NOT NULL,
  `cname` varchar(100) NOT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`cid`,`cname`) values ('1','JavaSE'),('2','JavaEE'),('3','Javascript'),('782ED15DE57F4E838661820008FE77D0','Java框架');

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `iid` char(32) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `oid` char(32) DEFAULT NULL,
  `bid` char(32) DEFAULT NULL,
  PRIMARY KEY (`iid`),
  KEY `oid` (`oid`),
  KEY `bid` (`bid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

insert  into `orderitem`(`iid`,`count`,`subtotal`,`oid`,`bid`) values ('D3106E9D2B4A416D826D3F1912028676',2,'126.40','42CBF46FD46F4429A611CBA2B2081B5D','6'),('311DB37A396E41A4A15CA1D2C0AC7924',2,'151.20','42CBF46FD46F4429A611CBA2B2081B5D','1'),('E77163FB4EBE42FDA94244327809261B',2,'95.00','031E407F6EEE4196B241450AD971E9FE','4'),('92E591FF297443FA8F312D8110CD9A40',1,'39.90','031E407F6EEE4196B241450AD971E9FE','3'),('771FD6D696E44C26943846A70412D521',1,'93.60','031E407F6EEE4196B241450AD971E9FE','9'),('6DE920700F984D55A9D5D5D7E5E1CF9F',3,'90.00','031E407F6EEE4196B241450AD971E9FE','7');

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `oid` char(32) NOT NULL,
  `ordertime` datetime DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  `state` smallint(1) DEFAULT NULL,
  `uid` char(32) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `uid` (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `orders` */

insert  into `orders`(`oid`,`ordertime`,`total`,`state`,`uid`,`address`) values ('42CBF46FD46F4429A611CBA2B2081B5D','2019-02-03 13:38:48','277.60',4,'140AD538598F4D348BDDCCAC1641CC33',NULL),('031E407F6EEE4196B241450AD971E9FE','2019-02-03 13:45:36','318.50',1,'140AD538598F4D348BDDCCAC1641CC33',NULL);

/*Table structure for table `tb_user` */

DROP TABLE IF EXISTS `tb_user`;

CREATE TABLE `tb_user` (
  `uid` char(32) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `code` char(64) NOT NULL,
  `state` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `tb_user` */

insert  into `tb_user`(`uid`,`username`,`password`,`email`,`code`,`state`) values ('140AD538598F4D348BDDCCAC1641CC33','moke123','123456','1916986844@qq.com','5C0089036DF54084BC7A128CD87545787A70A31FFA114298846507A661EA6239',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
