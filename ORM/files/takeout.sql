-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `customer_id` bigint(20) NOT NULL COMMENT '客户id',
  `seller_id` bigint(20) NOT NULL COMMENT ' 卖家id',
  `deliver_id` bigint(20) DEFAULT NULL COMMENT '外卖员id',
  `food_id` bigint(20) NOT NULL COMMENT '食品id',
  `food_comment` varchar(100) DEFAULT NULL COMMENT '对商品的评论内容',
  `seller_comment` varchar(100) DEFAULT NULL COMMENT '对商家的评论内容',
  `deliver_comment` varchar(100) DEFAULT NULL COMMENT '对快递员评论内容',
  `food_score` tinyint(1) DEFAULT '5' COMMENT '商品评分：1,2,3,4,5，数字越大评分越高',
  `deliver_score` tinyint(1) DEFAULT '5' COMMENT '快递员评分：1,2,3,4,5，数字越大评分越高',
  `seller_score` tinyint(1) DEFAULT '5' COMMENT '卖家评分：1,2,3,4,5，数字越大评分越高',
  `comment_date` datetime DEFAULT NULL,
  `named` tinyint(1) DEFAULT '1' COMMENT '是否匿名：0匿名，1不匿名',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '评论删除状态0：未删除，1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评价表';