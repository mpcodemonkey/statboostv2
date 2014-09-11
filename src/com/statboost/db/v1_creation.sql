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
  eml_vgr_uid int not null,
  constraint eml_vgr_uid_fk foreign key (eml_vgr_uid) references stt_email_variable_group(evg_uid)
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

create table stt_variable  (
  vrb_uid int primary key auto_increment,
  vrb_evg_uid int not null,
  constraint vrb_evg_uid_fk foreign key (vrb_evg_uid) references stt_email_variable_group (evg_uid),
  vrb_name varchar(50) not null,
  vrb_default_value varchar(1000),
  vrb_format varchar(100),
  vrb_display_name varchar(100) not null
);

create table stt_user  (
  usr_uid int primary key auto_increment,
  usr_role ENUM('CUSTOMER', 'ADMIN', 'EMPLOYEE', 'GUEST') not null,
  usr_email varchar(150) not null, //acts as a username
  usr_password varchar(16) not null,
  usr_address_1 varchar(150),
  usr_address_2 varchar(100),
  usr_city varchar(100),
  usr_state varchar(2),
  usr_zip varchar(5),
  usr_first_name varchar(100),
  usr_last_name varchar(100),
  usr_phone varchar(10),
  usr_newsletter boolean,
  usr_active boolean,
  usr_dci_number varchar(10)
);


