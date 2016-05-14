 -- 插入权限
 
INSERT INTO `mip_sys_authority` VALUES ('0d744b4b-dabf-4e2a-a8d3-cc9d54e7','上传',4,4,'上传'),('1136baad-ed57-49ee-923e-051a4b4e','修改',2,2,'修改权限'),('3c0293a4-d086-43d1-b738-21565458','删除',3,3,'删除费用'),('47d32114-8468-44fd-bfcc-b3329de1','访问',1,1,'访问权限'),('d402f069-5e1c-4e23-ac12-e2c1e2ed','下载',5,5,'下载');
 
--  插入用户组
 
INSERT INTO `mip_sys_group` VALUES ('b53aa32e-0dac-496d-be60-0b92cd4b1c1d','ga','ga',null,NULL),('21ce83d4-a163-4b58-9df0-35e00c9d363d','aa','aa','b53aa32e-0dac-496d-be60-0b92cd4b1c1d',NULL),('256db412-d249-41f5-9d17-218fb7463934','dddd12','ddddd12','b53aa32e-0dac-496d-be60-0b92cd4b1c1d',NULL),('4da0465b-3263-4f0b-9356-46a4766cccf7','营运中心','ssss','21ce83d4-a163-4b58-9df0-35e00c9d363d',NULL),('8bc546fc-d028-4f7d-8842-d25c98e91c57','研发中心','研发中心','b53aa32e-0dac-496d-be60-0b92cd4b1c1d','1'),('8ddb8840-5b28-4ec7-8693-34732de38f35','cc','cc','b53aa32e-0dac-496d-be60-0b92cd4b1c1d',NULL),('e3f7c3af-3aa6-42d4-bd0a-1449e920b5fb','nimei3','jixunimei','21ce83d4-a163-4b58-9df0-35e00c9d363d',NULL);
 
-- 插入资源

 
INSERT INTO `mip_sys_resource` VALUES ('6af712c6-11d2-4168-ad1f-35fad575','all','/**',1,31),('admin','res_all','/**.*',1,10),('ae6ef1fa-8688-45e4-9745-66d0c27a','admin','/admin/**',1,15),('f3b2418b-aab7-4bff-a9d1-cdc58938d1f5','auth','/auth',1,15),('user','res_user','/user/lldds',1,15);
 
-- 插入资源权限关联关系
 
