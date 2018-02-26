create database pfmp CHARACTER SET utf8 COLLATE utf8_general_ci;
use pfmp;

# 公司内部员工表
CREATE TABLE user_info(
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  seq_id CHAR (100) not null COMMENT '序列号',
  pick_name varchar(50) not null COMMENT '帐号',
  password char(20) not null COMMENT '密码',
  mobile CHAR(11) COMMENT '手机号',
  photo char(100) not NULL COMMENT '头像',
  status INT not NULL DEFAULT 0 COMMENT '状态',
  email varchar(50) COMMENT '邮箱',
  role int NOT NULL DEFAULT 0 COMMENT '角色',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE INDEX `uk_pick_name_pwd` (`pick_name`,`password`),
  INDEX `idx_seq_id` (`seq_id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


# 账单
CREATE TABLE billing_record_info (
  id INT(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  seq_id CHAR (100) not null COMMENT '序列号',
  billing_type CHAR (100) not null COMMENT '账单类型',
  type int not null DEFAULT 1 COMMENT '账单类型',
  add_time DATE not null COMMENT '添加时间',
  money_num DECIMAL(15,4) not null DEFAULT 0.0000 COMMENT '金额',
  money_desc varchar(1000) COMMENT '账单描述',
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (id),
  INDEX `idx_seq_id_type` (`seq_id`, `type`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

# 消息表
create table user_message_info(
  id int primary key auto_increment,
  receiver char(50) not null,
  sender char(50) not null,
  type char(50) not null,
  title char(200) not null,
  content varchar(1000),
  status int default 0,
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=utf8;

# 访客ip监控信息表
create table monitor_ipv4_info(
  id int primary key auto_increment,
  type char(50) not null,
  seq_id char(50),
  pick_Name char(50),
  ipv4 char(20) not NULL,
  address char(150),
  net_type char(50),
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=utf8;

# 信息反馈
create table feed_back_info(
  id int primary key auto_increment,
  type char(50) not null COMMENT '类型',
  seq_id char(50) not NULL COMMENT 'seq_id',
  pick_Name char(50) not NULL COMMENT '用户名',
  photo char(100) not NULL COMMENT '头像',
  content text not NULL COMMENT '内容',
  status int not null default 0 COMMENT '管理员处理状态',
  level int default 1  COMMENT '级别',
  up_id int COMMENT '上级编号',
  create_time timestamp not null DEFAULT CURRENT_TIMESTAMP
)ENGINE=INNODB DEFAULT CHARSET=utf8;
