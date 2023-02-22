/*
SQLyog Trial v13.1.8 (64 bit)
MySQL - 5.7.19 : Database - ysh_mall_tiny
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ysh_mall_tiny` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ysh_mall_tiny`;

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 分类id主键',
  `category_name` varchar(32) NOT NULL COMMENT '分类名称 分类名称',
  `category_level` int(11) NOT NULL COMMENT '分类层级 分类得类型，\n1:一级大分类\n2:二级分类\n3:三级小分类',
  `parent_id` int(11) NOT NULL COMMENT '父层级id 父id 上一级依赖的id，1级分类则为0，二级三级分别依赖上一级',
  `category_icon` varchar(64) DEFAULT NULL COMMENT '图标 logo',
  `category_slogan` varchar(64) DEFAULT NULL COMMENT '口号',
  `category_pic` varchar(64) DEFAULT NULL COMMENT '分类图',
  `category_bg_color` varchar(32) DEFAULT NULL COMMENT '背景颜色',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品分类';

/*Data for the table `category` */

insert  into `category`(`category_id`,`category_name`,`category_level`,`parent_id`,`category_icon`,`category_slogan`,`category_pic`,`category_bg_color`) values 
(1,'点心/蛋糕',1,0,'cake.png','每一道甜品都有一个故事','act1.png','black'),
(2,'饼干/膨化',1,0,'cookies.png','健康饮食远离我','act2.png','black'),
(3,'熟食/肉类',1,0,'meat.png','nice！nice！nice！','act3.png','black'),
(4,'素食/卤味',1,0,'bamboo.png',NULL,'act1.png','black'),
(5,'坚果/炒货',1,0,'nut.png','酥酥脆脆，回味无穷','act2.png','black'),
(6,'糖果/蜜饯',1,0,'candy.png',NULL,'act3.png','black'),
(7,'巧克力',1,0,'chocolate.png',NULL,'act1.png','black'),
(8,'海味/河鲜',1,0,'fish.png',NULL,'act2.png','black'),
(9,'花茶/果茶',1,0,'tea.png','健康快乐每一天','act3.png','black'),
(10,'品牌/礼包',1,0,'package.png','每日一个小惊喜','act1.png','black'),
(11,'蛋糕',2,1,NULL,NULL,NULL,NULL),
(12,'点心',2,1,NULL,NULL,NULL,NULL),
(13,'饼干',2,2,NULL,NULL,NULL,NULL),
(14,'薯片',2,2,NULL,NULL,NULL,NULL),
(15,'虾条',2,2,NULL,NULL,NULL,NULL),
(16,'猪肉脯',2,3,NULL,NULL,NULL,NULL),
(17,'牛肉丝',2,3,NULL,NULL,NULL,NULL),
(18,'小香肠',2,3,NULL,NULL,NULL,NULL),
(19,'豆干',2,4,NULL,NULL,NULL,NULL),
(20,'干笋',2,4,NULL,NULL,NULL,NULL),
(21,'鸭脖',2,4,NULL,NULL,NULL,NULL),
(22,'坚果',2,5,NULL,NULL,NULL,NULL),
(23,'锅巴',2,5,NULL,NULL,NULL,NULL),
(24,'糖果',2,6,NULL,NULL,NULL,NULL),
(25,'蜜饯',2,6,NULL,NULL,NULL,NULL),
(26,'巧克力',2,7,NULL,NULL,NULL,NULL),
(27,'果冻',2,7,NULL,NULL,NULL,NULL),
(28,'海带丝',2,8,NULL,NULL,NULL,NULL),
(29,'小鱼干',2,8,NULL,NULL,NULL,NULL),
(30,'鱿鱼丝',2,8,NULL,NULL,NULL,NULL),
(31,'花茶',2,9,NULL,NULL,NULL,NULL),
(32,'果茶',2,9,NULL,NULL,NULL,NULL),
(33,'品牌套餐',2,10,NULL,NULL,NULL,NULL),
(34,'活动礼包',2,10,NULL,NULL,NULL,NULL),
(35,'蒸蛋糕',3,11,NULL,NULL,NULL,NULL),
(36,'脱水蛋糕',3,11,NULL,NULL,NULL,NULL),
(37,'瑞士卷',3,11,NULL,NULL,NULL,NULL),
(38,'软面包',3,11,NULL,NULL,NULL,NULL),
(39,'马卡龙',3,11,NULL,NULL,NULL,NULL),
(40,'千层饼',3,11,NULL,NULL,NULL,NULL),
(41,'甜甜圈',3,11,NULL,NULL,NULL,NULL),
(42,'蒸三明治',3,11,NULL,NULL,NULL,NULL),
(43,'铜锣烧',3,11,NULL,NULL,NULL,NULL),
(44,'华夫饼',3,12,NULL,NULL,NULL,NULL),
(45,'老婆饼',3,12,NULL,NULL,NULL,NULL),
(46,'奥利奥',3,13,NULL,NULL,NULL,NULL),
(47,'粤利粤',3,13,NULL,NULL,NULL,NULL),
(49,'膨化虾条',3,15,NULL,NULL,NULL,NULL),
(50,'油炸虾条',3,15,NULL,NULL,NULL,NULL),
(51,'海鲜虾条',3,15,NULL,NULL,NULL,NULL);

