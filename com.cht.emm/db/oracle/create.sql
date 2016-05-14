create table MIP_SYS_TOKEN
(
  TOKEN_ID    VARCHAR2(36) not null,
  CREATE_TIME TIMESTAMP(6) not null,
  UPDATE_TIME TIMESTAMP(6) not null,
  USER_NAME   VARCHAR2(50) not null,
  DEVICE_ID   VARCHAR2(50)
);
alter table MIP_SYS_TOKEN
  add constraint PK_MIP_SYS_TOKEN_ID primary key (ID)
  using index ;

create table MIP_SYS_GROUP
(
  ID                VARCHAR2(36) not null,
  GROUP_NAME        VARCHAR2(45) not null,
  GROUP_DESC        VARCHAR2(45),
  PARENT_ID         VARCHAR2(36),
  STATUS            VARCHAR2(45),
  DELETED           NUMBER(3,0) default 0
);
comment on table MIP_SYS_GROUP
  is '用户组表';
comment on column MIP_SYS_GROUP.ID
  is '用户组标识';
comment on column MIP_SYS_GROUP.GROUP_NAME
  is '用户组名';
comment on column MIP_SYS_GROUP.GROUP_DESC
  is '用户组描述';
comment on column MIP_SYS_GROUP.PARENT_ID
  is '上级用户组标识';
comment on column MIP_SYS_GROUP.STATUS
  is '用户组状态';
comment on column MIP_SYS_GROUP.DELETED
  is '删除标志位';
alter table MIP_SYS_GROUP
  add constraint PK_MIP_SYS_GROUP_ID primary key (ID)
  using index ;

create table MIP_SYS_USER
(
  ID                 VARCHAR2(36) not null,
  USERNAME           VARCHAR2(50) not null,
  PASSWORD           VARCHAR2(50) not null,
  STATUS          	 NUMBER(3,0),
  DELETED            NUMBER(3,0) default 0
);
comment on table MIP_SYS_USER
  is '用户表';
comment on column MIP_SYS_USER.ID
  is '用户标识';
comment on column MIP_SYS_USER.USERNAME
  is '用户名';
comment on column MIP_SYS_USER.PASSWORD
  is '用户密码';
comment on column MIP_SYS_USER.STATUS
  is '用户状态';
comment on column MIP_SYS_USER.DELETED
  is '删除标志位';
alter table MIP_SYS_USER
  add constraint PK_MIP_SYS_USER_ID primary key (ID)
  using index ;
  
create table MIP_SYS_USER_DETAIL
(
  ID                  VARCHAR2(36) not null,
  USERALIAS           VARCHAR2(50),
  MOBILE              VARCHAR2(45),
  EMAIL          	  VARCHAR2(50),
  SEX                 NUMBER(1,0),
  CREATE_TIME         TIMESTAMP default sysdate not null,
  MODIFY_TIME         TIMESTAMP default sysdate not null,
  DELETED             NUMBER(3,0) default 0
);
comment on table MIP_SYS_USER_DETAIL
  is '用户表';
comment on column MIP_SYS_USER_DETAIL.ID
  is '用户标识';
comment on column MIP_SYS_USER_DETAIL.USERALIAS
  is '用户名';
comment on column MIP_SYS_USER_DETAIL.MOBILE
  is '用户密码';
comment on column MIP_SYS_USER_DETAIL.EMAIL
  is '用户状态';
comment on column MIP_SYS_USER_DETAIL.SEX
  is '用户状态';
comment on column MIP_SYS_USER_DETAIL.CREATE_TIME
  is '用户状态';
comment on column MIP_SYS_USER_DETAIL.MODIFY_TIME
  is '删除标志位';
comment on column MIP_SYS_USER_DETAIL.DELETED
  is '删除标志位';
alter table MIP_SYS_USER_DETAIL
  add constraint PK_MIP_SYS_USER_DETAIL_ID primary key (ID)
  using index ;
 
create table MIP_SYS_USER_GROUP
(
  ID       VARCHAR2(36) not null,
  USER_ID  VARCHAR2(36),
  GROUP_ID VARCHAR2(36)
);
comment on table MIP_SYS_USER_GROUP
  is '用户分组表';