INSERT INTO `mip_sys_resource_authority` VALUES ('0f28b758-b288-414b-b719-e241c6145cab','6af712c6-11d2-4168-ad1f-35fad575','47d32114-8468-44fd-bfcc-b3329de1','/access'),('1a552267-4fcf-43a6-99ef-4655d8772a6b','user','0d744b4b-dabf-4e2a-a8d3-cc9d54e7',NULL),('1f17ceef-4446-41bc-bcea-18395e7ab7b8','ae6ef1fa-8688-45e4-9745-66d0c27a','47d32114-8468-44fd-bfcc-b3329de1','/admin/acc'),('2e10265a-dd65-4d5a-a2e7-25c1bdc78764','6af712c6-11d2-4168-ad1f-35fad575','3c0293a4-d086-43d1-b738-21565458','/del'),('493951c7-9c40-4395-873a-ca79fd8230c8','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5','1136baad-ed57-49ee-923e-051a4b4e','/md'),('4cea654d-bdaa-40f9-936a-5e95c82fe4f9','ae6ef1fa-8688-45e4-9745-66d0c27a','3c0293a4-d086-43d1-b738-21565458','/admin/del'),('5d169232-ed68-473a-9312-8d1146ad301b','user','1136baad-ed57-49ee-923e-051a4b4e',NULL),('5ee50538-44ea-4d44-adf9-5d4c3e918c45','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5','3c0293a4-d086-43d1-b738-21565458','/del'),('6289bda4-bf80-409a-b150-3f3db163a74c','admin','1136baad-ed57-49ee-923e-051a4b4e',NULL),('67ecac77-d81a-427e-ab66-54ea58a565be','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5','0d744b4b-dabf-4e2a-a8d3-cc9d54e7','/up'),('767a310f-1b5b-49a6-b181-49e5a743d61c','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5','47d32114-8468-44fd-bfcc-b3329de1','/acc'),('78bf6dfe-41e6-4c11-b9cc-e13e918d9926','user','3c0293a4-d086-43d1-b738-21565458',NULL),('844aa4f9-0a5b-47ca-ad56-255cc78756aa','ae6ef1fa-8688-45e4-9745-66d0c27a','1136baad-ed57-49ee-923e-051a4b4e','/admin/md'),('a851ff97-2fd6-4c1f-913c-1f7a7f60d588','6af712c6-11d2-4168-ad1f-35fad575','1136baad-ed57-49ee-923e-051a4b4e','/md'),('b3936136-1e8f-4e99-9382-2f3afcbd7c6a','6af712c6-11d2-4168-ad1f-35fad575','0d744b4b-dabf-4e2a-a8d3-cc9d54e7','upload'),('c407dcda-2a5d-4d3e-878d-260aa28488a4','6af712c6-11d2-4168-ad1f-35fad575','d402f069-5e1c-4e23-ac12-e2c1e2ed','/download'),('d877304b-2c20-4419-81ec-3f5e5f8cd32d','ae6ef1fa-8688-45e4-9745-66d0c27a','0d744b4b-dabf-4e2a-a8d3-cc9d54e7','/admin/up'),('e74a0f16-39b5-41aa-9c31-6941f4ba911e','user','d402f069-5e1c-4e23-ac12-e2c1e2ed',NULL),('ed26748c-7e0c-4583-a17a-4ea017fd6c2c','admin','0d744b4b-dabf-4e2a-a8d3-cc9d54e7',NULL),('f63eeda8-5e08-4600-a1d8-a698780b760c','user','47d32114-8468-44fd-bfcc-b3329de1',NULL);
 
--  插入用户角色

 
INSERT INTO `mip_sys_role` VALUES ('1cf35101-fde7-4a93-901c-9a8e7e9d','admin','desceee'),('7733864a-4e4f-402c-ae7e-52af8fb905f5','NX','nx'),('a7d2ddd9-a750-4978-9480-df8c7e20362c','bb','bb'),('admin','ROLE_ADMIN','管理员'),('c0250820-5434-459d-896e-781cde0bf291','ee','ee'),('c8fba4b0-9f1a-49f0-9c59-2ab9efe564f2','sss','ssss'),('dffd209d-0a0e-4aa7-a323-785d6129','endpoint','终端用户'),('user','ROLE_USER','普通用户');
 
 
-- 插入角色资源关联关系

