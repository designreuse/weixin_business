INSERT INTO mip_sys_authority(id,name,show_index,loc_index,descp) VALUES ('0d744b4b-dabf-4e2a-a8d3-cc9d54e7','上传',4,4,'上传');
INSERT INTO mip_sys_authority(id,name,show_index,loc_index,descp) VALUES ('1136baad-ed57-49ee-923e-051a4b4e','修改',2,2,'修改权限');
INSERT INTO mip_sys_authority(id,name,show_index,loc_index,descp) VALUES ('3c0293a4-d086-43d1-b738-21565458','删除',3,3,'删除费用');
INSERT INTO mip_sys_authority(id,name,show_index,loc_index,descp) VALUES ('47d32114-8468-44fd-bfcc-b3329de1','访问',1,1,'访问权限');
INSERT INTO mip_sys_authority(id,name,show_index,loc_index,descp) VALUES ('d402f069-5e1c-4e23-ac12-e2c1e2ed','下载',5,5,'下载');

INSERT INTO mip_sys_group(id,group_name,group_desc,parent_id,status) VALUES ('8bc546fc-d028-4f7d-8842-d25c98e91c57','研发中心','研发中心',NULL,'1');
INSERT INTO mip_sys_group(id,group_name,group_desc,parent_id,status) VALUES ('0f3cce18-50b8-4e45-a519-da7692f861c7','研发中心-移动','mobile','8bc546fc-d028-4f7d-8842-d25c98e91c57','1');

INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('648b7a5a-734f-4dc2-8e36-643161e8','管理文件','asdasdas990-33k',2,63);
INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('6af712c6-11d2-4168-ad1f-35fad575','all','/**',1,127);
INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('ae6ef1fa-8688-45e4-9745-66d0c27a','admin','/admin/**',1,127);
INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('admin','res_all','/**.*',1,1);
INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('user','res_user','/user/**',1,111);
INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('e68313bc-569a-4068-b363-452fa87423f0','ppp','/ppp',1,NULL);
INSERT INTO mip_sys_resource(id,name,uri,type,permission) VALUES ('f3b2418b-aab7-4bff-a9d1-cdc58938d1f5','auth','/auth',1,NULL);

INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('085b7dd0-f42e-4558-8d92-90ec0638532c','6af712c6-11d2-4168-ad1f-35fad575','47d32114-8468-44fd-bfcc-b3329de1','/access');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('1a552267-4fcf-43a6-99ef-4655d8772a6b','user','0d744b4b-dabf-4e2a-a8d3-cc9d54e7',NULL);
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('1f17ceef-4446-41bc-bcea-18395e7ab7b8','ae6ef1fa-8688-45e4-9745-66d0c27a','47d32114-8468-44fd-bfcc-b3329de1','/up');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('2e10265a-dd65-4d5a-a2e7-25c1bdc78764','6af712c6-11d2-4168-ad1f-35fad575','3c0293a4-d086-43d1-b738-21565458','/del');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('4cea654d-bdaa-40f9-936a-5e95c82fe4f9','ae6ef1fa-8688-45e4-9745-66d0c27a','3c0293a4-d086-43d1-b738-21565458','/sss');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('5d169232-ed68-473a-9312-8d1146ad301b','user','1136baad-ed57-49ee-923e-051a4b4e',NULL);
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('78bf6dfe-41e6-4c11-b9cc-e13e918d9926','user','3c0293a4-d086-43d1-b738-21565458',NULL);
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('844aa4f9-0a5b-47ca-ad56-255cc78756aa','ae6ef1fa-8688-45e4-9745-66d0c27a','1136baad-ed57-49ee-923e-051a4b4e','/sss');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('a851ff97-2fd6-4c1f-913c-1f7a7f60d588','6af712c6-11d2-4168-ad1f-35fad575','1136baad-ed57-49ee-923e-051a4b4e','/md');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('d877304b-2c20-4419-81ec-3f5e5f8cd32d','ae6ef1fa-8688-45e4-9745-66d0c27a','0d744b4b-dabf-4e2a-a8d3-cc9d54e7','/up');
INSERT INTO mip_sys_resource_authority(id,resource_id,authority_id,url) VALUES ('f63eeda8-5e08-4600-a1d8-a698780b760c','user','47d32114-8468-44fd-bfcc-b3329de1',NULL);

INSERT INTO mip_sys_role(id,name,description) VALUES ('1cf35101-fde7-4a93-901c-9a8e7e9d','admin','管理员');
INSERT INTO mip_sys_role(id,name,description) VALUES ('322faf2c-afac-4fbd-86f9-0ccf37e4','web','web用户');
INSERT INTO mip_sys_role(id,name,description) VALUES ('dffd209d-0a0e-4aa7-a323-785d6129','endpoint','终端用户');
INSERT INTO mip_sys_role(id,name,description) VALUES ('admin','ROLE_ADMIN','管理员');
INSERT INTO mip_sys_role(id,name,description) VALUES ('user','ROLE_USER','普通用户');
INSERT INTO mip_sys_role(id,name,description) VALUES ('69aa2efb-afd6-4bfa-adb4-d648804ea93f','role_a','assss');
INSERT INTO mip_sys_role(id,name,description) VALUES ('7733864a-4e4f-402c-ae7e-52af8fb905f5','NX','nx');

INSERT INTO mip_sys_role_resource(id,role_id,resource_id,permission) VALUES ('1fb24af7-9b8e-4fb9-b4ce-7d88e56a','1cf35101-fde7-4a93-901c-9a8e7e9d','6af712c6-11d2-4168-ad1f-35fad575',127);
INSERT INTO mip_sys_role_resource(id,role_id,resource_id,permission) VALUES ('5f5b2a50-702f-4d1c-a111-6efa1ec7','322faf2c-afac-4fbd-86f9-0ccf37e4','648b7a5a-734f-4dc2-8e36-643161e8',63);
INSERT INTO mip_sys_role_resource(id,role_id,resource_id,permission) VALUES ('admin','admin','admin',1);
INSERT INTO mip_sys_role_resource(id,role_id,resource_id,permission) VALUES ('user','user','user',1);

INSERT INTO mip_sys_user(id,username,password,status) VALUES ('admin','admin','admin',1);
INSERT INTO mip_sys_user(id,username,password,status) VALUES ('user','user','user',1);

INSERT INTO mip_sys_app_type(id,name,description) VALUES ('0','默认分类','包含所有未分类的应用');