comment on column MIP_SYS_USER_GROUP.ID
  is '用户分组标识';
comment on column MIP_SYS_USER_GROUP.USER_ID
  is '用户标识';
comment on column MIP_SYS_USER_GROUP.GROUP_ID
  is '分组标识';
alter table MIP_SYS_USER_GROUP
  add constraint PK_MIP_SYS_USER_GROUP_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_ROLE
(
  ID                 VARCHAR2(36) not null,
  NAME               VARCHAR2(50) not null,
  DESCRIPTION		 VARCHAR2(255),
  DELETED            NUMBER(3,0) default 0
);
comment on table MIP_SYS_ROLE
  is '角色表';
comment on column MIP_SYS_ROLE.ID
  is '角色标识';
comment on column MIP_SYS_ROLE.NAME
  is '角色名';
comment on column MIP_SYS_ROLE.DESCRIPTION
  is '描述';
comment on column MIP_SYS_ROLE.DELETED
  is '删除标志位';
alter table MIP_SYS_ROLE
  add constraint PK_MIP_SYS_ROLE_ID primary key (ID)
  using index ;  
  
create table MIP_SYS_RESOURCE
(
  ID                 VARCHAR2(36) not null,
  NAME               VARCHAR2(50) not null,
  URI                VARCHAR2(50),
  TYPE               NUMBER(3,0),
  PERMISSION         NUMBER(10,0),
  DELETED            NUMBER(3,0) default 0
);
comment on table MIP_SYS_RESOURCE
  is '资源表';
comment on column MIP_SYS_RESOURCE.ID
  is '资源标识';
comment on column MIP_SYS_RESOURCE.NAME
  is '资源名';
comment on column MIP_SYS_RESOURCE.URI
  is '资源URI';
comment on column MIP_SYS_RESOURCE.TYPE
  is '资源类型';
comment on column MIP_SYS_RESOURCE.PERMISSION
  is '资源权限';
comment on column MIP_SYS_RESOURCE.DELETED
  is '删除标志位';
alter table MIP_SYS_RESOURCE
  add constraint PK_MIP_SYS_RESOURCE_ID primary key (ID)
  using index ;  
  
create table MIP_SYS_USER_ROLE
(
  ID                 VARCHAR2(36) not null,
  USER_ID            VARCHAR2(36) not null,
  ROLE_ID            VARCHAR2(36) not null
);
comment on table MIP_SYS_USER_ROLE
  is '用户角色表';
comment on column MIP_SYS_USER_ROLE.ID
  is '唯一标识';
comment on column MIP_SYS_USER_ROLE.USER_ID
  is '用户标识';
comment on column MIP_SYS_USER_ROLE.ROLE_ID
  is '角色标识';
alter table MIP_SYS_USER_ROLE
  add constraint PK_MIP_SYS_USER_ROLE_ID primary key (ID)
  using index ;  
  
create table MIP_SYS_ROLE_RESOURCE
(
  ID                 VARCHAR2(36) not null,
  ROLE_ID            VARCHAR2(36),
  RESOURCE_ID        VARCHAR2(36),
  PERMISSION         NUMBER(10,0)
);
comment on table MIP_SYS_ROLE_RESOURCE
  is '角色资源表';
comment on column MIP_SYS_ROLE_RESOURCE.ID
  is '唯一标识';
comment on column MIP_SYS_ROLE_RESOURCE.ROLE_ID
  is '角色标识';
comment on column MIP_SYS_ROLE_RESOURCE.RESOURCE_ID
  is '资源标识';
comment on column MIP_SYS_ROLE_RESOURCE.PERMISSION
  is '资源权限';
alter table MIP_SYS_ROLE_RESOURCE
  add constraint PK_MIP_SYS_ROLE_RESOURCE_ID primary key (ID)
  using index ;  
  
create table MIP_SYS_AUTHORITY
(
  ID                 VARCHAR2(36) not null,
  NAME               VARCHAR2(50) not null,
  SHOW_INDEX         NUMBER(10,0),
  LOC_INDEX          NUMBER(3,0),
  DESCP              VARCHAR2(255),
  DELETED            NUMBER(3,0) default 0
);
comment on table MIP_SYS_AUTHORITY
  is '权限表';
