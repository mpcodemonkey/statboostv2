create table stt_email_template  (
  etm_uid int primary key auto_increment,
  etm_name varchar(100) not null,
  etm_body text not null
);

create table stt_variable_group  (
  vgr_uid int primary key auto_increment,
  vrg_name varchar(50)
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
  constraint eml_etm_uid_fk foreign key (eml_etm_uid) references stt_variable_group(vgr_uid)
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
  constraint ewe_eml_uid_fk foreign key (ewe_eml_uid) references stt_email,
  ewe_wev_uid int not null,
  constraint ewe_wev_uid_fk foreign key (ewe_wev_uid) references stt_workflow_event
) ;

create table stt_group_event_link  (
  gel_uid int primary key auto_increment,
  gel_vgr_uid int not null,
  constraint gel_vgr_uid_fk foreign key (gel_vgr_uid) references stt_variable_group,
  gel_wev_uid int not null,
  constraint gel_wev_uif_fk foreign key (gel_wev_uid) references stt_workflow_event
) ;

create table stt_variable  (
  vrb_uid int primary key auto_increment,
  vrb_vrg_uid int not null,
  constraint vrb_vrg_uid_fk foreign key (vrb_vrg_uid) references stt_variable_group,
  vrb_name varchar(50) not null,
  vrb_default_value varchar(1000),
  vrb_format varchar(100),
  vrb_display_name varchar(100) not null
)