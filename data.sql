
ALTER TABLE `mp_component_table` 
MODIFY COLUMN `is_pagination` tinyint(1) NOT NULL COMMENT '是否分页（0：否，1：是），树形表格不存在分页设置' AFTER `table_title`,
MODIFY COLUMN `is_multi_choose` tinyint(1) NULL DEFAULT NULL COMMENT '是否复选（0：否，1：是）树表格不存在此设置' AFTER `table_type`,
MODIFY COLUMN `is_open_layer` tinyint(1) NULL DEFAULT NULL COMMENT '是否开启分层（0：否，1：是）树表格默认开启' AFTER `end_label`,
MODIFY COLUMN `is_open_searchbox` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用搜索框（0：否，1：是）' AFTER `title_align_strategy`,
MODIFY COLUMN `is_open_title_filter` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用表头筛选（0：否，1：是）' AFTER `is_open_searchbox`;

ALTER TABLE `mp_component_table_column` 
MODIFY COLUMN `is_font_bold` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否字体加粗(0:否，1：是)' AFTER `hierarchy`,
MODIFY COLUMN `is_freeze_column` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否冻结列（0：否，1：是）' AFTER `is_font_bold`,
MODIFY COLUMN `is_add_to_searchbox` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否加入搜索框查询（0：否，1：是）' AFTER `is_freeze_column`,
MODIFY COLUMN `is_allow_order` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否允许表头排序(0:否，1：是)' AFTER `search_strategy`,
MODIFY COLUMN `is_hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏（0：否，1：是）' AFTER `is_allow_order`,
MODIFY COLUMN `is_open_hyperlink` tinyint(1) NULL DEFAULT 0 COMMENT '是否启用超链接（0：否，1：是）' AFTER `is_hidden`;

ALTER TABLE `mp_component_tree` 
MODIFY COLUMN `is_open_layer` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启快速分层(0:否，1：是)' AFTER `choose_type`;

ALTER TABLE `mp_component_table` 
CHANGE COLUMN `is_open_searchbox` `is_open_search_box` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用搜索框（0：否，1：是）' AFTER `title_align_strategy`;

ALTER TABLE `mp_component_table_column` 
CHANGE COLUMN `is_add_to_searchbox` `is_add_to_search_box` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否加入搜索框查询（0：否，1：是）' AFTER `is_freeze_column`;