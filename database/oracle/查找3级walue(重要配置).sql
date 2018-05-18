--先用infodba创建包
            
create or replace package p_view_param  is
   function set_param(cfglvl2 varchar2) return varchar2;
   function get_param  return varchar2;
   function set_parent(cfgItemId varchar2) return varchar2;
   function get_parent  return varchar2;
end p_view_param; 

--再创建包主体
create or replace package body p_view_param is
       my_cfglvl2 varchar2(1048);
       my_cfgItemId varchar2(1048);
       function set_param(cfglvl2 varchar2) return varchar2 is
       begin
         my_cfglvl2:=cfglvl2;
         return cfglvl2;
        end;
          /*获取第二层*/
       function get_param return varchar2 is
       begin
         return my_cfglvl2;
       end;
      /*获取第一层*/
        function set_parent(cfgItemId varchar2) return varchar2 is
       begin
         my_cfgItemId:=cfgItemId;
         return cfgItemId;
        end;

       function get_parent return varchar2 is
       begin
         return my_cfgItemId;
       end;
end p_view_param;


--寻找到选项值
create or replace view cfglvl3
as 
WITH 
   cte_0000000009906FC0_5 AS(
     SELECT t_02.puid 
            AS FamAllocRev, t_01.rwso_threadu 
            AS FamAllocThrd 
            FROM PWORKSPACEOBJECT t_01 , PCFG0ABSCONFIGURATORWSO t_02 , PPOM_OBJECT t_03 
            WHERE ( ( t_02.puid IS NOT NULL  
            AND t_03.ppid IN  (1333,1347 )  ) 
            AND ( t_01.puid = t_02.puid 
            AND t_02.puid = t_03.puid ) ) )  , 
  cte_0000000009906FC0_11 AS (
    SELECT t_04.puid 
            AS FAThrd, t_05.rcfg0TargetThreadu 
            AS FATarget, t_04.rcfg0ProductItemu 
            AS FAContext 
            FROM PCFG0ABSALLOCATIONTHREAD t_04 , PCFG0ABSASSOCIATIONTHREAD t_05 
            WHERE ( t_04.puid IS NOT NULL  
            AND ( t_04.puid = t_05.puid ) ) )  , 
  cte_0000000009906FC0_20 AS (
    SELECT  DISTINCT t_08.puid 
            AS FamRev, t_06.rwso_threadu 
            AS FamThrd 
            FROM PWORKSPACEOBJECT t_06 , PPOM_OBJECT t_07 , PCFG0ABSFAMILY t_08 
            WHERE ( ( t_08.puid = p_view_param.get_param() 
            AND ( t_07.ppid = 1319 ) ) 
            AND ( t_06.puid = t_07.puid 
            AND t_07.puid = t_08.puid ) ) )  , 
  cte_0000000009906FC0_29 AS (
    SELECT  DISTINCT t_09.puid 
            AS FamPC 
            FROM PCFG0PRODUCTITEM t_09 
            WHERE t_09.puid = p_view_param.get_parent() )  , 
  cte_0000000009906FC0_37 AS (
    SELECT t_010.puid 
            AS VAThrd, t_011.rcfg0TargetThreadu 
            AS VATarget, t_010.rcfg0ProductItemu 
            AS VAContext 
            FROM PCFG0ABSALLOCATIONTHREAD t_010 , PCFG0ABSASSOCIATIONTHREAD t_011 
            WHERE ( t_010.puid IS NOT NULL  
            AND ( t_010.puid = t_011.puid ) ) )  , 
  cte_0000000009906FC0_43 AS (
            SELECT t_013.puid 
            AS ValAllocRev, t_012.rwso_threadu 
            AS ValAllocThrd 
            FROM PWORKSPACEOBJECT t_012 , PCFG0ABSCONFIGURATORWSO t_013 , PPOM_OBJECT t_014 
            WHERE ( ( t_013.puid IS NOT NULL  
            AND t_014.ppid IN  (1333,1347 )  ) 
            AND ( t_012.puid = t_013.puid 
            AND t_013.puid = t_014.puid ) ) )  , 
  cte_0000000009906FC0_51 AS (
            SELECT t_016.puid 
            AS ValRev, t_016.rcfg0OptionFamilyThreadu 
            AS ValFamThrd, t_015.rwso_threadu 
            AS ValThrd 
            FROM PWORKSPACEOBJECT t_015 , PCFG0ABSOPTIONVALUE t_016 
            WHERE ( t_016.puid IS NOT NULL  
            AND ( t_015.puid = t_016.puid ) ) )  , 
  cte_0000000009906FC0_60 AS (
            SELECT  DISTINCT t_017.puid 
            AS ValPC 
            FROM PCFG0PRODUCTITEM t_017 
            WHERE t_017.puid = p_view_param.get_parent()  )
     SELECT  
            DISTINCT 
                t_033.FamAllocRev,t_033.FamAllocThrd, t_033.FAThrd, t_033.FATarget, t_033.FAContext, t_033.FamRev, t_033.FamThrd, 
                t_033.FamPC, t_033.VAThrd, t_033.VATarget, t_033.VAContext, t_033.ValAllocRev, t_033.ValAllocThrd, t_033.ValRev, 
                t_033.ValFamThrd, t_033.ValThrd, t_033.ValPC 
            FROM (
         SELECT t_029.FamAllocRev,t_029.FamAllocThrd, t_031.FAThrd, t_031.FATarget, t_031.FAContext, t_030.FamRev, t_030.FamThrd, 
                t_032.FamPC, t_028.VAThrd, t_028.VATarget, t_028.VAContext, t_028.ValAllocRev, t_028.ValAllocThrd, t_028.ValRev, 
                t_028.ValFamThrd, t_028.ValThrd, t_028.ValPC 
            FROM cte_0000000009906FC0_5 t_029 
            JOIN (
         SELECT t_025.FAThrd, t_025.FATarget, t_025.FAContext 
            FROM cte_0000000009906FC0_11 t_025) t_031 
            ON  t_029.FamAllocThrd = t_031.FAThrd 
            JOIN (
        SELECT  t_027.FamRev, t_027.FamThrd 
            FROM cte_0000000009906FC0_20 t_027) t_030 
            ON  t_031.FATarget = t_030.FamThrd 
            JOIN (
         SELECT t_026.FamPC 
            FROM cte_0000000009906FC0_29 t_026) t_032 
            ON t_031.FAContext = t_032.FamPC 
            LEFT OUTER JOIN (
         SELECT t_023.VAThrd, t_023.VATarget, t_023.VAContext, t_024.ValAllocRev, t_024.ValAllocThrd, t_022.ValRev, t_022.ValFamThrd, t_022.ValThrd, t_021.ValPC 
            FROM cte_0000000009906FC0_37 t_023 
            JOIN (
         SELECT t_019.ValAllocRev, t_019.ValAllocThrd
            FROM cte_0000000009906FC0_43 t_019) t_024 
            ON  t_023.VAThrd = t_024.ValAllocThrd 
            JOIN (
         SELECT t_018.ValRev, t_018.ValFamThrd, t_018.ValThrd 
            FROM cte_0000000009906FC0_51 t_018) t_022 
            ON t_023.VATarget = t_022.ValThrd JOIN (
         SELECT t_020.ValPC 
            FROM cte_0000000009906FC0_60 t_020) t_021 
            ON t_023.VAContext = t_021.ValPC) t_028 
            ON t_030.FamThrd = t_028.ValFamThrd) t_033 ;
            