comment on column MIP_SYS_AUTHORITY.ID
  is '权限标识';
comment on column MIP_SYS_AUTHORITY.NAME
  is '权限名称';
comment on column MIP_SYS_AUTHORITY.SHOW_INDEX
  is '权限显示序号';
comment on column MIP_SYS_AUTHORITY.LOCAL_INDEX
  is '权限本地序号';
comment on column MIP_SYS_AUTHORITY.DESCP
  is '权限描述';
comment on column MIP_SYS_AUTHORITY.DELETED
  is '删除标志位';
alter table MIP_SYS_AUTHORITY
  add constraint PK_MIP_SYS_AUTHORITY_ID primary key (ID)
  using index ;  
  
create table MIP_SYS_RESOURCE_AUTHORITY
(
  ID                 VARCHAR2(36) not null,
  RESOURCE_ID        VARCHAR2(36),
  AUTHORITY_ID       VARCHAR2(36),
  URL                VARCHAR2(256)
);
comment on table MIP_SYS_RESOURCE_AUTHORITY
  is '资源权限关联表';
comment on column MIP_SYS_RESOURCE_AUTHORITY.ID
  is '关联标识';
comment on column MIP_SYS_RESOURCE_AUTHORITY.RESOURCE_ID
  is '资源标识';
comment on column MIP_SYS_RESOURCE_AUTHORITY.AUTHORITY_ID
  is '权限标识';
comment on column MIP_SYS_RESOURCE_AUTHORITY.URL
  is '资源路径';
alter table MIP_SYS_RESOURCE_AUTHORITY
  add constraint PK_RESOURCE_AUTHORITY_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_DEVICE
(
  ID                 VARCHAR2(36) not null,
  NAME               VARCHAR2(50) not null,
  OS                 NUMBER(3,0),
  TYPE               NUMBER(3,0),
  STATUS             NUMBER(3,0)
);
comment on table MIP_SYS_DEVICE
  is '设备表';
comment on column MIP_SYS_DEVICE.ID
  is '设备标识';
comment on column MIP_SYS_DEVICE.NAME
  is '设备名称';
comment on column MIP_SYS_DEVICE.OS
  is '设备操作系统';
comment on column MIP_SYS_DEVICE.TYPE
  is '设备类型';
comment on column MIP_SYS_DEVICE.STATUS
  is '设备状态';
alter table MIP_SYS_DEVICE
  add constraint PK_MIP_SYS_DEVICE_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_DEVICE_DETAIL
(
  ID                 VARCHAR2(36) not null,
  DEVICE_DETAIL      CLOB,
  REGISTER_TIME      TIMESTAMP,
  DESTROY_TIME       TIMESTAMP,
  REMARK             VARCHAR2(128)
);
comment on table MIP_SYS_DEVICE_DETAIL
  is '设备详情表';
comment on column MIP_SYS_DEVICE_DETAIL.ID
  is '设备标识';
comment on column MIP_SYS_DEVICE_DETAIL.DEVICE_DETAIL
  is '设备详情';
comment on column MIP_SYS_DEVICE_DETAIL.REGISTER_TIME
  is '设备注册时间';
comment on column MIP_SYS_DEVICE_DETAIL.DESTROY_TIME
  is '设备注销时间';
comment on column MIP_SYS_DEVICE_DETAIL.REMARK
  is '备注信息';
alter table MIP_SYS_DEVICE_DETAIL
  add constraint PK_MIP_SYS_DEVICE_DETAIL_ID primary key (ID)
  using index ;
  
create table MIP_SYS_USER_DEVICE
(
  ID                 VARCHAR2(36) not null,
  USER_ID            VARCHAR2(36) not null,
  DEVICE_ID          VARCHAR2(36) not null,
  STATUS             NUMBER(3,0) not null
);
comment on table MIP_SYS_USER_DEVICE
  is '用户设备表';
comment on column MIP_SYS_USER_DEVICE.ID
  is '唯一标识';
comment on column MIP_SYS_USER_DEVICE.USER_ID
  is '用户标识';
comment on column MIP_SYS_USER_DEVICE.DEVICE_ID
  is '设备标识';
comment on column MIP_SYS_USER_DEVICE.STATUS
  is '审核状态';