INSERT INTO `mip_sys_role_resource` VALUES ('04bb8304-7240-40a6-8210-31bb2452b783','user','ae6ef1fa-8688-45e4-9745-66d0c27a',15),('0e8c10d1-6127-41ce-bd29-71e7de83846b','c0250820-5434-459d-896e-781cde0bf291','6af712c6-11d2-4168-ad1f-35fad575',14),('1474b5cb-c3f0-4d9b-876a-1d5f05f11687','dffd209d-0a0e-4aa7-a323-785d6129','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5',15),('1d9590ee-cace-46f9-945c-b764cb4e6188','a7d2ddd9-a750-4978-9480-df8c7e20362c','admin',10),('1f318891-df1f-4299-aea6-2dfa5975708e','1cf35101-fde7-4a93-901c-9a8e7e9d','admin',10),('2259eb86-19ad-481a-8380-aaba5acf10cf','user','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5',15),('270ba241-d8bd-4df9-b93b-5f3684f21356','a7d2ddd9-a750-4978-9480-df8c7e20362c','6af712c6-11d2-4168-ad1f-35fad575',14),('2ea2f688-b559-4e3c-b87b-1749a8a36912','7733864a-4e4f-402c-ae7e-52af8fb905f5','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5',15),('30fefaaf-fa41-4744-9f9e-291facc4d0e7','admin','ae6ef1fa-8688-45e4-9745-66d0c27a',15),('3585d3ba-1902-4754-9a8c-72b70a541e10','dffd209d-0a0e-4aa7-a323-785d6129','admin',10),('3c0185c6-8db6-442f-8650-e00f74e01575','dffd209d-0a0e-4aa7-a323-785d6129','ae6ef1fa-8688-45e4-9745-66d0c27a',0),('44777005-3edc-4b75-a7bb-8a0db156b040','user','admin',10),('539a2b90-be83-48df-82a6-0b522ad71505','dffd209d-0a0e-4aa7-a323-785d6129','user',15),('667817a9-b25d-4ce9-b72d-cf2cc18ec648','dffd209d-0a0e-4aa7-a323-785d6129','6af712c6-11d2-4168-ad1f-35fad575',6),('67b91c31-86f9-478a-b8f8-e8b7ecf9922b','1cf35101-fde7-4a93-901c-9a8e7e9d','user',15),('6c4fefe6-e6bd-4abc-b10b-1104a353f4ba','admin','6af712c6-11d2-4168-ad1f-35fad575',6),('704345bc-ffc0-4a67-bd69-225fdc71c4fc','admin','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5',15),('763f7378-8b40-4a42-bb61-987b18cdc755','7733864a-4e4f-402c-ae7e-52af8fb905f5','user',15),('7a316f9a-7ab6-413f-81bf-44ae6a57ff1c','c0250820-5434-459d-896e-781cde0bf291','admin',10),('ac7cbd5b-1504-4227-8624-6058853ba441','admin','user',15),('c7ffeaec-ccdd-4ddc-a9cd-b3792e7d94c1','user','6af712c6-11d2-4168-ad1f-35fad575',6),('c9f8b350-f65a-485d-aecf-666b1e707d10','c8fba4b0-9f1a-49f0-9c59-2ab9efe564f2','admin',10),('d24c10b5-a022-4ab1-b326-9aecb8a53f57','7733864a-4e4f-402c-ae7e-52af8fb905f5','6af712c6-11d2-4168-ad1f-35fad575',6),('e7cd4008-4b88-45b0-bbd3-1771b90ec68d','admin','admin',10),('ed4518f4-35ea-4588-b5f0-e2607da45769','1cf35101-fde7-4a93-901c-9a8e7e9d','6af712c6-11d2-4168-ad1f-35fad575',14),('effa2a21-9481-4934-b304-4e450ed07d20','1cf35101-fde7-4a93-901c-9a8e7e9d','ae6ef1fa-8688-45e4-9745-66d0c27a',15),('f2831ac3-0f2e-4b5b-87e1-9ca3996e8691','7733864a-4e4f-402c-ae7e-52af8fb905f5','admin',10),('f51f410c-4f3e-476e-acc0-0b39755f3418','1cf35101-fde7-4a93-901c-9a8e7e9d','f3b2418b-aab7-4bff-a9d1-cdc58938d1f5',15),('ffb6b2d3-fe52-4098-a6e7-00adf30074c7','c8fba4b0-9f1a-49f0-9c59-2ab9efe564f2','6af712c6-11d2-4168-ad1f-35fad575',6),('user','user','user',15);
 
-- 插入用户
 
INSERT INTO `mip_sys_user` VALUES ('admin','admin','admin',1),('dcb195e4-a05c-4d1e-aad4-cef35f8f4cb7','ua','ua',1),('user','user','user',1);
 
-- 插入用户与组关联关系

