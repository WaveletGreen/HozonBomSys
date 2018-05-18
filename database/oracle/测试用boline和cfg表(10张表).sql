--根据配置项找打bom行
select * from hz_cfg0_of_bomline_record t2 
       left join HZ_BOM_LINE_RECORD t on t2.p_bomlinepuid=t.p_puid 
       /*where t.p_puid='1bd0c708-8e78-475e-a94e-a519831c7e93'
       where t2.p_bomlinepuid=(
             select t.p_puid from HZ_BOM_LINE_RECORD t 
             where t.p_puid='1bd0c708-8e78-475e-a94e-a519831c7e93') */
select * from hz_cfg0_record
select * from hz_cfg0_of_bomline_record t2 
select * from HZ_BOM_LINE_RECORD t2 

---根据车模配置找到配置项和bomline
select t5.p_cfg0_option_value,t5.p_parse_logic_value,t7.p_cfg0_desc,t7.p_cfg0_family_name,t3.object_name,t8.p_bom_line_id,t8.p_bom_line_block from hz_cfg0_to_model_record t5 
       join hz_cfg0_model_record t4 
            on t5.p_cfg0_model_record=t4.puid 
 /*           and t4.puid in(
                  select t3.puid
                  from hz_cfg0_model_record t3 
\*                  where t3.object_name='低续航豪华型'
*\                  )*/
        right join hz_cfg0_model_record t3 on t3.puid=t4.puid
        
         join hz_cfg0_of_bomline_record t6 on t6.p_to_cfg0_id_of_bl=t5.p_cfg0_id_record
       /*这里如果是右连接查询，则找到的是有匹配bom行的配置项,左链接查询则查找所有的配置项，但有些配置项可能没有关联bom行*/
       /*right*/
        join HZ_BOM_LINE_RECORD t8 on t8.p_puid=t6.p_bomlinepuid
        join hz_cfg0_record t7 on t7.puid=t5.p_cfg0_id_record
        where t8.p_bom_digifax_id='d050cb5e-2f54-4782-92dc-6cf750e0b066'
        
create or replace view getcfg0BomlineOfModel
as
select t5.p_cfg0_option_value,t5.p_parse_logic_value,t7.p_cfg0_desc,t7.p_cfg0_family_name,t3.object_name,t8.p_bom_line_id,t8.p_bom_line_block from hz_cfg0_to_model_record t5 
        join hz_cfg0_model_record t4 on t5.p_cfg0_model_record=t4.puid 
        full   join hz_cfg0_model_record t3 on t3.puid=t4.puid
        full join hz_cfg0_of_bomline_record t6 on t6.p_to_cfg0_id_of_bl=t5.p_cfg0_id_record
       /*这里如果是右连接查询，则找到的是有匹配bom行的配置项,左链接查询则查找所有的配置项，但有些配置项可能没有关联bom行*/
       /*right*/
        full join HZ_BOM_LINE_RECORD t8 on t8.p_puid=t6.p_bomlinepuid
        join hz_cfg0_record t7 on t7.puid=t5.p_cfg0_id_record
        where t8.p_bom_digifax_id=infodba.p_view_param.get_param();
        
select * from getcfg0BomlineOfModel t where infodba.p_view_param.set_param('d050cb5e-2f54-4782-92dc-6cf750e0b066')='d050cb5e-2f54-4782-92dc-6cf750e0b066';

/*80418353-3de6-460e-a012-5e4595c05a6e*/


-----------创建视图，重要
create or replace view getCfg0BomlineOfModel2
as 
select t1.object_name,
       t1.object_desc,
       /*t1.p_cfg0_model_of_main_record,*/
       t2.p_cfg0_model_record,
       t2.p_cfg0_option_value,
       t2.p_parse_logic_value,
       t3.p_cfg0_object_id,
       t3.p_cfg0_desc,
       t3.p_cfg0_family_name,
       t3.p_h9featurename,
       t3.p_h9featureenname,
       t3.p_h9featuredesc,
       /*t5.p_bom_digifax_id,*/
       t4.p_bom_line_name,
       t5.p_bom_line_id,/*
       t5.p_bom_line_block*/
       t5.p_bom_of_which_dept
        from hz_cfg0_model_record t1 
       full join HZ_CFG0_TO_MODEL_RECORD t2 on t2.p_cfg0_model_record=t1.puid
       join hz_cfg0_record t3 on t3.puid=t2.p_cfg0_id_record
       full join hz_cfg0_of_bomline_record t4 on t4.p_to_cfg0_id_of_bl=t3.puid
       left outer join HZ_BOM_LINE_RECORD t5 on t5.p_puid=t4.p_bomlinepuid
       where t1.p_cfg0_model_of_main_record=infodba.p_view_param.get_param()
       order by t1.object_name

select * from getCfg0BomlineOfModel2 t where infodba.p_view_param.set_param('9deb81ea-9544-473b-aeb5-d7683c7753c3')='9deb81ea-9544-473b-aeb5-d7683c7753c3';
              
