CREATE TABLE `magic_card` (
  `CardName` varchar(150) NOT NULL,
  `CardNames` varchar(150) DEFAULT NULL,
  `SetID` char(3) NOT NULL,
  `ManaCost` varchar(50) DEFAULT NULL,
  `CMC` double DEFAULT NULL,
  `Colors` varchar(100) DEFAULT NULL,
  `CardType` varchar(500) DEFAULT NULL,
  `SuperTypes` varchar(100) DEFAULT NULL,
  `SubTypes` varchar(100) DEFAULT NULL,
  `Types` varchar(100) DEFAULT NULL,
  `Rarity` varchar(50) DEFAULT NULL,
  `Text` varchar(1000) DEFAULT NULL,
  `Flavor` varchar(500) DEFAULT NULL,
  `Artist` varchar(50) DEFAULT NULL,
  `CardNumber` varchar(5) DEFAULT NULL,
  `CardPower` varchar(5) DEFAULT NULL,
  `Toughness` varchar(5) DEFAULT NULL,
  `Loyalty` int(11) DEFAULT NULL,
  `Layout` varchar(50) DEFAULT NULL,
  `Multiverseid` int(11) NOT NULL,
  `Variations` varchar(100) DEFAULT NULL,
  `ImageName` varchar(150) NOT NULL,
  `Watermark` varchar(50) DEFAULT NULL,
  `Border` varchar(15) DEFAULT NULL,
  `Hand` int(11) DEFAULT NULL,
  `Life` int(11) DEFAULT NULL,
  `mgc_uid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`mgc_uid`),
  KEY `SetID` (`SetID`),
  CONSTRAINT `magic_card_ibfk_1` FOREIGN KEY (`SetID`) REFERENCES `magic_set` (`CodeID`)
) ENGINE=InnoDB AUTO_INCREMENT=23621 DEFAULT CHARSET=utf8;

alter table magic_card change Cardname mcr_name varchar(150);
alter table magic_card change CardNames mcr_names varchar(150);
alter table magic_card change SetID mcr_set_id char(3);
alter table magic_card change ManaCost mcr_mana_cost varchar(50);
alter table magic_card change CMC mcr_cmc double;
alter table magic_card change Colors mcr_colors varchar(100);
alter table magic_card change CardType mcr_type varchar(500);
alter table magic_card change SuperTypes mcr_super_types varchar(100);
alter table magic_card change SubTypes mcr_sub_types varchar(100);
alter table magic_card change Types mcr_types varchar(100);
alter table magic_card change Rarity mcr_rarity varchar(50);
alter table magic_card change Text mcr_text varchar(1000);
alter table magic_card change Flavor mcr_flavor varchar(500);
alter table magic_card change Artist mcr_artist varchar(50);
alter table magic_card change CardNumber mcr_number varchar(5);
alter table magic_card change CardPower mcr_power varchar(5);
alter table magic_card change Toughness mcr_toughness varchar(5);
alter table magic_card change Loyalty mcr_loyalty int(11);
alter table magic_card change Layout mcr_layout varchar(50);
alter table magic_card change Multiverseid mcr_multiverse_id int(11);
alter table magic_card change Variations mcr_variations varchar(100);
alter table magic_card change ImageName mcr_image_name varchar(150);
alter table magic_card change Watermark mcr_watermark varchar(50);
alter table magic_card change Border mcr_border varchar(15);
alter table magic_card change Hand mcr_hand int(11);
alter table magic_card change Life mcr_life int(11);
alter table magic_card change mgc_uid mcr_uid int(11);

CREATE TABLE `magic_set` (
  `SetName` varchar(100) NOT NULL,
  `CodeID` char(3) NOT NULL,
  `ReleaseDate` date DEFAULT NULL,
  `Border` varchar(20) DEFAULT NULL,
  `SetType` varchar(50) DEFAULT NULL,
  `Block` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`CodeID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter table magic_set change SetName mst_name varchar(100);
alter table magic_set change CodeID mst_uid char(3);
alter table magic_set change ReleaseDate mst_release_date date;
alter table magic_set change Border mst_border varchar(20);
alter table magic_set change SetType mst_type varchar(50);
alter table magic_set change Block mst_block varchar(50);

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('Admin','Employee','Customer') NOT NULL DEFAULT 'Customer',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

alter table user change user_id usr_uid int(11);
alter table user change first_name usr_first_name varchar(50);
alter table user change last_name usr_last_name varchar(50);
alter table user change email usr_email varchar(255);
alter table user change password usr_password varchar(255);
alter table user change role usr_role enum('Admin','Employee','Customer', 'Guest') ;

CREATE TABLE `yugioh_card` (
  `Id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `Name` varchar(255) NOT NULL,
  `CardType` varchar(50) DEFAULT NULL,
  `Attribute` varchar(25) DEFAULT NULL,
  `Type__c` varchar(25) DEFAULT NULL,
  `Level__c` int(11) DEFAULT NULL,
  `ATK` int(11) DEFAULT NULL,
  `DEF` int(11) DEFAULT NULL,
  `Description` text,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5894 DEFAULT CHARSET=utf8;

alter table yugioh_card change Id ycr_uid int(10);
alter table yugioh_card change Name ycr_name varchar(255);
alter table yugioh_card change CardType ycr_card_type varchar(50);
alter table yugioh_card change Attribute ycr_attribute varchar(25);
alter table yugioh_card change Type__c ycr_type varchar(25);
alter table yugioh_card change Level__c ycr_level int(11);
alter table yugioh_card change ATK ycr_atk int(11);
alter table yugioh_card change DEF ycr_def int(11);
alter table yugioh_card change Description ycr_description text;

rename table magic_card to stt_magic_card;
rename table magic_set to stt_magic_set;
rename table user to stt_user;
rename table yugioh_card to stt_yugioh_card;

alter table stt_user add column usr_address_1 varchar(150);
alter table stt_user add column usr_address_2 varchar(100);
alter table stt_user add column usr_city varchar(100);
alter table stt_user add column usr_state varchar(2);
alter table stt_user add column usr_zip varchar(5);
alter table stt_user add column usr_phone varchar(10);
alter table stt_user add column usr_newsletter boolean;
alter table stt_user add column usr_active boolean;
alter table stt_user add column usr_dci_number varchar(10);
