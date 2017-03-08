CREATE TABLE chat_user
(
   chat_id              INT(22) NOT NULL COMMENT '交流频道ID',
   user_id              INT(22) NOT NULL COMMENT '用户ID',
   news_id_read         INT(1) NOT NULL DEFAULT 0 COMMENT '最新消息是否已读',
   PRIMARY KEY(chat_id, user_id)
)
ENGINE =  InnoDB;

ALTER TABLE chat_user COMMENT '交流人员表';


CREATE TABLE chat
(
   chat_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '交流表ID',
   chat_title           TEXT COMMENT '频道标题',
   PRIMARY KEY(chat_id)
)
ENGINE =  InnoDB;

ALTER TABLE chat COMMENT '交流表';


CREATE TABLE chat_logs
(
   logs_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
   chat_id              INT(22) NOT NULL DEFAULT 0 COMMENT '频道ID',
   logs_content         TEXT COMMENT '记录内容',
   logs_type            VARCHAR(255) COMMENT '记录类型',
   create_time          DATETIME COMMENT '记录时间',
   PRIMARY KEY(logs_id)
)
ENGINE =  InnoDB;

ALTER TABLE chat_logs COMMENT '交流记录表';

CREATE TABLE idea
(
   idea_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '创意ID',
   idea_title           VARCHAR(255) COMMENT '创意标题',
   idea_content         TEXT COMMENT '创意内容',
   create_time          DATETIME COMMENT '创建时间',
   create_user_id       INT(22) NOT NULL DEFAULT 0 COMMENT '创建人',
   is_anonymous         INT(1) NOT NULL DEFAULT 0 COMMENT '是否匿名',
   by_audit             INT(1) NOT NULL DEFAULT 0 COMMENT '通过审核',
   is_delete			INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
   good                 INT(10) NOT NULL DEFAULT 0 COMMENT '赞',
   bad                  INT(10) NOT NULL DEFAULT 0 COMMENT '踩',
   PRIMARY KEY(idea_id)
)
ENGINE =  InnoDB;

ALTER TABLE idea COMMENT '创意';


CREATE TABLE idea_vote
(
   idea_id              INT(22) NOT NULL COMMENT '创意ID',
   user_id              INT(22) NOT NULL COMMENT '用户ID',
   PRIMARY KEY(idea_id, user_id)
)
ENGINE =  InnoDB;

ALTER TABLE idea_vote COMMENT '创意投票表';