alter table MIP_SYS_USER_DEVICE
  add constraint PK_MIP_SYS_USER_DEVICE_ID primary key (ID)
  using index ;
  
create table MIP_SYS_DEVICE_ORDER
(
  ID                 VARCHAR2(36) not null,
  DEVICE_ID          VARCHAR2(36) not null,
  DEVICE_ORDER       VARCHAR2(36) not null,
  CREATE_TIME        TIMESTAMP  default sysdate not null
);
comment on table MIP_SYS_DEVICE_ORDER
  is '设备指令表';
comment on column MIP_SYS_DEVICE_ORDER.ID
  is '唯一标识';
comment on column MIP_SYS_DEVICE_ORDER.DEVICE_ID
  is '设备标识';
comment on column MIP_SYS_DEVICE_ORDER.DEVICE_ORDER
  is '设备指令';
comment on column MIP_SYS_DEVICE_ORDER.CREATE_TIME
  is '指令创建时间';
alter table MIP_SYS_DEVICE_ORDER
  add constraint PK_MIP_SYS_DEVICE_ORDER_ID primary key (ID)
  using index ;
  
create table MIP_SYS_APPLICATION
(
  ID                 VARCHAR2(36) not null,
  APP_ID             VARCHAR2(36) not null,
  APP_NAME           VARCHAR2(32) not null,
  APP_TYPE           INTEGER not null,
  APP_VERSION_NAME   VARCHAR2(32) ,
  APP_VERSION_CODE   VARCHAR2(32) not null,
  APP_OS             INTEGER not null,
  APP_PKG_NAME       VARCHAR2(64)
);
comment on table MIP_SYS_APPLICATION
  is '应用表';
comment on column MIP_SYS_APPLICATION.ID
  is '唯一标识';
comment on column MIP_SYS_APPLICATION.APP_ID
  is '应用标识';
comment on column MIP_SYS_APPLICATION.APP_NAME
  is '应用名称';
comment on column MIP_SYS_APPLICATION.APP_TYPE
  is '应用类型';
comment on column MIP_SYS_APPLICATION.APP_VERSION_NAME
  is '应用版本名称';
comment on column MIP_SYS_APPLICATION.APP_VERSION_CODE
  is '应用版本号';
comment on column MIP_SYS_APPLICATION.APP_OS
  is '应用操作系统类型';
comment on column MIP_SYS_APPLICATION.APP_PKG_NAME
  is '应用包名';
alter table MIP_SYS_APPLICATION
  add constraint PK_MIP_SYS_APPLICATION_ID primary key (ID)
  using index ;
  
create table MIP_SYS_APP_DETAIL
(
  ID                 VARCHAR2(36) not null,
  APP_ICON_URL       VARCHAR2(128) not null,
  APP_URL            VARCHAR2(128) not null,
  APP_SCORE          INTEGER default 0 not null,
  APP_DOWNLOAD_COUNT INTEGER default 0 not null,
  APP_PUBLISHER      VARCHAR2(36) not null,
  APP_DESC           VARCHAR2(1024),
  APP_CREATE_TIME    TIMESTAMP(6) default sysdate not null,
  APP_UPDATE_TIME    TIMESTAMP(6),
  APP_OS_VERSION     VARCHAR2(32),
  APP_SCORE_COUNT    INTEGER default 0 not null,
  APP_STATUS         INTEGER default 0 not null,
  APP_SCREENSHOT     VARCHAR2(1024)
);
comment on table MIP_SYS_APP_DETAIL
  is '应用详情表';
comment on column MIP_SYS_APP_DETAIL.ID
  is '唯一标识';
comment on column MIP_SYS_APP_DETAIL.APP_ICON_URL
  is '应用图标下载地址';
comment on column MIP_SYS_APP_DETAIL.APP_URL
  is '应用下载地址';
comment on column MIP_SYS_APP_DETAIL.APP_SCORE
  is '应用分数';
comment on column MIP_SYS_APP_DETAIL.APP_SCORE_COUNT
  is '应用打分次数';
comment on column MIP_SYS_APP_DETAIL.APP_DOWNLOAD_COUNT
  is '应用下载次数';
comment on column MIP_SYS_APP_DETAIL.APP_PUBLISHER
  is '应用发布者';