INSERT INTO `mip_sys_user_group` VALUES ('57b4bd20-c5b5-4a6d-bbb2-2b9ddd4b05c7','admin','21ce83d4-a163-4b58-9df0-35e00c9d363d'),('257ba294-12ca-4ecf-9891-0ba056b0b44d','dcb195e4-a05c-4d1e-aad4-cef35f8f4cb7','21ce83d4-a163-4b58-9df0-35e00c9d363d'),('1c626be4-0531-46df-af01-8b8adb0565b9','user','21ce83d4-a163-4b58-9df0-35e00c9d363d'),('b04c7052-6a42-430f-a9de-238719091331','admin','256db412-d249-41f5-9d17-218fb7463934'),('57a29497-0270-4f3f-9e0f-e70c5ec03111','user','256db412-d249-41f5-9d17-218fb7463934'),('da1f91f7-2b75-4fda-931f-37c689078fd3','admin','4da0465b-3263-4f0b-9356-46a4766cccf7'),('42a267ff-256c-44e3-9e69-620f148d8674','admin','8bc546fc-d028-4f7d-8842-d25c98e91c57'),('3764abed-fdc9-4957-992e-51bcad6bc37f','user','8bc546fc-d028-4f7d-8842-d25c98e91c57'),('be976fd4-35d5-4da0-940f-fb3b873c9af9','admin','8ddb8840-5b28-4ec7-8693-34732de38f35'),('009d34fe-30c2-4ec9-a5dd-b0e92e310fbf','admin','b53aa32e-0dac-496d-be60-0b92cd4b1c1d'),('fd5dc2d9-70c0-483e-8ba5-8b18fa58d11d','admin','e3f7c3af-3aa6-42d4-bd0a-1449e920b5fb');
 
-- 插入用户角色关联关系

 
INSERT INTO `mip_sys_user_role` VALUES ('1e41dae8-7933-4ce6-acbf-76b3f3b1fede','admin','1cf35101-fde7-4a93-901c-9a8e7e9d'),('1b920df9-3572-478b-9d6e-bb01fdf0fa42','dcb195e4-a05c-4d1e-aad4-cef35f8f4cb7','1cf35101-fde7-4a93-901c-9a8e7e9d'),('52e6cecb-f92c-48ea-84d0-daa0448d1255','user','1cf35101-fde7-4a93-901c-9a8e7e9d'),('4b3528c6-388a-4c27-b76d-6b25598fa44d','admin','7733864a-4e4f-402c-ae7e-52af8fb905f5'),('aedb79f9-d1cc-4f76-8635-c78cdb142016','dcb195e4-a05c-4d1e-aad4-cef35f8f4cb7','7733864a-4e4f-402c-ae7e-52af8fb905f5'),('c9afeade-ddeb-400b-9a2d-f307c15e416d','user','7733864a-4e4f-402c-ae7e-52af8fb905f5'),('1fc1bf3f-ac93-4d17-ac9c-9de4d1eddaa5','admin','a7d2ddd9-a750-4978-9480-df8c7e20362c'),('f9f49579-fe1f-417b-ae63-9038d4ee37af','dcb195e4-a05c-4d1e-aad4-cef35f8f4cb7','a7d2ddd9-a750-4978-9480-df8c7e20362c'),('sasdaadsa3qe4e','admin','admin'),('8e354f2c-6c01-42ed-839d-500c754d6ca1','dcb195e4-a05c-4d1e-aad4-cef35f8f4cb7','admin'),('f2aaf842-c0e2-4290-9d05-0668c13096dc','user','admin'),('83ae9423-9028-4c53-b72d-5b03ade7aef4','admin','c0250820-5434-459d-896e-781cde0bf291'),('bae335fb-4011-49ba-8625-a7c65c975126','admin','c8fba4b0-9f1a-49f0-9c59-2ab9efe564f2'),('4694ba76-3be8-4a83-aed5-91c04fa1b483','admin','dffd209d-0a0e-4aa7-a323-785d6129'),('ae017630-592b-47f9-a646-2e8927b4bc75','admin','user'),('user','user','user');
 