CREATE TABLE rel_idea_file
(
   idea_id              INT(22) NOT NULL COMMENT '创意ID',
   files_id             INT(22) NOT NULL COMMENT '文件ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_idea_file COMMENT '创意文件表';

CREATE TABLE seek_help
(
   request_id           INT(22) NOT NULL AUTO_INCREMENT,
   seek_help_user_id    INT(22) NOT NULL DEFAULT 0 COMMENT '求助人ID',
   help_title           VARCHAR(255) NOT NULL COMMENT '求助标题',
   help_content         TEXT COMMENT '求助内容',
   help_user_id         INT(22) NOT NULL DEFAULT 0 COMMENT '帮助人ID',
   create_time          DATETIME COMMENT '创建时间',
   solve_time           DATETIME COMMENT '解决时间',
   is_delete            INT(1) NOT NULL DEFAULT 0,
   PRIMARY KEY(request_id)
)
ENGINE =  InnoDB;

ALTER TABLE seek_help COMMENT '求助主表';

CREATE TABLE rel_help_file
(
   request_id           INT(22) NOT NULL COMMENT '求助ID',
   files_id             INT(22) NOT NULL COMMENT '文件ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_help_file COMMENT '求助文件';

CREATE TABLE rel_flow_field
(
   flow_id              INT(22) NOT NULL COMMENT '审批ID',
   field_id             INT(22) NOT NULL COMMENT '条件ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_flow_field COMMENT '审批关联条件';

CREATE TABLE rel_history_role
(
   step_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '步骤ID',
   history_id           INT(22) NOT NULL DEFAULT 0 COMMENT '历史ID',
   role_id              INT(22) NOT NULL DEFAULT 0 COMMENT '角色ID',
   sequences             INT(22) NOT NULL DEFAULT 0 COMMENT '排序字段:每个位置数字相隔100,如第一个位置为100,第二个位置为200,以此类推',
   is_completed         INT(1) NOT NULL DEFAULT 0 COMMENT '是否已审批',
   is_ok                INT(1) NOT NULL DEFAULT 0 COMMENT '单步审核是否通过',
   memo                 TEXT COMMENT '单步审核解释',
   PRIMARY KEY(step_id)
)
ENGINE =  InnoDB;

ALTER TABLE rel_history_role COMMENT '审批历史关联角色';

CREATE TABLE flow_history
(
   history_id           INT(22) NOT NULL AUTO_INCREMENT COMMENT '历史ID',
   flow_name            VARCHAR(255) COMMENT '审批流名称',
   version              INT(5) NOT NULL DEFAULT 0 COMMENT '版本',
   memo                 TEXT COMMENT '审批原因',
   create_time          DATETIME COMMENT '创建时间',
   create_user_id       INT(22) NOT NULL DEFAULT 0 COMMENT '创建用户',
   create_dept_id       INT(22) NOT NULL DEFAULT 0 COMMENT '创建部门',
   is_all_ok            INT(1) NOT NULL DEFAULT 0 COMMENT '审批是否结束',
   flow_condition       TEXT COMMENT '审批条件',
   PRIMARY KEY(history_id)
)
ENGINE =  InnoDB;

ALTER TABLE flow_history COMMENT '审批流历史';

CREATE TABLE flow_setting
(
   flow_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '审批流ID',
   flow_name            VARCHAR(255) COMMENT '审批流名称',
   flow_dec             TEXT COMMENT '审批流描述',
   version              INT(5) NOT NULL DEFAULT 0 COMMENT '版本',
   PRIMARY KEY(flow_id)
)
ENGINE =  InnoDB;

ALTER TABLE flow_setting COMMENT '审批流设置';



CREATE TABLE rel_flow_user_condition
(
   bind_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '绑定ID',
   flow_id              INT(22) NOT NULL DEFAULT 0 COMMENT '审批ID',
   role_id              INT(22) NOT NULL DEFAULT 0 COMMENT '角色ID',
   field_id             INT(22) NOT NULL DEFAULT 0 COMMENT '条件ID',
   formula              VARCHAR(255) COMMENT '条件公式包括:大于>,小于<,等于==,不等于!=,包含in,不包含notin',
   sequences            INT(22) NOT NULL DEFAULT 0 COMMENT '排序',
   PRIMARY KEY(bind_id)
)
ENGINE =  InnoDB;

ALTER TABLE rel_flow_user_condition COMMENT '审批角色绑定条件';


CREATE TABLE condition_field
(
   field_id             INT(22) NOT NULL AUTO_INCREMENT COMMENT '字段ID',
   field_type           VARCHAR(255) COMMENT '字段类型(值有INT=数值型,VARCHAR型字符串型,ENUM=枚举型)',
   field_value          TEXT COMMENT '字段初始值(数值行=0,字符串型="",枚举型为{},时间型为数字)',
   PRIMARY KEY(field_id)
)
ENGINE =  InnoDB;

ALTER TABLE condition_field COMMENT '条件字段';


CREATE TABLE msg
(
   msg_id               INT(22) NOT NULL AUTO_INCREMENT COMMENT '消息ID',
   src_type             INT(2) DEFAULT 0 COMMENT '消息类型(1:系统消息,2:项目消息,3:审批消息,4:用户消息,5广播消息,6创意消息,7求助消息,8交流消息,9目标消息,10任务单消息)',
   src_id               INT(22) NOT NULL DEFAULT 0 COMMENT '来源ID',
   msg_title            VARCHAR(256) COMMENT '消息标题',
   msg_content          TEXT COMMENT '消息内容',
   create_time          DATETIME COMMENT '消息时间',
   create_user_id       INT(22) NOT NULL DEFAULT 0 COMMENT '创建用户',
   attach_flag          INT(1) NOT NULL DEFAULT 0 COMMENT '是否有附件',
   PRIMARY KEY(msg_id)
)
ENGINE =  InnoDB;

ALTER TABLE msg COMMENT '消息';

CREATE TABLE rel_msg_user
(
   msg_id               INT(22) NOT NULL COMMENT '消息ID',
   user_id              INT(22) NOT NULL COMMENT 'UserID',
   is_read              INT(1) NOT NULL DEFAULT 0 COMMENT '是否已读'
)
ENGINE =  InnoDB;

ALTER TABLE rel_msg_user COMMENT '消息相关人员';


CREATE TABLE users
(
   user_id              INT(22) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户ID',
   user_account         VARCHAR(255) NOT NULL COMMENT '用户帐号',
   user_name            VARCHAR(255) DEFAULT '' COMMENT '用户名称',
   password             VARCHAR(255) COMMENT '用户密码',
   user_photo           TEXT COMMENT '用户头像',
   create_time          DATETIME COMMENT '创建时间',
   login_time           DATETIME COMMENT '登录时间',
   privilege            TEXT COMMENT '权限',
   email                TEXT COMMENT '邮箱地址',
   cookie_key           TEXT COMMENT 'Cookie的UUID',
   config_text          TEXT COMMENT '配置文本.文本为json串',
   is_delete			INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
   PRIMARY KEY(user_id)
)
ENGINE =  InnoDB;

ALTER TABLE users COMMENT '用户表';


CREATE TABLE rel_dept_user
(
   rel_id               INT(22) NOT NULL AUTO_INCREMENT,
   dept_id              INT(22) NOT NULL DEFAULT 0 COMMENT '部门ID',
   user_id              INT(22) NOT NULL DEFAULT 0 COMMENT '用户ID',
   role_id              INT(22) NOT NULL DEFAULT 0 COMMENT '角色ID',
   temp                 DATETIME COMMENT '临时授权时间',
   primary key (rel_id)
)
ENGINE =  InnoDB;

ALTER TABLE rel_dept_user COMMENT '组织机构关联用户表';

CREATE TABLE department
(
   dept_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
   parent_id            INT(22) NOT NULL DEFAULT 0 COMMENT '父部门ID',
   dept_name            VARCHAR(255) COMMENT '部门名称',
   has_child         	INT(1) NOT NULL DEFAULT 0 COMMENT '有无子目录',
   create_time          DATETIME COMMENT '部门创建时间',
   is_delete            INT(1) DEFAULT 0 comment '是否删除',
   PRIMARY KEY(dept_id)
)
ENGINE =  InnoDB;

ALTER TABLE department COMMENT '组织机构表';


CREATE TABLE role
(
   role_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
   parent_id            INT(22) NOT NULL DEFAULT 0 COMMENT '父角色ID',
   role_name            VARCHAR(100) COMMENT '角色名称',
   role_privilege       TEXT COMMENT '角色权限',
   is_delete			INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
   PRIMARY KEY(role_id)
)
ENGINE =  InnoDB;

ALTER TABLE role COMMENT '角色表';

CREATE TABLE system_files
(
   files_id             INT(22) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
   files_name           VARCHAR(255) COMMENT '文件名称',
   files_ext            TEXT COMMENT '文件后缀名',
   files_url            TEXT COMMENT '文件地址',
   files_des            TEXT COMMENT '文件描述',
   is_delete            INT(1) NOT NULL DEFAULT 0 COMMENT '是否删除',
   PRIMARY KEY(files_id)
)
ENGINE =  InnoDB;

ALTER TABLE system_files COMMENT '文件表';


CREATE TABLE server_cofig
(
   service_id           INT(22) NOT NULL AUTO_INCREMENT COMMENT '服务器ID',
   server_name          TEXT COMMENT '服务器名称',
   server_vesion        TEXT COMMENT '服务器版本',
   server_encrypt       VARCHAR(16) COMMENT '服务器加密文字',
   PRIMARY KEY(service_id)
)
ENGINE =  InnoDB;

ALTER TABLE server_cofig COMMENT '服务器配置';

CREATE TABLE privilege
(
   privilege_id         INT(22) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
   privilege_name       VARCHAR(100) COMMENT '权限名称',
   privilege_dec        VARCHAR(1024) COMMENT '权限描述',
   privilege_define     VARCHAR(100) COMMENT '权限常量值',
   PRIMARY KEY(privilege_id)
)
ENGINE =  InnoDB;

ALTER TABLE privilege COMMENT '权限表';


CREATE TABLE failedlogins
(
   ip                   VARCHAR(128) NOT NULL DEFAULT '' COMMENT '非法登录的IP地址',
   user_account         VARCHAR(255) NOT NULL DEFAULT '' COMMENT '用户帐号',
   count                INT(1) COMMENT '失败次数',
   lastupdate           DATETIME COMMENT '最后更新时间',
   PRIMARY KEY(ip,user_account)
)
ENGINE =  InnoDB;

ALTER TABLE failedlogins COMMENT '非法登录指登录失败';


CREATE TABLE pro_task
(
   task_id              INT(22) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
   target_id            INT(22) NOT NULL DEFAULT 0 COMMENT '目标ID',
   task_des             TEXT COMMENT '任务描述',
   create_time          DATETIME COMMENT '创建时间',
   start_time           DATETIME COMMENT '任务实际开始时间',
   end_time             DATETIME COMMENT '任务实际结束时间',
   start_task_time      DATETIME COMMENT '任务预期开始时间',
   end_task_time        DATETIME COMMENT '任务预期结束时间',
   status_value         INT(6) NOT NULL DEFAULT 0 COMMENT '任务状态.0-未开始.100-进行中.200-已完结.300-已取消',
   create_user_id       INT(22) NOT NULL DEFAULT 0 COMMENT '创建人',
   receive_task_user    INT(22) NOT NULL DEFAULT 0 COMMENT '任务接收人',
   estimated_hour       FLOAT(4,1) NOT NULL DEFAULT 0 COMMENT '预估时数',
   urgent_degree        INT(1) NOT NULL DEFAULT 3 COMMENT '紧急程度',
   PRIMARY KEY(task_id)
)
ENGINE =  InnoDB;

ALTER TABLE pro_task COMMENT '项目任务单';


CREATE TABLE rel_pro_file
(
   pro_id               INT(22) NOT NULL COMMENT '项目ID',
   files_id             INT(22) NOT NULL COMMENT '文件ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_pro_file COMMENT '项目文件';

CREATE TABLE pro_target
(
   target_id            INT(22) NOT NULL AUTO_INCREMENT COMMENT '目标ID',
   pro_id               INT(22) NOT NULL DEFAULT 0 COMMENT '项目ID',
   target_name          VARCHAR(256) COMMENT '目标名称',
   target_des           TEXT COMMENT '目标描述',
   create_time          DATETIME COMMENT '创建时间',
   create_user_id       INT(22) NOT NULL DEFAULT 0,
   update_time          DATETIME COMMENT '更新时间',
   start_time           DATETIME COMMENT '任务实际开始时间',
   end_time             DATETIME COMMENT '任务实际结束时间',
   start_target_time    DATETIME COMMENT '任务预期开始时间',
   end_target_time      DATETIME COMMENT '任务预期结束时间',
   estimated_day        FLOAT(4,1) NOT NULL DEFAULT 0 COMMENT '预估天数',
   target_status        INT(4) NOT NULL DEFAULT 0 COMMENT '目标状态(0.未开始,100.进行中.200.关闭)',
   PRIMARY KEY(target_id)
)
ENGINE =  InnoDB;

ALTER TABLE pro_target COMMENT '项目目标';


CREATE TABLE rel_target_file
(
   target_id            INT(22) NOT NULL COMMENT '目标ID',
   files_id             INT(22) NOT NULL COMMENT '文件ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_target_file COMMENT '项目目标文件';

CREATE TABLE rel_project_user
(
   pro_id               INT(22) NOT NULL COMMENT '项目ID',
   user_id              INT(22) NOT NULL COMMENT '用户ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_project_user COMMENT '项目组成员表';

CREATE TABLE project
(
   pro_id               INT(22) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
   pro_name             VARCHAR(255) COMMENT '项目名称',
   pro_des              TEXT COMMENT '项目描述',
   create_time          DATETIME COMMENT '创建时间',
   update_time          DATETIME COMMENT '更新时间',
   start_time           DATETIME COMMENT '任务实际开始时间',
   end_time             DATETIME COMMENT '任务实际结束时间',
   start_pro_time       DATETIME COMMENT '任务预期开始时间',
   end_pro_time         DATETIME COMMENT '任务预期结束时间',
   estimated_day        INT(4) NOT NULL DEFAULT 0 COMMENT '预估天数',
   pro_status           INT(4) NOT NULL DEFAULT 0 COMMENT '项目状态(0.未开始,100.进行中.200.关闭)',
   create_user_id       INT(22) NOT NULL DEFAULT 0 COMMENT '创建人',
   is_open              INT(1) NOT NULL DEFAULT 0 COMMENT '是否公开',
   PRIMARY KEY(pro_id)
)
ENGINE =  InnoDB;

ALTER TABLE project COMMENT '项目表';

CREATE TABLE user_history
(
   history_id           INT(22) NOT NULL AUTO_INCREMENT COMMENT '历史记录ID',
   user_id              INT(22) NOT NULL DEFAULT 0 COMMENT '用户ID',
   history_type         INT(2) NOT NULL DEFAULT 0 COMMENT '记录类型(1_审批,2_求助,3_创意,4_项目,5_目标,6_任务单)',
   history_type_id      INT(2) NOT NULL DEFAULT 0 COMMENT '记录类型ID',
   history_status       INT(1) NOT NULL DEFAULT 0 COMMENT '记录状态(0:未知,1:新增,2:完成,3:修改,4:取消)',
   history_content      TEXT COMMENT '记录内容',
   log_time             DATETIME comment '记录时间',
   PRIMARY KEY (history_id)
)
ENGINE =  InnoDB;

ALTER TABLE user_history COMMENT '用户历史记录';

CREATE TABLE project_history
(
   history_id           INT(22) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
   pro_id               INT(22) NOT NULL DEFAULT 0 COMMENT '项目ID',
   target_id            INT(22) NOT NULL DEFAULT 0 COMMENT '目标ID',
   task_id              INT(22) NOT NULL DEFAULT 0 COMMENT '任务单ID',
   history_status       INT(2) NOT NULL DEFAULT 0 COMMENT '记录状态',
   history_content      TEXT COMMENT '历史内容',
   create_time          DATETIME,
   PRIMARY KEY (history_id)
)
ENGINE =  InnoDB;

ALTER TABLE project_history COMMENT '项目历程';

CREATE TABLE rel_task_file
(
   task_id              INT(22) NOT NULL COMMENT '任务单ID',
   files_id             INT(22) NOT NULL COMMENT '文件ID'
)
ENGINE =  InnoDB;

ALTER TABLE rel_task_file COMMENT '任务单文件';

CREATE TABLE child_server_config
(
   child_mark           VARCHAR(255) NOT NULL COMMENT '子版块标识',
   child_field_name     VARCHAR(255) NOT NULL COMMENT '子版块设置字段名',
   child_field_value    INT(3) NOT NULL DEFAULT 0 COMMENT '子版块设置值'
)
ENGINE =  InnoDB;

ALTER TABLE child_server_config COMMENT '子版块配置';


CREATE TABLE user_msg_config
(
   user_id              INT(22) UNSIGNED NOT NULL COMMENT '用户ID',
   idea_send            INT(1) NOT NULL DEFAULT 0 COMMENT '创意通知设定',
   help_send            INT(1) NOT NULL DEFAULT 0 COMMENT '求助通知设定',
   pro_send             INT(1) NOT NULL DEFAULT 0 COMMENT '项目通知设定',
   protarget_send       INT(1) NOT NULL DEFAULT 0 COMMENT '任务目标通知设定'
)
ENGINE =  InnoDB;

ALTER TABLE user_msg_config COMMENT '用户消息推送定制';


CREATE TABLE attention_protarget
(
   user_id              INT(22) UNSIGNED NOT NULL COMMENT '用户ID',
   target_id            INT(22) UNSIGNED NOT NULL COMMENT '目标ID'
)
ENGINE =  InnoDB;

ALTER TABLE attention_protarget COMMENT '用户关注任务';