--寻找到族组
create or replace view getcfglvl1 as
WITH 
    cte_0000000008EEE9C0_5 AS (
      SELECT t_02.puid
             AS GrpAllocRev, t_01.rwso_threadu 
             AS GrpAllocThread
             FROM PWORKSPACEOBJECT t_01 , PCFG0ABSCONFIGURATORWSO t_02 , PPOM_OBJECT t_03 
             WHERE ( ( t_02.puid IS NOT NULL  
             AND t_03.ppid IN  (1333,1347 )  ) 
             AND ( t_01.puid = t_02.puid 
             AND t_02.puid = t_03.puid ) ) ),

    cte_0000000008EEE9C0_11 AS (
      SELECT t_04.puid
             AS GAThrd, t_05.rcfg0TargetThreadu
             AS GATarget, t_04.rcfg0ProductItemu
             AS GAContext
             FROM PCFG0ABSALLOCATIONTHREAD t_04 , PCFG0ABSASSOCIATIONTHREAD t_05
             WHERE ( t_04.puid IS NOT NULL  
                   AND ( t_04.puid = t_05.puid )) ),
    cte_0000000008EEE9C0_17 AS (
      SELECT t_07.puid FROM PWORKSPACEOBJECT t_06 , PCFG0ABSFAMILYGROUP t_07 , PFND0WSOTHREAD t_08 , PPOM_OBJECT t_09 
            WHERE ( ( ( t_06.rwso_threadu = t_08.puid ) 
                  AND (  UPPER(t_08.pfnd0ThreadId)  
                  LIKE  UPPER( '%' )  
                        AND t_09.ppid IN  (1243,1268 )  ) )
                        AND ( t_06.puid = t_07.puid
                        AND t_08.puid = t_09.puid ) ) )  ,
    cte_0000000008EEE9C0_28 AS (
      SELECT  DISTINCT t_010.puid AS GrpPC FROM PCFG0PRODUCTITEM t_010
              WHERE t_010.puid = p_view_param.get_param() )  , 
    cte_0000000008EEE9C0_36 AS (
      SELECT t_012.puid AS GroupRev, t_011.pvalu_0 AS GrpFamilyThrds
              FROM PCFG0FAMILYTHREADS t_011 , PCFG0ABSFAMILYGROUP t_012
                   WHERE ( t_012.puid IS NOT NULL
                   AND ( t_011.puid = t_012.puid ) ) )  , 
    cte_0000000008EEE9C0_44 AS (
      SELECT t_014.puid AS FamAllocRev, 
             t_013.rwso_threadu AS FamAllocThrd
              FROM PWORKSPACEOBJECT t_013 , PCFG0ABSCONFIGURATORWSO t_014 , PPOM_OBJECT t_015 
              WHERE ( ( t_014.puid IS NOT NULL  AND t_015.ppid IN  (1333,1347 )  ) 
              AND ( t_013.puid = t_014.puid AND t_014.puid = t_015.puid ) ) )  , 
    cte_0000000008EEE9C0_50 AS (
      SELECT t_016.puid AS FAThrd, 
             t_017.rcfg0TargetThreadu AS FATarget, 
             t_016.rcfg0ProductItemu AS FAContext 
             FROM PCFG0ABSALLOCATIONTHREAD t_016 , PCFG0ABSASSOCIATIONTHREAD t_017 
               WHERE ( t_016.puid IS NOT NULL  AND ( t_016.puid = t_017.puid ) ) )  ,
    cte_0000000008EEE9C0_58 AS (
      SELECT t_019.puid AS FamRev,
             t_018.rwso_threadu AS FamThrd 
             FROM PWORKSPACEOBJECT t_018 , PCFG0ABSFAMILY t_019
             WHERE ( t_019.puid IS NOT NULL  AND ( t_018.puid = t_019.puid ) ) )  ,
   cte_0000000008EEE9C0_67 AS (
      SELECT  DISTINCT t_020.puid AS FamPC FROM PCFG0PRODUCTITEM t_020 WHERE t_020.puid =p_view_param.get_param()
      )  
      SELECT  DISTINCT 
              t_040.GrpAllocRev, t_040.GrpAllocThread, t_040.GAThrd, t_040.GATarget,
              t_040.GAContext, t_040.GrpRev
              , t_040.GrpThrd, t_040.GrpPC, t_040.GroupRev, t_040.GrpFamilyThrds, t_040.FamAllocRev, t_040.FamAllocThrd, t_040.FAThrd, t_040.FATarget, t_040.FAContext, t_040.FamRev, t_040.FamThrd, t_040.FamPC
      FROM (
          SELECT 
              t_034.GrpAllocRev, t_034.GrpAllocThread, t_039.GAThrd,t_039.GATarget, 
              t_039.GAContext, t_038.GrpRev
              , t_038.GrpThrd, t_035.GrpPC, t_037.GroupRev, t_037.GrpFamilyThrds, t_036.FamAllocRev, t_036.FamAllocThrd, t_036.FAThrd, t_036.FATarget, t_036.FAContext, t_036.FamRev, t_036.FamThrd, t_036.FamPC 
    
          FROM cte_0000000008EEE9C0_5 t_034 
          JOIN (
               SELECT t_031.GAThrd, t_031.GATarget, t_031.GAContext 
               FROM cte_0000000008EEE9C0_11 t_031) t_039 
               ON t_034.GrpAllocThread = t_039.GAThrd 
               JOIN (
                       SELECT  DISTINCT t_022.puid AS GrpRev,t_021.rwso_threadu AS GrpThrd 
                       FROM PCFG0ABSFAMILYGROUP t_022 
                       JOIN cte_0000000008EEE9C0_17 t_023
                       ON t_022.puid = t_023.puid
                       JOIN PWORKSPACEOBJECT t_021 
                       ON t_022.puid = t_021.puid ) t_038 
                       ON t_039.GATarget = t_038.GrpThrd 
                       JOIN (
                           SELECT t_032.GrpPC 
                           FROM cte_0000000008EEE9C0_28 t_032) t_035
                           ON t_039.GAContext = t_035.GrpPC 
                           LEFT OUTER JOIN (
                                 SELECT t_033.GroupRev, t_033.GrpFamilyThrds 
                                 FROM cte_0000000008EEE9C0_36 t_033) t_037 
                           ON t_038.GrpRev = t_037.GroupRev 
                           FULL OUTER JOIN (
                                            SELECT t_027.FamAllocRev, t_027.FamAllocThrd, t_030.FAThrd, t_030.FATarget, t_030.FAContext, t_029.FamRev, t_029.FamThrd, t_028.FamPC
                                            FROM cte_0000000008EEE9C0_44 t_027 
                                            JOIN (
                                                 SELECT t_024.FAThrd, t_024.FATarget, t_024.FAContext 
                                                 FROM cte_0000000008EEE9C0_50 t_024
                                                 ) t_030 
                                            ON t_027.FamAllocThrd = t_030.FAThrd 
                                            JOIN (
                                                 SELECT t_026.FamRev, t_026.FamThrd 
                                                 FROM cte_0000000008EEE9C0_58 t_026
                                                 ) t_029 
                                            ON t_030.FATarget = t_029.FamThrd
                                            JOIN (/*找到配置项首行的item（没错，是个item）的puid*/
                                                 SELECT t_025.FamPC 
                                                 FROM cte_0000000008EEE9C0_67 t_025
                                                 ) t_028 
                                            ON t_030.FAContext = t_028.FamPC
                                            ) t_036 
          ON t_037.GrpFamilyThrds = t_036.FamThrd
                                     ) t_040 ;
                                     
