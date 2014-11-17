CREATE TABLE `stt_magic_set` (
  `mst_name` varchar(100) DEFAULT NULL,
  `mst_uid` char(4) NOT NULL DEFAULT '',
  `mst_release_date` varchar(50) DEFAULT NULL,
  `mst_border` varchar(20) DEFAULT NULL,
  `mst_type` varchar(50) DEFAULT NULL,
  `mst_block` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mst_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_magic_card` (
  `mcr_card_name` varchar(150) DEFAULT NULL,
  `mcr_names` varchar(150) DEFAULT NULL,
  `mcr_set_id` char(4) DEFAULT NULL,
  `mcr_mana_cost` varchar(50) DEFAULT NULL,
  `mcr_cmc` double DEFAULT NULL,
  `mcr_colors` varchar(100) DEFAULT NULL,
  `mcr_type` varchar(500) DEFAULT NULL,
  `mcr_super_types` varchar(100) DEFAULT NULL,
  `mcr_sub_types` varchar(100) DEFAULT NULL,
  `mcr_types` varchar(100) DEFAULT NULL,
  `mcr_rarity` varchar(50) DEFAULT NULL,
  `mcr_text` varchar(1000) DEFAULT NULL,
  `mcr_flavor` varchar(500) DEFAULT NULL,
  `mcr_artist` varchar(50) DEFAULT NULL,
  `mcr_number` varchar(5) DEFAULT NULL,
  `mcr_power` varchar(5) DEFAULT NULL,
  `mcr_toughness` varchar(5) DEFAULT NULL,
  `mcr_loyalty` int(11) DEFAULT NULL,
  `mcr_layout` varchar(50) DEFAULT NULL,
  `mcr_multiverse_id` int(11) DEFAULT NULL,
  `mcr_variations` varchar(100) DEFAULT NULL,
  `mcr_image_name` varchar(150) DEFAULT NULL,
  `mcr_watermark` varchar(50) DEFAULT NULL,
  `mcr_border` varchar(15) DEFAULT NULL,
  `mcr_hand` int(11) DEFAULT NULL,
  `mcr_life` int(11) DEFAULT NULL,
  `mcr_timeshifted` tinyint(1) DEFAULT '0',
  `mcr_reserved` tinyint(1) DEFAULT '0',
  `mcr_release_date` varchar(50) DEFAULT NULL,
  `mcr_uid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`mcr_uid`),
  KEY `stt_magic_card` (`mcr_set_id`),
  CONSTRAINT `stt_magic_card` FOREIGN KEY (`mcr_set_id`) REFERENCES `stt_magic_set` (`mst_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=25952 DEFAULT CHARSET=utf8;

CREATE TABLE `stt_user` (
  `usr_first_name` varchar(50) DEFAULT NULL,
  `usr_last_name` varchar(50) DEFAULT NULL,
  `usr_email` varchar(255) DEFAULT NULL,
  `usr_password` varchar(255) DEFAULT NULL,
  `usr_role` enum('Admin','Employee','Customer','Guest') DEFAULT NULL,
  `usr_address_1` varchar(150) DEFAULT NULL,
  `usr_address_2` varchar(100) DEFAULT NULL,
  `usr_city` varchar(100) DEFAULT NULL,
  `usr_state` varchar(2) DEFAULT NULL,
  `usr_zip` varchar(5) DEFAULT NULL,
  `usr_phone` varchar(14) DEFAULT NULL,
  `usr_newsletter` tinyint(1) DEFAULT '0',
  `usr_active` tinyint(1) DEFAULT '0',
  `usr_dci_number` varchar(10) DEFAULT NULL,
  `usr_uid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`usr_uid`),
  UNIQUE KEY `email` (`usr_email`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE `stt_yugioh_card` (
  `ycr_uid` int(11) NOT NULL AUTO_INCREMENT,
  `ycr_name` varchar(255) DEFAULT NULL,
  `ycr_card_type` varchar(50) DEFAULT NULL,
  `ycr_attribute` varchar(25) DEFAULT NULL,
  `ycr_type` varchar(25) DEFAULT NULL,
  `ycr_level` int(11) DEFAULT NULL,
  `ycr_atk` int(11) DEFAULT NULL,
  `ycr_def` int(11) DEFAULT NULL,
  `ycr_description` text,
  PRIMARY KEY (`ycr_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=23778 DEFAULT CHARSET=utf8;


CREATE TABLE `stt_webpage` (
  `wbp_uid` int(11) NOT NULL AUTO_INCREMENT,
  `wbp_name` varchar(150) DEFAULT NULL,
  `wbp_body` text,
  `wbp_code` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`wbp_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE `stt_order` (
  `ord_uid` int(11) NOT NULL AUTO_INCREMENT,
  `ord_usr_uid` int(11) DEFAULT NULL,
  `ord_status` enum('PLACED','SHIPPED','RETURNED','READY_FOR_PICKUP','CANCELLED','DELIVERED') DEFAULT NULL,
  `ord_total` DECIMAL(7, 2) NOT NULL,
  `ord_tax_total` DECIMAL(7, 2) NOT NULL,
  `ord_shipping_total` DECIMAL(7, 2) NOT NULL,
  `ord_shipping_method` varchar(50) DEFAULT NULL,
  `ord_date_submitted` datetime NOT NULL,
  `ord_date_complete` datetime DEFAULT NULL,
  `ord_shipping_address1` varchar(150) DEFAULT NULL,
  `ord_shipping_address2` varchar(100) DEFAULT NULL,
  `ord_shipping_city` varchar(100) DEFAULT NULL,
  `ord_shipping_state` varchar(2) DEFAULT NULL,
  `ord_shipping_zip` varchar(5) DEFAULT NULL,
  `ord_transaction_id` varchar(10) DEFAULT NULL,
  `ord_tracking_number` varchar(35) DEFAULT NULL,
  `ord_in_store_pickup` tinyint(1) DEFAULT NULL,
  `ord_paid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ord_uid`),
  KEY `ord_usr_uid_fk` (`ord_usr_uid`),
  CONSTRAINT `ord_usr_uid_fk` FOREIGN KEY (`ord_usr_uid`) REFERENCES `stt_user` (`usr_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_event` (
  `evn_uid` int(11) NOT NULL AUTO_INCREMENT,
  `evn_title` varchar(200) DEFAULT NULL,
  `evn_description` text,
  `evn_player_limit` int(11) DEFAULT NULL,
  `evn_number_in_store_users` int(11) DEFAULT NULL,
  `evn_from_date` datetime DEFAULT NULL,
  `evn_to_date` datetime DEFAULT NULL,
  PRIMARY KEY (`evn_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_inventory` (
  `inv_uid` int(11) NOT NULL AUTO_INCREMENT,
  `inv_name` varchar(200) DEFAULT NULL,
  `inv_image` varchar(300) DEFAULT NULL,
  `inv_mcr_uid` int(11) DEFAULT NULL,
  `inv_ycr_uid` int(11) DEFAULT NULL,
  `inv_evn_uid` int(11) DEFAULT NULL,
  `inv_pre_order` tinyint(1) DEFAULT NULL,
  `inv_description` text,
  PRIMARY KEY (`inv_uid`),
  KEY `inv_mcr_uid_fk` (`inv_mcr_uid`),
  KEY `inv_ycr_uid_fk` (`inv_ycr_uid`),
  KEY `inv_evn_uid_fk` (`inv_evn_uid`),
  CONSTRAINT `inv_evn_uid_fk` FOREIGN KEY (`inv_evn_uid`) REFERENCES `stt_event` (`evn_uid`),
  CONSTRAINT `inv_mcr_uid_fk` FOREIGN KEY (`inv_mcr_uid`) REFERENCES `stt_magic_card` (`mcr_uid`),
  CONSTRAINT `inv_ycr_uid_fk` FOREIGN KEY (`inv_ycr_uid`) REFERENCES `stt_yugioh_card` (`ycr_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_inventory_item` (
  `iit_uid` int(11) NOT NULL AUTO_INCREMENT,
  `iit_ord_uid` int(11) NOT NULL,
  `iit_price` DECIMAL(7, 2) NOT NULL,
  `iit_quantity` int(8) NOT NULL DEFAULT 1,
  `iit_name` varchar(200) NOT NULL,
  `iit_image` varchar(300) NOT NULL,
  `iit_mcr_uid` int(11) DEFAULT NULL,
  `iit_ycr_uid` int(11) DEFAULT NULL,
  `iit_evn_uid` int(11) DEFAULT NULL,
  `iit_pre_order` tinyint(1) DEFAULT NULL,
  `iit_description` text NOT NULL,
  `iit_condition` varchar(30) NOT NULL,
  PRIMARY KEY (`iit_uid`),
  KEY `iit_ord_uid_fk` (`iit_ord_uid`),
  KEY `iit_mcr_uid_fk` (`iit_mcr_uid`),
  KEY `iit_ycr_uid_fk` (`iit_ycr_uid`),
  KEY `iit_evn_uid_fk` (`iit_evn_uid`),
  CONSTRAINT `iit_evn_uid_fk` FOREIGN KEY (`iit_evn_uid`) REFERENCES `stt_event` (`evn_uid`),
  CONSTRAINT `iit_mcr_uid_fk` FOREIGN KEY (`iit_mcr_uid`) REFERENCES `stt_inventory` (`inv_uid`),
  CONSTRAINT `iit_ord_uid_fk` FOREIGN KEY (`iit_ord_uid`) REFERENCES `stt_order` (`ord_uid`),
  CONSTRAINT `iit_ycr_uid_fk` FOREIGN KEY (`iit_ycr_uid`) REFERENCES `stt_yugioh_card` (`ycr_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_stock_notification` (
  `snt_uid` int(11) NOT NULL AUTO_INCREMENT,
  `snt_email` varchar(200) DEFAULT NULL,
  `snt_inv_uid` int(11) NOT NULL,
  PRIMARY KEY (`snt_uid`),
  KEY `snt_inv_uid_fk` (`snt_inv_uid`),
  CONSTRAINT `snt_inv_uid_fk` FOREIGN KEY (`snt_inv_uid`) REFERENCES `stt_inventory` (`inv_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_announcement` (
  `ann_uid` int(11) NOT NULL AUTO_INCREMENT,
  `ann_created` datetime DEFAULT NULL,
  `ann_title` varchar(150) DEFAULT NULL,
  `ann_content` text,
  PRIMARY KEY (`ann_uid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE stt_cost (
  cst_uid int(11) NOT NULL AUTO_INCREMENT,
  cst_item_condition ENUM('NEW','NEAR_MINT', 'LIGHTLY_PLAYED', 'MODERATELY_PLAYED', 'HEAVILY_PLAYED', 'DAMAGED')
                      NOT NULL DEFAULT 'NEW',
  cst_item_price DECIMAL(7, 2) NOT NULL,
  cst_item_quantity int(8) DEFAULT NULL,
  cst_inv_uid int(11) NOT NULL,
  FOREIGN KEY (cst_inv_uid) REFERENCES stt_inventory(inv_uid),
  PRIMARY KEY (cst_uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_category` (
  `cat_uid` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(30) NOT NULL,
  `ctg_deletable` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`cat_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


