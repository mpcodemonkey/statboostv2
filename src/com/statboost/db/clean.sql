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
  `mcr_timeshifted` boolean DEFAULT FALSE,
  `mcr_reserved` boolean DEFAULT FALSE,
  `mcr_release_date` varchar(50) DEFAULT NULL,
  `mcr_uid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`mcr_uid`),
  CONSTRAINT `stt_magic_card` FOREIGN KEY (`mcr_set_id`) REFERENCES `stt_magic_set` (`mst_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_magic_set` (
  `mst_name` varchar(100) DEFAULT NULL,
  `mst_uid` char(4) NOT NULL DEFAULT '',
  `mst_release_date` varchar(50) DEFAULT NULL,
  `mst_border` varchar(20) DEFAULT NULL,
  `mst_type` varchar(50) DEFAULT NULL,
  `mst_block` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`mst_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_user` (
  `usr_uid` int(11) NOT NULL DEFAULT '0',
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
  `usr_phone` varchar(10) DEFAULT NULL,
  `usr_newsletter` boolean DEFAULT false,
  `usr_active` boolean DEFAULT true,
  `usr_dci_number` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`usr_uid`),
  UNIQUE KEY `email` (`usr_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `stt_yugioh_card` (
  `ycr_uid` int(10) NOT NULL DEFAULT '0',
  `ycr_name` varchar(255) DEFAULT NULL,
  `ycr_card_type` varchar(50) DEFAULT NULL,
  `ycr_attribute` varchar(25) DEFAULT NULL,
  `ycr_type` varchar(25) DEFAULT NULL,
  `ycr_level` int(11) DEFAULT NULL,
  `ycr_atk` int(11) DEFAULT NULL,
  `ycr_def` int(11) DEFAULT NULL,
  `ycr_description` text,
  PRIMARY KEY (`ycr_uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table stt_email_template  (
  etm_uid int primary key auto_increment,
  etm_name varchar(100) not null,
  etm_body text not null
);

create table stt_email_variable_group  (
  evg_uid int primary key auto_increment,
  evg_name varchar(50)
);

create table stt_email  (
  eml_uid int primary key auto_increment,
  eml_name varchar(100) not null,
  eml_subject varchar(250) not null,
  eml_from varchar(1000) not null,
  eml_to varchar(1000) not null,
  eml_body text not null,
  eml_etm_uid int not null,
  constraint eml_etm_uid_fk foreign key (eml_etm_uid) references stt_email_template(etm_uid),
  eml_evg_uid int not null,
  constraint eml_evg_uid_fk foreign key (eml_evg_uid) references stt_email_variable_group(evg_uid)
);

create table stt_workflow_event  (
  wev_uid int primary key auto_increment,
  wev_name varchar(100) not null,
  wev_description varchar(1000),
  wev_code varchar(100) not null
);

create table stt_email_workflow_event_link  (
  ewe_uid int primary key auto_increment,
  ewe_eml_uid int not null,
  constraint ewe_eml_uid_fk foreign key (ewe_eml_uid) references stt_email (eml_uid),
  ewe_wev_uid int not null,
  constraint ewe_wev_uid_fk foreign key (ewe_wev_uid) references stt_workflow_event (wev_uid)
) ;

create table stt_group_event_link  (
  gel_uid int primary key auto_increment,
  gel_evg_uid int not null,
  constraint gel_evg_uid_fk foreign key (gel_evg_uid) references stt_email_variable_group (evg_uid),
  gel_wev_uid int not null,
  constraint gel_wev_uif_fk foreign key (gel_wev_uid) references stt_workflow_event (wev_uid)
) ;

create table stt_email_variable  (
  evr_uid int primary key auto_increment,
  evr_evg_uid int not null,
  constraint evr_evg_uid_fk foreign key (evr_evg_uid) references stt_email_variable_group (evg_uid),
  evr_name varchar(50) not null,
  err_default_value varchar(1000),
  evr_format varchar(100),
  evr_display_name varchar(100) not null
);

create table stt_webpage  (
  wbp_uid int primary key auto_increment,
  wbp_name varchar(150),
  wbp_body text,
  wbp_code varchar(150)
);

create table stt_announcement  (
  ann_uid int primary key auto_increment,
  ann_created datetime,
  ann_title varchar(150),
  ann_content text
);

create table stt_order  (
  ord_uid int primary key auto_increment,
  ord_usr_uid int not null,
  constraint ord_usr_uid_fk foreign key (ord_usr_uid) references stt_user (usr_uid),
  ord_status enum('PLACED','SHIPPED','RETURNED','READY_FOR_PICKUP', 'CANCELLED', 'DELIVERED') DEFAULT NULL,
  ord_shipping_address1 varchar(150), -- all shipping address fields nullable in case they are picking it up in the store
  ord_shipping_address2 varchar(100),
  ord_shipping_city varchar(100),
  ord_shipping_state varchar(2),
  ord_shipping_zip varchar(5),
  ord_transaction_id varchar(10),
  ord_tracking_number varchar(35),
  ord_in_store_pickup tinyint(1),
  ord_paid tinyint(1)
);

create table stt_event  (
  evn_uid int primary key auto_increment,
  evn_date datetime not null,
  evn_title varchar(200),
  evn_description text,
  evn_player_limit int,
  evn_number_in_store_users int
);

create table stt_inventory  (
  inv_uid int primary key auto_increment,
  inv_price double,
  inv_number_in_stock int,
  inv_name varchar(200),
  inv_image varchar(300),
  inv_mcr_uid int,
  constraint inv_mcr_uid_fk foreign key (inv_mcr_uid) references stt_magic_card (mcr_uid),
  inv_ycr_uid int,
  constraint inv_ycr_uid_fk foreign key (inv_ycr_uid) references stt_yugioh_card (ycr_uid),
  inv_evn_uid int,
  constraint inv_evn_uid_fk foreign key (inv_evn_uid) references stt_event (evn_uid),
  inv_pre_order tinyint(1),
  inv_description text
);

create table stt_inventory_item  (
  iit_uid int primary key auto_increment,
  iit_ord_uid int not null,
  constraint iit_ord_uid_fk foreign key (iit_ord_uid) references stt_order (ord_uid),
  iit_price double not null,
  iit_name varchar(200) not null,
  iit_image varchar(300) not null,
  iit_mcr_uid int,
  constraint iit_mcr_uid_fk foreign key (iit_mcr_uid) references  stt_inventory (inv_uid),
  iit_ycr_uid int,
  constraint iit_ycr_uid_fk foreign key (iit_ycr_uid) references stt_yugioh_card (ycr_uid),
  iit_evn_uid int,
  constraint iit_evn_uid_fk foreign key (iit_evn_uid) references stt_event (evn_uid),
  iit_pre_order tinyint(1),
  iit_description text not null
);

create table stt_stock_notification  (
  snt_uid int primary key auto_increment,
  snt_email varchar(200),
  snt_inv_uid int not null,
  constraint snt_inv_uid_fk foreign key (snt_inv_uid) references stt_inventory (inv_uid)
);