--根据产品配置器的Item的puid查找配置模型
create or replace view get_model_cfg_by_item_puid
as
WITH cte_00000000056498C0_6 AS (
     SELECT  
           DISTINCT t_01.puid 
           AS Cfg0ProductContext__0 
           FROM PCFG0PRODUCTITEM t_01 
           WHERE t_01.puid = p_view_param.get_param() )  ,
     cte_00000000056498C0_12 AS (
     SELECT t_02.puid 
           AS ImanRelation_1, t_02.rprimary_objectu 
           AS ImanRelation_2, t_02.rsecondary_objectu 
           AS ImanRelation_3 
           FROM PIMANRELATION t_02 
           WHERE t_02.puid IS NOT NULL  )  , 
     cte_00000000056498C0_20 AS (
     SELECT t_03.puid 
           AS VariantRule_4 
           FROM PVARIANTRULE t_03 
           WHERE t_03.puid IS NOT NULL  )  
     
     SELECT  
           DISTINCT 
                    t_09.Cfg0ProductContext__0, 
                    t_09.ImanRelation_1
                    , t_09.ImanRelation_2, t_09.ImanRelation_3, t_09.VariantRule_4 , t_012.pobject_name
           FROM (
             SELECT 
                    t_07.Cfg0ProductContext__0, 
                    t_08.ImanRelation_1
                    , t_08.ImanRelation_2, t_08.ImanRelation_3 , t_06.VariantRule_4
             FROM cte_00000000056498C0_6 t_07 
                  JOIN (
                       SELECT t_05.ImanRelation_1, t_05.ImanRelation_2, t_05.ImanRelation_3 
                       FROM cte_00000000056498C0_12 t_05) t_08 
                       ON t_07.Cfg0ProductContext__0 = t_08.ImanRelation_2 
                       JOIN (
                            SELECT t_04.VariantRule_4 
                            FROM cte_00000000056498C0_20 t_04) t_06 
                      ON t_08.ImanRelation_3 = t_06.VariantRule_4) t_09 
                      LEFT OUTER JOIN (
                                 SELECT t_011.puid,t_010.pobject_name 
                                 FROM PVARIANTRULE t_011,PWORKSPACEOBJECT t_010 
                                 WHERE t_011.puid = t_010.puid ) t_012 
                     ON t_09.VariantRule_4 = t_012.puid 
                     ORDER BY t_012.pobject_name ASC  ;
                     