comment on column MIP_SYS_APP_DETAIL.APP_DESC
  is '应用描述';
comment on column MIP_SYS_APP_DETAIL.APP_CREATE_TIME
  is '应用发布时间';
comment on column MIP_SYS_APP_DETAIL.APP_UPDATE_TIME
  is '应用更新时间';
comment on column MIP_SYS_APP_DETAIL.APP_OS_VERSION
  is '应用要求的操作系统最低版本';
comment on column MIP_SYS_APP_DETAIL.APP_STATUS
  is '应用的状态（0：正常；1：停用，禁止下载）';
comment on column MIP_SYS_APP_DETAIL.APP_SCREENSHOT
  is '应用截图';
alter table MIP_SYS_APP_DETAIL
  add constraint PK_MIP_SYS_APP_DETAIL_ID primary key (ID)
  using index ;

create table MIP_SYS_APP_TYPE
(
  ID          VARCHAR2(36) not null,
  NAME        VARCHAR2(64) not null,
  DESCRIPTION VARCHAR2(256)
);
comment on table MIP_SYS_APP_TYPE
  is '应用分类表';
comment on column MIP_SYS_APP_TYPE.ID
  is '唯一标识';
comment on column MIP_SYS_APP_TYPE.NAME
  is '应用分类名称';
comment on column MIP_SYS_APP_TYPE.DESCRIPTION
  is '应用分类描述';
alter table MIP_SYS_APP_TYPE
  add constraint PK_MIP_SYS_APP_TYPE_ID primary key (ID)
  using index ;
  
create table MIP_SYS_APP_USERGROUP
(
  ID                 VARCHAR2(36) not null,
  APP_ID             VARCHAR2(36) not null,
  GROUP_ID           VARCHAR2(36) not null
);
comment on table MIP_SYS_APP_USERGROUP
  is '应用分配表';
comment on column MIP_SYS_APP_USERGROUP.ID
  is '唯一标识';
comment on column MIP_SYS_APP_USERGROUP.APP_ID
  is '应用标识';
comment on column MIP_SYS_APP_USERGROUP.GROUP_ID
  is '用户组标识';
alter table MIP_SYS_APP_USERGROUP
  add constraint PK_MIP_SYS_APP_USERGROUP_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_APP_DEPLOY
(
  ID            VARCHAR2(36) not null,
  USER_ID       VARCHAR2(36),
  DEVICE_ID     VARCHAR2(36),
  APP_ID        VARCHAR2(36),
  STATUS        NUMBER(3),
  DOWNLOAD_TIME TIMESTAMP(6)
);
comment on table MIP_SYS_APP_DEPLOY
  is '应用部署情况表';
comment on column MIP_SYS_APP_DEPLOY.ID
  is '唯一标识';
comment on column MIP_SYS_APP_DEPLOY.USER_ID
  is '用户标识';
comment on column MIP_SYS_APP_DEPLOY.DEVICE_ID
  is '设备标识';
comment on column MIP_SYS_APP_DEPLOY.APP_ID
  is '应用标识';
comment on column MIP_SYS_APP_DEPLOY.STATUS
  is '应用部署状态';
comment on column MIP_SYS_APP_DEPLOY.DOWNLOAD_TIME
  is '应用下载时间';
alter table MIP_SYS_APP_DEPLOY
  add constraint PK_MIP_SYS_APP_DEPLOY_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_APP_SCORE
(
  ID          VARCHAR2(36) not null,
  APP_ID      VARCHAR2(36) not null,
  APP_SCORE   NUMBER not null,
  APP_COMMENT VARCHAR2(512),
  USER_ID     VARCHAR2(36),
  SCORE_TIME  TIMESTAMP(6) default sysdate not null
);
comment on table MIP_SYS_APP_SCORE
  is '应用评分记录表';
comment on column MIP_SYS_APP_SCORE.ID
  is '唯一标识';
comment on column MIP_SYS_APP_SCORE.APP_ID
  is '应用标识';
comment on column MIP_SYS_APP_SCORE.APP_SCORE
  is '应用评分';
comment on column MIP_SYS_APP_SCORE.APP_COMMENT
  is '应用评价';