/*Table structure for table `index_img` */

DROP TABLE IF EXISTS `index_img`;

CREATE TABLE `index_img` (
  `img_id` varchar(64) NOT NULL COMMENT '主键',
  `img_url` varchar(128) NOT NULL COMMENT '图片 图片地址',
  `img_bg_color` varchar(32) DEFAULT NULL COMMENT '背景色 背景颜色',
  `prod_id` varchar(64) DEFAULT NULL COMMENT '商品id 商品id',
  `category_id` varchar(64) DEFAULT NULL COMMENT '商品分类id 商品分类id',
  `index_type` int(11) NOT NULL COMMENT '轮播图类型 轮播图类型，用于判断，可以根据商品id或者分类进行页面跳转，1：商品 2：分类',
  `seq` int(11) NOT NULL COMMENT '轮播图展示顺序 轮播图展示顺序，从小到大',
  `status` int(11) NOT NULL COMMENT '是否展示:1表示正常显示，0表示下线 是否展示，1：展示    0：不展示',
  `create_time` datetime NOT NULL COMMENT '创建时间 创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间 更新',
  PRIMARY KEY (`img_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='轮播图 ';

/*Data for the table `index_img` */

insert  into `index_img`(`img_id`,`img_url`,`img_bg_color`,`prod_id`,`category_id`,`index_type`,`seq`,`status`,`create_time`,`update_time`) values 
('1','ad1.jpg','green','101',NULL,1,1,1,'2021-04-14 17:21:13','2021-04-14 17:21:18'),
('2','ad2.jpg','pink',NULL,'1',2,2,1,'2021-04-14 17:21:49','2021-04-14 17:21:52'),
('3','ad3.jpg','black','103',NULL,1,3,1,'2021-04-14 17:22:23','2021-04-14 17:22:27'),
('4','ad4.jpg','orange',NULL,'2',2,4,1,'2021-04-14 17:23:06','2021-04-14 17:23:08'),
('5','ad5.jpg','yellow','101',NULL,1,2,0,'2021-04-24 09:34:01','2021-04-25 09:34:05');

/*Table structure for table `order_item` */

DROP TABLE IF EXISTS `order_item`;

CREATE TABLE `order_item` (
  `item_id` varchar(64) NOT NULL COMMENT '订单项ID',
  `order_id` varchar(64) NOT NULL COMMENT '订单ID',
  `product_id` varchar(64) NOT NULL COMMENT '商品ID',
  `product_name` varchar(32) NOT NULL COMMENT '商品名称',
  `product_img` varchar(32) DEFAULT NULL COMMENT '商品图片',
  `sku_id` varchar(128) NOT NULL COMMENT 'skuID',
  `sku_name` varchar(32) NOT NULL COMMENT 'sku名称',
  `product_price` decimal(32,8) NOT NULL COMMENT '商品价格',
  `buy_counts` int(11) NOT NULL COMMENT '购买数量',
  `total_amount` decimal(32,8) DEFAULT NULL COMMENT '商品总金额',
  `basket_date` datetime DEFAULT NULL COMMENT '加入购物车时间',
  `buy_time` datetime DEFAULT NULL COMMENT '购买时间',
  `is_comment` int(11) DEFAULT NULL COMMENT '评论状态： 0 未评价  1 已评价',
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单项/快照 ';

/*Data for the table `order_item` */

insert  into `order_item`(`item_id`,`order_id`,`product_id`,`product_name`,`product_img`,`sku_id`,`sku_name`,`product_price`,`buy_counts`,`total_amount`,`basket_date`,`buy_time`,`is_comment`) values 
('16756768528493611','086560ecccca4e538aadf482dbb4f14c','3','咪咪小虾条','mmxt_1.png','3','咪咪虾条 整箱50包',40.00000000,3,120.00000000,'2023-02-06 17:47:33','2023-02-06 17:47:33',0),
('16756768528548464','086560ecccca4e538aadf482dbb4f14c','3','咪咪小虾条','mmxt_1.png','3','咪咪虾条 整箱50包',40.00000000,3,120.00000000,'2023-02-06 17:47:33','2023-02-06 17:47:33',0),
('16756773911512411','053f82c3b6234e8784ded0864773d77f','3','咪咪小虾条','mmxt_1.png','3','咪咪虾条 整箱50包',40.00000000,3,120.00000000,'2023-02-06 17:56:31','2023-02-06 17:56:31',0),
('16756773911568726','053f82c3b6234e8784ded0864773d77f','3','咪咪小虾条','mmxt_1.png','3','咪咪虾条 整箱50包',40.00000000,3,120.00000000,'2023-02-06 17:56:31','2023-02-06 17:56:31',0),
('16756795420757497','f8437f655ce2454f8cf15faef57ed20b','3','咪咪小虾条','mmxt_1.png','3','咪咪虾条 整箱50包',40.00000000,3,120.00000000,'2023-02-06 18:32:22','2023-02-06 18:32:22',0),
('16756795420785981','f8437f655ce2454f8cf15faef57ed20b','3','咪咪小虾条','mmxt_1.png','3','咪咪虾条 整箱50包',40.00000000,3,120.00000000,'2023-02-06 18:32:22','2023-02-06 18:32:22',0);

/*Table structure for table `orders` */

DROP TABLE IF EXISTS `orders`;

CREATE TABLE `orders` (
  `order_id` varchar(64) NOT NULL COMMENT '订单ID 同时也是订单编号',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `untitled` varchar(32) DEFAULT NULL COMMENT '产品名称（多个产品用,隔开）',
  `receiver_name` varchar(32) NOT NULL COMMENT '收货人快照',
  `receiver_mobile` varchar(32) NOT NULL COMMENT '收货人手机号快照',
  `receiver_address` varchar(128) NOT NULL COMMENT '收货地址快照',
  `total_amount` decimal(32,8) DEFAULT NULL COMMENT '订单总价格',
  `actual_amount` int(11) DEFAULT NULL COMMENT '实际支付总价格',
  `pay_type` int(11) DEFAULT NULL COMMENT '支付方式 1:微信 2:支付宝',
  `order_remark` varchar(32) DEFAULT NULL COMMENT '订单备注',
  `status` varchar(32) DEFAULT NULL COMMENT '订单状态 1:待付款 2:待发货 3:待收货 4:待评价 5:已完成 6:已关闭',
  `delivery_type` varchar(32) DEFAULT NULL COMMENT '配送方式',
  `delivery_flow_id` varchar(32) DEFAULT NULL COMMENT '物流单号',
  `order_freight` decimal(32,8) DEFAULT '0.00000000' COMMENT '订单运费 默认可以为零，代表包邮',
  `delete_status` int(11) DEFAULT '0' COMMENT '逻辑删除状态 1: 删除 0:未删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间（成交时间）',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `pay_time` datetime DEFAULT NULL COMMENT '付款时间',
  `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
  `flish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `close_type` int(11) DEFAULT NULL COMMENT '订单关闭类型1-超时未支付 2-退款关闭 4-买家取消 15-已通过货到付款交易',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单 ';

/*Data for the table `orders` */

insert  into `orders`(`order_id`,`user_id`,`untitled`,`receiver_name`,`receiver_mobile`,`receiver_address`,`total_amount`,`actual_amount`,`pay_type`,`order_remark`,`status`,`delivery_type`,`delivery_flow_id`,`order_freight`,`delete_status`,`create_time`,`update_time`,`pay_time`,`delivery_time`,`flish_time`,`cancel_time`,`close_type`) values 
('053f82c3b6234e8784ded0864773d77f','1','咪咪小虾条, 咪咪小虾条, ','张三','18888888888','广西贵港桂平',1.00000000,1,1,'订单备注','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-02-06 17:56:31',NULL),
('086560ecccca4e538aadf482dbb4f14c','1','咪咪小虾条, 咪咪小虾条, ','张三','18888888888','广西贵港桂平',1.00000000,1,1,'订单备注',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2023-02-06 17:47:33',NULL),
('39e41464bb104be29e80a6bc138c0afa','1','','张三','18888888888','广西贵港桂平',1.00000000,1,1,'订单备注','1',NULL,NULL,NULL,NULL,'2023-02-08 13:05:29',NULL,NULL,NULL,NULL,'2023-02-06 17:55:27',NULL),
('f8437f655ce2454f8cf15faef57ed20b','1','咪咪小虾条, 咪咪小虾条, ','张三','18888888888','广西贵港桂平',1.00000000,1,1,'订单备注','2',NULL,NULL,NULL,NULL,'2023-02-08 13:05:33',NULL,NULL,NULL,NULL,'2023-02-06 18:32:22',NULL);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `product_id` varchar(64) NOT NULL COMMENT '商品主键id',
  `product_name` varchar(32) NOT NULL COMMENT '商品名称 商品名称',
  `category_id` int(11) NOT NULL COMMENT '分类外键id 分类id',
  `root_category_id` int(11) NOT NULL COMMENT '一级分类外键id 一级分类id，用于优化查询',
  `sold_num` int(11) NOT NULL COMMENT '销量 累计销售',
  `product_status` int(11) NOT NULL COMMENT '默认是1，表示正常状态, -1表示删除, 0下架 默认是1，表示正常状态, -1表示删除, 0下架',
  `content` text NOT NULL COMMENT '商品内容 商品内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品 商品信息相关表：分类表，商品图片表，商品规格表，商品参数表';

/*Data for the table `product` */

insert  into `product`(`product_id`,`product_name`,`category_id`,`root_category_id`,`sold_num`,`product_status`,`content`,`create_time`,`update_time`) values 
('1','奥利奥小饼干666',46,2,196,1,'<label style=\"color:red;font-size:18px\">1.全新包装</label><br/><img src=\"static/pimgs/ala_desc1.png\"/><br/><label style=\"color:green;font-size:18px\">2.全新口味</label><br/><img src=\"static/pimgs/ala_desc2.png\"/><label style=\"color:blue;font-size:18px\">3.全新体验</label><br/><img src=\"static/pimgs/ala_desc3.png\"/>','2021-04-26 15:20:51','2021-04-23 15:20:58'),
('10','商品10',10,1,127,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('11','商品11',10,1,128,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('12','商品12',46,2,122,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('13','商品13',46,2,123,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('14','商品14',46,2,124,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('15','商品15',46,2,125,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('16','商品16',46,2,126,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('17','商品17',46,2,127,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('18','商品18',46,2,128,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('19','加力加小虾条1',49,2,111,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('2','旺旺华夫饼',22,1,202,1,'旺旺华夫饼商品内容','2021-04-26 15:27:22','2021-04-25 15:27:25'),
('20','加力加虾条2',49,2,199,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('21','加力加虾条3',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('22','加力加虾条4',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('23','加力加虾条5',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('24','加力加虾条6',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('25','加力加虾条7',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('26','加力加虾条8',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('27','加力加虾条9',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('28','加力加虾条10',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('29','加力加虾条11',49,2,168,1,'加力加虾条说明','2021-03-04 09:53:14','2021-05-10 09:53:20'),
('3','咪咪小虾条',50,2,199,1,'<img src=\"static/pimgs/mmxt_desc.jpg\"/>','2021-04-26 15:58:09','2021-04-25 15:58:11'),
('4','旺旺雪饼',46,2,201,1,'旺旺雪饼商品内容','2021-04-25 15:58:59','2021-04-25 15:59:03'),
('5','商品5',10,1,122,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('6','商品6',10,1,123,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('7','商品7',10,1,124,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('8','商品8',10,1,125,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11'),
('9','商品9',10,1,126,1,'商品说明','2021-04-26 11:11:11','2021-04-26 11:11:11');

/*Table structure for table `product_comments` */

DROP TABLE IF EXISTS `product_comments`;

CREATE TABLE `product_comments` (
  `comm_id` varchar(64) NOT NULL COMMENT 'ID',
  `product_id` varchar(64) NOT NULL COMMENT '商品id',
  `product_name` varchar(64) DEFAULT NULL COMMENT '商品名称',
  `order_item_id` varchar(64) DEFAULT NULL COMMENT '订单项(商品快照)ID 可为空',
  `user_id` varchar(64) DEFAULT NULL COMMENT '评论用户id 用户名须脱敏',
  `is_anonymous` int(11) DEFAULT NULL COMMENT '是否匿名（1:是，0:否）',
  `comm_type` int(11) DEFAULT NULL COMMENT '评价类型（1好评，0中评，-1差评）',
  `comm_level` int(11) NOT NULL COMMENT '评价等级 1：好评 2：中评 3：差评',
  `comm_content` varchar(128) NOT NULL COMMENT '评价内容',
  `comm_imgs` varchar(32) DEFAULT NULL COMMENT '评价晒图(JSON {img1:url1,img2:url2}  )',
  `sepc_name` datetime DEFAULT NULL COMMENT '评价时间 可为空',
  `reply_status` int(11) DEFAULT NULL COMMENT '是否回复（0:未回复，1:已回复）',
  `reply_content` varchar(32) DEFAULT NULL COMMENT '回复内容',
  `reply_time` datetime DEFAULT NULL COMMENT '回复时间',
  `is_show` int(11) DEFAULT NULL COMMENT '是否显示（1:是，0:否）',
  PRIMARY KEY (`comm_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品评价 ';

/*Data for the table `product_comments` */

insert  into `product_comments`(`comm_id`,`product_id`,`product_name`,`order_item_id`,`user_id`,`is_anonymous`,`comm_type`,`comm_level`,`comm_content`,`comm_imgs`,`sepc_name`,`reply_status`,`reply_content`,`reply_time`,`is_show`) values 
('1','3','咪咪虾条','10000001','4',1,1,1,'我买这不是来吃的，就是玩儿','{}','2021-04-27 14:51:10',0,NULL,'2021-04-27 14:51:32',1),
('2','3','咪咪虾条','10000002','5',0,1,1,'nice','{img1:“mmxt2.png”}','2021-04-27 14:53:20',1,'你好我也好','2021-04-27 14:53:37',1),
('3','3','咪咪虾条','10000003','1',1,1,1,'评价内容3','{}','2021-04-27 09:36:36',0,NULL,NULL,1),
('4','3','咪咪虾条','10000004','2',1,1,1,'评价内容3','{}','2021-04-28 09:37:28',0,NULL,NULL,1),
('5','3','咪咪虾条','10000005','2',1,0,1,'评价内容5','{}','2021-04-28 09:38:13',0,NULL,NULL,1),
('6','3','咪咪虾条','10000006','3',1,0,1,'评价内容6','{}','2021-04-21 09:39:16',0,NULL,NULL,1),
('7','3','咪咪虾条','10000007','4',0,-1,1,'评价内容7','{}','2021-04-06 09:40:01',0,NULL,NULL,1);

/*Table structure for table `product_img` */

DROP TABLE IF EXISTS `product_img`;

CREATE TABLE `product_img` (
  `id` varchar(64) NOT NULL COMMENT '图片主键',
  `item_id` varchar(64) NOT NULL COMMENT '商品外键id 商品外键id',
  `url` varchar(128) NOT NULL COMMENT '图片地址 图片地址',
  `sort` int(11) NOT NULL COMMENT '顺序 图片顺序，从小到大',
  `is_main` int(11) NOT NULL COMMENT '是否主图 是否主图，1：是，0：否',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品图片 ';

/*Data for the table `product_img` */

insert  into `product_img`(`id`,`item_id`,`url`,`sort`,`is_main`,`created_time`,`updated_time`) values 
('1','1','ala_1.png',1,1,'2021-04-22 15:25:08','2021-04-22 15:25:15'),
('10','6','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('11','7','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('12','8','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('13','9','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('14','10','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('15','11','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('16','12','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('17','13','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('18','14','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('19','15','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('2','1','ala_2.png',2,0,'2021-04-22 15:25:39','2021-04-22 15:25:44'),
('20','16','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('21','17','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('22','18','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11'),
('23','19','jlj_1.png',1,1,'2021-05-10 09:54:10','2021-05-10 09:54:12'),
('3','2','hfb_1.png',1,1,'2021-04-24 15:30:09','2021-04-25 15:30:19'),
('4','2','hfb_2.png',2,0,'2021-04-25 15:30:45','2021-04-25 15:30:48'),
('5','3','mmxt_1.png',1,1,'2021-04-25 15:59:28','2021-04-25 15:59:31'),
('6','3','mmxt_2.png',2,0,'2021-04-25 15:59:51','2021-04-25 15:59:54'),
('7','4','wwxb_1.png',1,1,'2021-04-25 16:00:12','2021-04-25 16:00:17'),
('8','4','wwxb_2.png',2,0,'2021-04-25 16:00:36','2021-04-25 16:00:39'),
('9','5','wwxb_1.png',1,1,'2021-04-26 11:11:11','2021-04-26 11:11:11');

/*Table structure for table `product_params` */

DROP TABLE IF EXISTS `product_params`;

CREATE TABLE `product_params` (
  `param_id` varchar(64) NOT NULL COMMENT '商品参数id',
  `product_id` varchar(32) NOT NULL COMMENT '商品id',
  `product_place` varchar(32) NOT NULL COMMENT '产地 产地，例：中国江苏',
  `foot_period` varchar(32) NOT NULL COMMENT '保质期 保质期，例：180天',
  `brand` varchar(32) NOT NULL COMMENT '品牌名 品牌名，例：三只大灰狼',
  `factory_name` varchar(32) NOT NULL COMMENT '生产厂名 生产厂名，例：大灰狼工厂',
  `factory_address` varchar(32) NOT NULL COMMENT '生产厂址 生产厂址，例：大灰狼生产基地',
  `packaging_method` varchar(32) NOT NULL COMMENT '包装方式 包装方式，例：袋装',
  `weight` varchar(32) NOT NULL COMMENT '规格重量 规格重量，例：35g',
  `storage_method` varchar(32) NOT NULL COMMENT '存储方法 存储方法，例：常温5~25°',
  `eat_method` varchar(32) NOT NULL COMMENT '食用方式 食用方式，例：开袋即食',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`param_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品参数 ';

/*Data for the table `product_params` */

insert  into `product_params`(`param_id`,`product_id`,`product_place`,`foot_period`,`brand`,`factory_name`,`factory_address`,`packaging_method`,`weight`,`storage_method`,`eat_method`,`create_time`,`update_time`) values 
('1','3','中国湖北武汉','12个月','咪咪','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('2','19','中国湖北武汉','12个月','卫龙','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('3','20','中国湖北武汉','12个月','威龙','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('4','21','中国湖北武汉','12个月','卫龙','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('5','22','中国湖北武汉','12个月','威龙','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('6','23','中国湖北武汉','12个月','老干妈','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('7','24','中国湖北武汉','12个月','卫龙','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35'),
('8','1','中国湖北武汉','12个月','良品铺子','贤磊咪咪虾条有限责任公司','湖北武汉江夏区光谷大道77号','袋装','50g','常温保存','开袋即食','2021-04-27 11:53:20','2021-04-27 11:53:35');

/*Table structure for table `product_sku` */

DROP TABLE IF EXISTS `product_sku`;

CREATE TABLE `product_sku` (
  `sku_id` varchar(64) NOT NULL COMMENT 'sku编号',
  `product_id` varchar(64) NOT NULL COMMENT '商品id',
  `sku_name` varchar(32) NOT NULL COMMENT 'sku名称',
  `sku_img` varchar(32) DEFAULT NULL COMMENT 'sku图片',
  `untitled` varchar(400) DEFAULT NULL COMMENT '属性组合（格式是p1:v1;p2:v2）',
  `original_price` int(11) NOT NULL COMMENT '原价',
  `sell_price` int(11) NOT NULL COMMENT '销售价格',
  `discounts` decimal(4,2) NOT NULL COMMENT '折扣力度',
  `stock` int(11) NOT NULL COMMENT '库存',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `status` int(11) DEFAULT NULL COMMENT 'sku状态(1启用，0禁用，-1删除)',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='商品规格 每一件商品都有不同的规格，不同的规格又有不同的价格和优惠力度，规格表为此设计';

/*Data for the table `product_sku` */

insert  into `product_sku`(`sku_id`,`product_id`,`sku_name`,`sku_img`,`untitled`,`original_price`,`sell_price`,`discounts`,`stock`,`create_time`,`update_time`,`status`) values 
('1','3','咪咪虾条 单包 50g','mmxt_1.png','{口味:[\"原味\",\"烧烤\",\"芥末\"],包装:[\"盒装\",\"袋装\"]}',2,1,0.70,307,'2021-04-26 15:58:00','2021-04-25 15:58:00',1),
('10','24','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('11','25','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('12','26','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('13','27','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('14','28','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('15','29','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('2','3','咪咪虾条 6连包','mmxt_1.png','{口味:[\"原味\",\"烧烤\"],包装:[\"新包装\",\"老包装\"]}',6,5,0.60,171,'2021-04-26 14:48:21','2021-04-26 14:48:25',1),
('3','3','咪咪虾条 整箱50包','mmxt_1.png','{口味:[\"原味\",\"烧烤\",\"孜然\"]}',50,40,0.50,16,'2021-04-26 14:49:20','2021-04-26 14:49:23',1),
('4','1','奥利奥分享装','ala_1.png','{口味:[\"草莓\",\"巧克力\",\"牛奶\",\"原味\"]}',10,8,0.80,137,'2021-04-27 11:38:55','2021-04-27 11:39:00',1),
('5','19','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('6','20','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('7','21','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('8','22','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1),
('9','23','加力加 100g','jlj_1.png','{口味:[\"原味\",\"微辣\",\"BT\"]}',3,2,0.50,999,'2021-05-10 09:56:09','2021-05-10 09:56:12',1);

/*Table structure for table `shopping_cart` */

DROP TABLE IF EXISTS `shopping_cart`;

CREATE TABLE `shopping_cart` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `product_id` varchar(32) NOT NULL COMMENT '商品ID',
  `sku_id` varchar(32) NOT NULL COMMENT 'skuID',
  `user_id` varchar(32) NOT NULL COMMENT '用户ID',
  `cart_num` varchar(32) NOT NULL COMMENT '购物车商品数量',
  `cart_time` varchar(32) NOT NULL COMMENT '添加购物车时间',
  `product_price` decimal(32,8) DEFAULT NULL COMMENT '添加购物车时商品价格',
  `sku_props` varchar(255) DEFAULT NULL COMMENT '选择的套餐的属性',
  PRIMARY KEY (`cart_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='购物车 ';

/*Data for the table `shopping_cart` */

insert  into `shopping_cart`(`cart_id`,`product_id`,`sku_id`,`user_id`,`cart_num`,`cart_time`,`product_price`,`sku_props`) values 
(6,'1','4','6','1','2021-05-12 09:18:47',8.00000000,'口味:草莓;');

/*Table structure for table `user_addr` */

DROP TABLE IF EXISTS `user_addr`;

CREATE TABLE `user_addr` (
  `addr_id` varchar(64) NOT NULL COMMENT '地址主键id',
  `user_id` varchar(64) NOT NULL COMMENT '用户ID',
  `receiver_name` varchar(32) NOT NULL COMMENT '收件人姓名',
  `receiver_mobile` varchar(32) NOT NULL COMMENT '收件人手机号',
  `province` varchar(32) NOT NULL COMMENT '省份',
  `city` varchar(32) NOT NULL COMMENT '城市',
  `area` varchar(32) NOT NULL COMMENT '区县',
  `addr` varchar(128) NOT NULL COMMENT '详细地址',
  `post_code` varchar(32) DEFAULT NULL COMMENT '邮编',
  `status` int(11) DEFAULT NULL COMMENT '状态,1正常，0无效',
  `common_addr` int(11) DEFAULT NULL COMMENT '是否默认地址 1是 1:是  0:否',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`addr_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户地址 ';

/*Data for the table `user_addr` */

insert  into `user_addr`(`addr_id`,`user_id`,`receiver_name`,`receiver_mobile`,`province`,`city`,`area`,`addr`,`post_code`,`status`,`common_addr`,`create_time`,`update_time`) values 
('1','1','张三','13030303300','湖北','武汉','江夏区','光谷大道77号','421000',1,1,'2021-04-30 09:33:30','2021-04-28 09:33:37'),
('2','1','小丽','13131313311','湖北','宜昌','夷陵区','巴拉巴拉','410000',1,0,'2021-04-30 09:34:41','2021-04-30 09:34:44'),
('3','6','韩梅梅','13232323322','湖南','长沙','岳麓区','淅沥淅沥','430000',1,1,'2021-04-30 09:35:49','2021-04-30 09:35:53');

/*Table structure for table `user_login_history` */

DROP TABLE IF EXISTS `user_login_history`;

CREATE TABLE `user_login_history` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `AREA` varchar(32) DEFAULT NULL COMMENT '地区',
  `COUNTRY` varchar(32) DEFAULT NULL COMMENT '国家',
  `USER_ID` varchar(32) DEFAULT NULL COMMENT '用户id',
  `IP` varchar(32) NOT NULL COMMENT 'IP',
  `LOGIN_TIME` varchar(32) NOT NULL COMMENT '时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='登录历史表 ';

/*Data for the table `user_login_history` */

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(64) NOT NULL AUTO_INCREMENT COMMENT '主键id 用户id',
  `username` varchar(32) NOT NULL COMMENT '用户名 用户名',
  `password` varchar(64) NOT NULL COMMENT '密码 密码',
  `nickname` varchar(32) DEFAULT NULL COMMENT '昵称 昵称',
  `realname` varchar(128) DEFAULT NULL COMMENT '真实姓名 真实姓名',
  `user_img` varchar(1024) NOT NULL COMMENT '头像 头像',
  `user_mobile` varchar(32) DEFAULT NULL COMMENT '手机号 手机号',
  `user_email` varchar(32) DEFAULT NULL COMMENT '邮箱地址 邮箱地址',
  `user_sex` char(1) DEFAULT NULL COMMENT '性别 M(男) or F(女)',
  `user_birth` date DEFAULT NULL COMMENT '生日 生日',
  `user_regtime` datetime NOT NULL COMMENT '注册时间 创建时间',
  `user_modtime` datetime NOT NULL COMMENT '更新时间 更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='用户 ';

/*Data for the table `users` */

insert  into `users`(`user_id`,`username`,`password`,`nickname`,`realname`,`user_img`,`user_mobile`,`user_email`,`user_sex`,`user_birth`,`user_regtime`,`user_modtime`) values 
(1,'zhangsan','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'img/default.png',NULL,NULL,NULL,NULL,'2023-01-16 16:23:40','2023-01-16 16:23:40'),
(2,'ysh','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'img/default.png',NULL,NULL,NULL,NULL,'2023-01-16 16:23:56','2023-01-16 16:23:56'),
(3,'aaa','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'img/default.png',NULL,NULL,NULL,NULL,'2023-01-16 16:24:01','2023-01-16 16:24:01'),
(4,'bbb','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'img/default.png',NULL,NULL,NULL,NULL,'2023-01-16 16:24:05','2023-01-16 16:24:05'),
(5,'ccc','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'img/default.png',NULL,NULL,NULL,NULL,'2023-01-16 16:24:09','2023-01-16 16:24:09'),
(6,'ddd','e10adc3949ba59abbe56e057f20f883e',NULL,NULL,'img/default.png',NULL,NULL,NULL,NULL,'2023-01-16 16:24:14','2023-01-16 16:24:14');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
