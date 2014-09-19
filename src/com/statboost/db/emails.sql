insert into stt_email_template (
  etm_name
  ,etm_body
) VALUES (
  'Standard Template'  -- etm_name - IN varchar(100)
  ,''  -- etm_body - IN text
);

insert into stt_email_variable_group (
  evg_name
) VALUES (
  'Order'  -- evg_name - IN varchar(50)
);

insert into stt_email (
  eml_name
  ,eml_subject
  ,eml_from
  ,eml_to
  ,eml_body
  ,eml_etm_uid
  ,eml_evg_uid
) VALUES (
  'Order Placed'  -- eml_name - IN varchar(100)
  ,'Your order has been placed.'  -- eml_subject - IN varchar(250)
  ,'${companyEmail}'  -- eml_from - IN varchar(1000)
  ,'${}'  -- eml_to - IN varchar(1000)
  ,'You placed an order, yay!'  -- eml_body - IN text
  ,(select etm_uid from stt_email_template where etm_name = 'Standard Template')   -- eml_etm_uid - IN int(11)
  ,(select evg_uid FROM stt_email_variable_group where evg_name = 'Order')   -- eml_evg_uid - IN int(11)
);

insert into stt_email_variable (
  evr_evg_uid
  ,evr_name
  ,err_default_value
  ,evr_format
  ,evr_display_name
) VALUES (
  (select evg_uid from stt_email_variable_group where evg_name='Order')   -- evr_evg_uid - IN int(11)
  ,'order.billingName'  -- evr_name - IN varchar(50)
  ,'Customer'  -- err_default_value - IN varchar(1000)
  ,'NONE'  -- evr_format - IN varchar(100)
  ,'Order - Billing Name'  -- evr_display_name - IN varchar(100)
);

insert into stt_workflow_event (  
  wev_name
  ,wev_description
  ,wev_code
) VALUES (   
  'Order Placed'  -- wev_name - IN varchar(100)
  ,'This will be triggered when a customer has successfullly place and paid for an order.'  -- wev_description - IN varchar(1000)
  ,'ORDER_PLACED'  -- wev_code - IN varchar(100)
);

insert into stt_email_workflow_event_link (  
  ewe_eml_uid
  ,ewe_wev_uid
) VALUES (   
  (select eml_uid from stt_email where eml_name = 'Order Placed')   -- ewe_eml_uid - IN int(11)
  ,(select wev_uid from stt_workflow_event where wev_name = 'Order Placed')   -- ewe_wev_uid - IN int(11)
);