---根据产品配置器的id查找到配置模型
create or replace view get_model_by_item_id
as
  WITH cte_000000000917E310_3 AS (
       SELECT t_06.puid 
       FROM PFND0WSOTHREAD t_01 , PCFG0ABSMODELFAMILYTHREAD t_02 , PITEM t_03 , PWORKSPACEOBJECT t_04 , PPOM_OBJECT t_05 , PCFG0ABSFAMILY t_06 
             WHERE ( ( ( ( ( t_04.rwso_threadu = t_02.puid ) 
             AND t_05.ppid IN  (1245,1252,1274,1256,1277 )  ) 
             AND  UPPER(t_01.pfnd0ThreadId)  LIKE  UPPER( '%' )  ) 
                  AND ( ( t_04.rwso_threadu = t_02.puid ) 
                  AND ( ( t_02.rcfg0OwningProductItemu = t_03.puid ) 
                  AND  ( UPPER(t_03.pitem_id)  =  UPPER( p_view_param.get_param() )  ) ) ) ) 
                  AND ( t_01.puid = t_02.puid 
                    AND t_04.puid = t_05.puid 
                    AND t_05.puid = t_06.puid ) ) )  , 
         cte_000000000917E310_11 AS (
         SELECT t_07.puid 
                AS Model__2, t_07.rcfg0ModelFamilyThreadu 
                AS Model__3 
         FROM PCFG0ABSMODEL t_07 
                WHERE t_07.puid IS NOT NULL  )  
                
         SELECT  
                DISTINCT 
                      t_014.SearchModelFamily__0, t_014.SearchModelFamily__1, t_014.Model__2, t_014.Model__3 
                      FROM (
                           SELECT  
                                 DISTINCT t_012.puid 
                                 AS SearchModelFamily__0, t_010.rwso_threadu 
                                 AS SearchModelFamily__1, t_013.Model__2, t_013.Model__3 
                                 FROM PCFG0ABSFAMILY t_012 
                                 JOIN cte_000000000917E310_3 t_09 
                                 ON t_012.puid = t_09.puid 
                                 JOIN PWORKSPACEOBJECT t_010 
                                 ON t_012.puid = t_010.puid  
                                 JOIN PPOM_OBJECT t_011 
                                 ON t_012.puid = t_011.puid 
                                 AND t_011.ppid IN  (1245,1252,1274,1256,1277 )   
                                 LEFT OUTER JOIN (
                                      SELECT t_08.Model__2, t_08.Model__3 
                                      FROM cte_000000000917E310_11 t_08) t_013 
                                 ON t_010.rwso_threadu = t_013.Model__3) t_014 ;