comment on column MIP_SYS_APP_SCORE.USER_ID
  is '用户标识';
comment on column MIP_SYS_APP_SCORE.SCORE_TIME
  is '应用评分时间';
alter table MIP_SYS_APP_SCORE
  add constraint PK_MIP_SYS_APP_SCORE_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_APPTYPE_APP
(
  ID          VARCHAR2(36) not null,
  APP_ID      VARCHAR2(36) not null,
  APP_TYPE_ID VARCHAR2(36) not null
);
comment on table MIP_SYS_APPTYPE_APP
  is '应用与应用分类关联表';
comment on column MIP_SYS_APPTYPE_APP.ID
  is '唯一标识';
comment on column MIP_SYS_APPTYPE_APP.APP_ID
  is '应用标识';
comment on column MIP_SYS_APPTYPE_APP.APP_TYPE_ID
  is '应用分类标识';
alter table MIP_SYS_APPTYPE_APP
  add constraint PK_MIP_SYS_APPTYPE_APP_ID primary key (ID)
  using index ; 
  
create table MIP_SYS_CONFIG
(
  ID             VARCHAR2(36) not null,
  CONFIG_NAME    VARCHAR2(32) not null,
  CONFIG_DESC    VARCHAR2(128),
  CONFIG_CREATOR VARCHAR2(36),
  CREATE_TIME    TIMESTAMP(6),
  CONFIG_CONTENT CLOB,
  CONFIG_TYPE    INTEGER
);
comment on table MIP_SYS_CONFIG
  is '配置表';
comment on column MIP_SYS_CONFIG.ID
  is '唯一标识';
comment on column MIP_SYS_CONFIG.CONFIG_NAME
  is '配置名称';
comment on column MIP_SYS_CONFIG.CONFIG_DESC
  is '配置描述';
comment on column MIP_SYS_CONFIG.CONFIG_CREATOR
  is '配置创建用户标识';
comment on column MIP_SYS_CONFIG.CREATE_TIME
  is '配置创建时间';
comment on column MIP_SYS_CONFIG.CONFIG_CONTENT
  is '配置内容';
comment on column MIP_SYS_CONFIG.CONFIG_TYPE
  is '配置类型';
alter table MIP_SYS_CONFIG
  add constraint PK_MIP_SYS_CONFIG_ID primary key (ID)
  using index ;
  
create table MIP_SYS_CONFIG_DEVICE
(
  ID        VARCHAR2(36) not null,
  DEVICE_ID VARCHAR2(36) not null,
  CONFIG_ID VARCHAR2(36) not null,
  STATUS    INTEGER not null
);
comment on table MIP_SYS_CONFIG_DEVICE
  is '设备与配置关联表';
comment on column MIP_SYS_CONFIG_DEVICE.ID
  is '唯一标识';
comment on column MIP_SYS_CONFIG_DEVICE.DEVICE_ID
  is '设备标识';
comment on column MIP_SYS_CONFIG_DEVICE.CONFIG_ID
  is '配置标识';
comment on column MIP_SYS_CONFIG_DEVICE.STATUS
  is '配置的部署状态';
alter table MIP_SYS_CONFIG_DEVICE
  add constraint PK_MIP_SYS_CONFIG_DEVICE_ID primary key (ID)
  using index ;
  
create table MIP_SYS_STRATEGY
(
  ID               VARCHAR2(36) not null,
  STRATEGY_NAME    VARCHAR2(32) not null,
  STRATEGY_CREATOR VARCHAR2(36),
  CREATE_TIME      TIMESTAMP(6),
  STRATEGY_CONTENT CLOB,
  STRATEGY_DESC    VARCHAR2(128)
);
comment on table MIP_SYS_STRATEGY
  is '策略表';
comment on column MIP_SYS_STRATEGY.ID
  is '唯一标识';
comment on column MIP_SYS_STRATEGY.STRATEGY_NAME
  is '策略名称';
comment on column MIP_SYS_STRATEGY.STRATEGY_CREATOR
  is '策略创建用户标识';
comment on column MIP_SYS_STRATEGY.CREATE_TIME
  is '策略创建时间';
comment on column MIP_SYS_STRATEGY.STRATEGY_CONTENT
  is '策略内容';