/*授权到某个用户即可*/
grant  select on cfglvl3  to HOZON
grant  select on get_model_cfg_by_item_puid  to HOZON
grant  select on get_model_by_item_id  to HOZON

grant  select on getcfglvl1  to HOZON
grant execute on p_view_param to HOZON

---传入产品配置器的item的puid，查找到其下面的所有选项值
select t.GrpRev,t.FamRev,t.GAContext from infodba.getcfglvl1 t where infodba.p_view_param.set_param('R1bpLyl446t5TA')='R1bpLyl446t5TA'
---传入产品配置器的puid，查找到下层的所有配置好配置规则的模型
select t3.VariantRule_4,t3.pobject_name from infodba.get_model_cfg_by_item_puid t3 where infodba.p_view_param.set_param('R1bpLyl446t5TA')='R1bpLyl446t5TA'
---根据产品配置的器item_id，获取到模型，并不是配置好的规则模型
select v.SearchModelFamily__0,v.Model__2 from infodba.get_model_by_item_id v where infodba.p_view_param.set_param('006576')='006576'
----参数1是族组的puid，参数2是产品配置器的puid，传入2个参数，获取到参数1的选项族的下方的所有选项族
select t2.ValRev,t2.FamRev from infodba.cfglvl3 t2 where infodba.p_view_param.set_param('wDfpNa7X46t5TA')='wDfpNa7X46t5TA' and infodba.p_view_param.set_parent('R1bpLyl446t5TA')='R1bpLyl446t5TA'
--该方法是测试
select * from infodba.getcfglvl1 t1 where infodba.p_view_param.set_param('R1bpLyl446t5TA')='R1bpLyl446t5TA'
--测试
select * from infodba.cfglvl3 t2 where infodba.p_view_param.set_param('wDfpNa7X46t5TA')='wDfpNa7X46t5TA' and infodba.p_view_param.set_parent('R1bpLyl446t5TA')='R1bpLyl446t5TA'

       