comment on column MIP_SYS_STRATEGY.STRATEGY_DESC
  is '策略描述';
alter table MIP_SYS_STRATEGY
  add constraint PK_MIP_SYS_STRATEGY_ID primary key (ID)
  using index ;

create table MIP_SYS_STRATEGY_DEVICE
(
  ID          VARCHAR2(36) not null,
  DEVICE_ID   VARCHAR2(36) not null,
  STRATEGY_ID VARCHAR2(36) not null,
  STATUS      INTEGER not null
);
comment on table MIP_SYS_STRATEGY_DEVICE
  is '设备与策略关联表';
comment on column MIP_SYS_STRATEGY_DEVICE.ID
  is '唯一标识';
comment on column MIP_SYS_STRATEGY_DEVICE.DEVICE_ID
  is '设备标识';
comment on column MIP_SYS_STRATEGY_DEVICE.STRATEGY_ID
  is '策略标识';
comment on column MIP_SYS_STRATEGY_DEVICE.STATUS
  is '策略的部署状态';
alter table MIP_SYS_STRATEGY_DEVICE
  add constraint PK_MIP_SYS_STRATEGY_DEVICE_ID primary key (ID)
  using index ;
  
create table MIP_SYS_NEWS
(
  ID          VARCHAR2(36) not null,
  TYPE        INTEGER not null,
  TITLE       VARCHAR2(256) not null,
  LEAD        VARCHAR2(1024),
  PHOTO       VARCHAR2(128),
  CREATE_TIME TIMESTAMP(6) not null,
  URL         VARCHAR2(128) not null,
  EDITOR      VARCHAR2(32),
  BODY        CLOB
);
comment on table MIP_SYS_NEWS
  is '资讯表';
comment on column MIP_SYS_NEWS.ID
  is '唯一标识';
comment on column MIP_SYS_NEWS.TYPE
  is '资讯类型';
comment on column MIP_SYS_NEWS.TITLE
  is '资讯标题';
comment on column MIP_SYS_NEWS.LEAD
  is '资讯导语';
comment on column MIP_SYS_NEWS.PHOTO
  is '资讯图片';
comment on column MIP_SYS_NEWS.CREATE_TIME
  is '资讯创建时间';
comment on column MIP_SYS_NEWS.URL
  is '资讯地址';
comment on column MIP_SYS_NEWS.EDITOR
  is '资讯来源';
comment on column MIP_SYS_NEWS.BODY
  is '资讯内容';
alter table MIP_SYS_NEWS
  add constraint PK_MIP_SYS_NEWS_ID primary key (ID)
  using index ;
  
create table MIP_SYS_NEWS_DETAIL
(
  ID    VARCHAR2(36) not null,
  MARK  INTEGER default 0 not null,
  LOCAL VARCHAR2(32),
  PICS  VARCHAR2(1024)
);
comment on table MIP_SYS_NEWS_DETAIL
  is '资讯详情表';
comment on column MIP_SYS_NEWS_DETAIL.ID
  is '唯一标识';
comment on column MIP_SYS_NEWS_DETAIL.MARK
  is '资讯标记';
comment on column MIP_SYS_NEWS_DETAIL.LOCAL
  is '资讯短语';
comment on column MIP_SYS_NEWS_DETAIL.PICS
  is '资讯图片';
alter table MIP_SYS_NEWS_DETAIL
  add constraint PK_MIP_SYS_NEWS_DETAIL_ID primary key (ID)
  using index ;
  
create table MIP_SYS_NOTIFICATION
(
  ID          VARCHAR2(36) not null,
  USERNAME    VARCHAR2(36),
  CLIENT_IP   VARCHAR2(32),
  RESRC       VARCHAR2(64),
  MESSAGE_ID  VARCHAR2(64),
  APIKEY      VARCHAR2(64),
  TITLE       VARCHAR2(32),
  MESSAGE     VARCHAR2(32),
  URI         VARCHAR2(128),
  STATUS      VARCHAR2(2),
  CREATE_TIME TIMESTAMP(6),
  UPDATE_TIME TIMESTAMP(6)
);
alter table MIP_SYS_NOTIFICATION
  add constraint PK_MIP_SYS_NOTIFICATION_ID primary key (ID)
  using index ;

