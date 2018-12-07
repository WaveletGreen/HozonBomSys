/*
 * Copyright (c) 2018.
 * This file was written by fancyears·milos·malvis @connor. Any question/bug you can't post to 1243093366@qq.com.
 * ALL RIGHTS RESERVED.
 */

package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.helper.UUIDHelper;
import com.connor.hozon.bom.bomSystem.iservice.integrate.ISynBomService;
import integration.base.bom.ZPPTCO005;
import integration.logic.ReflectBom;
import integration.option.ActionFlagOption;
import integration.service.impl.bom5.TransBomService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import sql.pojo.bom.HzMBomToERPBean;

import java.util.*;
/**
 * @Author: Fancyears·Maylos·Malvis
 * @Description: fuck
 * @Date: Created in 2018/9/6 13:19
 * @Modified By:
 */
@Configuration
@Service("synBomService")
public class SynBomService implements ISynBomService {
    /***
     * sql语句in的容量
     */
    private final static int capacity = 1000;
    /**
     * 用来获取传输MBOM的数据
     */
    @Autowired
    HzMBomToERPDao hzMBomToERPDao;
    /**
     * 发送服务
     */
    @Autowired
    TransBomService transBomService;

    /**
     * 执行更新的时候传，有可能时传多条，传多条也演变成传单挑数据传输，先找到父层，再找到子层，父+子一条一条传即可
     *
     * @param projectPuid 项目UID
     * @param   uid 单条BOM的UID
     * @return
     */
    @Override
    public JSONObject updateByUids(String projectPuid, String uid) {
        JSONObject result = null;//deleteByUids(projectPuid, Collections.singletonList(puidOfUpdate));
//        if (result.getBoolean("status")) {
        result = execute(projectPuid, Collections.singletonList(uid), ActionFlagOption.UPDATE);
//        }
        return result;
    }
    /**
     *批量更新
     *
     * @param projectPuid 项目UID
     * @param   uids 当前项目{@code projectPuid}下需要更新数据的BOM的UID合集
     * @return
     */
    @Override
    public JSONObject updateByUids(String projectPuid, List<String> uids) {
        JSONObject result = null;//deleteByUids(projectPuid, Collections.singletonList(puidOfUpdate));
//        if (result.getBoolean("status")) {
        result = execute(projectPuid, uids, ActionFlagOption.UPDATE);
//        }
        return result;
    }

    /**
     * 删除时候传，删除的时候BOM系统直接删除，SAP标记的是这个BOM不可用
     *
     *
     * @param puidsOfDelete 需要删除的BOM行的Puid
     * @param projectPuid   项目的Puid
     * @return
     */
    @Override
    public JSONObject deleteByUids(String projectPuid, List<String> puidsOfDelete) {
        return execute(projectPuid, puidsOfDelete, ActionFlagOption.DELETE);
    }

    /**
     * 默认传输的action代码是{@link ActionFlagOption#ADD}
     * 添加1行，将单条BOM传输到SAP，并更新该条BOM的is_sent字段为1，表示已经发送所SAP1次,
     * 下一次无论是{@link ActionFlagOption#ADD}还是{@link ActionFlagOption#UPDATE}，都将执行{@link ActionFlagOption#UPDATE}
     * 否则SAP会存在2条同名的BOM
     *
     * @param addedPuid   被添加的BOM行的puid
     * @param uid bomline的uid
     * @return
     */
    @Override
    public JSONObject addOne(String uid, String addedPuid) {
        return execute(uid, Collections.singletonList(addedPuid), ActionFlagOption.ADD);
    }
    /**
     * 批量添加到SAP，默认传输的action代码是{@link ActionFlagOption#ADD}
     * 添加n行，将n行BOM传输到SAP，并更新传输成功的BOM的is_sent字段为1，表示已经发送所SAP1次,
     * 下一次无论是{@link ActionFlagOption#ADD}还是{@link ActionFlagOption#UPDATE}，都将执行{@link ActionFlagOption#UPDATE}
     * 否则SAP会存在2条同名的BOM
     *
     *  @param projectUid 项目的UID
     * @param addedPuids   被添加的BOM行的puid
    * @return
     */
    @Override
    public JSONObject addByUids(String projectUid, List<String> addedPuids) {
        return execute(projectUid, addedPuids, ActionFlagOption.ADD);
    }

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
    @Override
    public JSONObject synAllByProjectUid(String projectPuid) {
        //每次都清空缓存
        transBomService.setClearInputEachTime(true);
        if (SynMaterielService.debug)
            transBomService.getInput().getItem().clear();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();

        JSONObject result = new JSONObject();
        //查询所有的2Y层，因为2Y层没有父层，所以2Y层不进行单独传输，只能依赖下层的子层进行2Y层的标识
        List<HzMBomToERPBean> beanListIs2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 1);
        //非2Y层作为所有的子层，2Y层的直接子层要标识其父层是2Y作为传输的标准，其余非2Y都从改List中取出重新搭建BOM的树形结构批量传输
        List<HzMBomToERPBean> beanListIsnot2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 0);
        //父层所有的
        List<HzMBomToERPBean> beanListOfParent;
        //实际上是所有的
        List<HzMBomToERPBean> beanListOfChildren;
        //所有数据
        List<HzMBomToERPBean> all = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 3);

        //筛选所有父id和子id
        List<String> children = new ArrayList<>();
        //父层puid集合
        List<String> parentPuids = new ArrayList<>();
        Set<String> parents = new HashSet<>();

        all.stream().filter(a -> a.getParentUID() != null && !("".equalsIgnoreCase(a.getParentUID()))).forEach(a -> {
            children.add(a.getPuid());
            parents.add(a.getParentUID());
        });
        //存放所有父层puid
        parentPuids.addAll(parents);


        List<String> isNot2YUids = new ArrayList<>();
        List<String> is2YUids = new ArrayList<>();

        /**
         * 筛选puid
         */
        beanListIs2Y.forEach(l -> is2YUids.add(l.getPuid()));
        beanListIsnot2Y.forEach(l -> isNot2YUids.add(l.getPuid()));
        /**
         * 标识
         */
        HzMBomToERPBean bom = new HzMBomToERPBean();

        beanListIs2Y = splitListThenQuery(bom, projectPuid, is2YUids, 1)/*hzMBomToERPDao.selectByUidOfBatch(projectPuid, is2YUids)*/;
        beanListIsnot2Y = splitListThenQuery(bom, projectPuid, isNot2YUids, 1)/* hzMBomToERPDao.selectByUidOfBatch(projectPuid, isNot2YUids)*/;
        //父层
        beanListOfParent = splitListThenQuery(bom, projectPuid, parentPuids, 2)/* hzMBomToERPDao.selectbyParentUidOfBatch(projectPuid, parentPuids)*/;
        //所有的
        beanListOfChildren = splitListThenQuery(bom, projectPuid, children, 1)/*hzMBomToERPDao.selectByUidOfBatch(projectPuid, children)*/;


        Map<String, HzMBomToERPBean> mapOf2YUid = new HashMap<>();
        Map<String, HzMBomToERPBean> mapOfIsnot2YUid = new HashMap<>();
        Map<String, HzMBomToERPBean> mapOfparents = new HashMap<>();

        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;
        //没有找到父层结构的，不发送，告诉用户哪个发送失败
        List<HzMBomToERPBean> mapOfFailure = new ArrayList<>();
//        //行号，记录该行的位置
//        Map<String, HzMBomToERPBean> lineNumofBean = new HashMap<>();

        //存储数据包号,包含行号数据
        Map<String, Map<String, ReflectBom>> mapOfPackNo = new HashMap<>();

        //同一个父的使用同一个包号
        Map<String, String> packNumOfParent = new HashMap<>();

        //2Y层进缓存
        beanListIs2Y.forEach(bean -> {
            mapOf2YUid.put(bean.getBomUid(), bean);
        });
        //parent层进入缓存
        beanListOfParent.forEach(bean -> {
            mapOfparents.put(bean.getBomUid(), bean);
        });

        //包号
        String packNum = UUIDHelper.generateUpperUid();
        for (HzMBomToERPBean bean : beanListOfChildren) {
            HzMBomToERPBean parent = null;
            if (bean.getParentUID() == null) {
                continue;
            }
            //判断是否是2Y层，2Y层没有父层
            if (mapOf2YUid.containsKey(bean.getPuid())) {
                totalOfUnknown++;
                continue;
            } else {
                //从2Y层中取
                if (mapOf2YUid.containsKey(bean.getParentUID())) {
                    parent = mapOf2YUid.get(bean.getParentUID());
                }
                //从parent中取，parent容量>2Y容量
                else if (mapOfparents.containsKey(bean.getParentUID())) {
                    parent = mapOfparents.get(bean.getParentUID());
                }
            }
//            //location cannt be empty
//            if (bean.getStockLocation() == null || "".equals(bean.getStockLocation())) {
//                totalOfUnknown++;
//                continue;
//            }

            if (bean.getIs2Y() == 1) {
                continue;
            }
//            if (mapOf2YUid.containsKey(bean.getPuid())) {
//                continue;
//            }

            /*******包号没有对应上，需要修改**********/
            if (parent != null) {
                if (null == bean.getLineIndex() || "".equals(bean.getLineIndex())) {
                    totalOfUnknown++;
                    continue;
                }
                //初始化映射
                ReflectBom reflectBom = new ReflectBom(bean);
                //设置父层
                reflectBom.setHeadOfBomLine(parent.getBomLineId());
                //包号进缓存
                if (!packNumOfParent.containsKey(parent.getBomLineId())) {
//                    if(!packNumOfParentParent.containsValue(packNum)){
                    packNumOfParent.put(parent.getBomLineId(), packNum);
                    //重新生成包号
                    packNum = UUIDHelper.generateUpperUid();
//                    }
                }
                //设置数据包号，从缓存中获取
                reflectBom.setPackNo(packNumOfParent.get(parent.getBomLineId()));
                //设置行号
                reflectBom.setLineNum(reflectBom.getOrderOfBomLine());
                //第一次发送是传所有，都是新增
                reflectBom.setActionFlag(ActionFlagOption.ADD);
//                //添加进缓存
//                transBomService.getInput().getItem().add(reflectBom.getZpptci005());
//                //将行号和bean进行绑定
//                lineNumofBean.put(bean.getBomOrderNum(), bean);

                if (mapOfPackNo.containsKey(packNumOfParent.get(parent.getBomLineId()))) {
                    if (mapOfPackNo.get(packNumOfParent.get(parent.getBomLineId())).containsKey(bean.getBomOrderNum())) {
                        System.out.println("重复的行号");
                    } else {
                        //放进行号集合中
                        mapOfPackNo.get(packNumOfParent.get(parent.getBomLineId())).put(reflectBom.getLineNum(), reflectBom);
                    }
                } else {
                    Map<String, ReflectBom> _lineNumofBean = new HashMap<>();
                    _lineNumofBean.put(reflectBom.getOrderOfBomLine(), reflectBom);
                    mapOfPackNo.put(packNumOfParent.get(parent.getBomLineId()), _lineNumofBean);
                }
            } else {
                mapOfFailure.add(bean);
                totalOfOutOfParent++;
            }
        }
        Map<String, ReflectBom> coach = new HashMap<>();
        Map<String, ReflectBom> coach2 = new HashMap<>();
        Map<String, ReflectBom> rel = new HashMap<>();
        for (String key : mapOfPackNo.keySet()) {
            coach.clear();
            transBomService.getInput().getItem().clear();
            /***********************part1******************/
/*            for (String k : mapOfPackNo.get(key).keySet()) {
                //一个物料下的相同组件存在多行，只能是工位不一致才允许多行
                coach2.put(k, mapOfPackNo.get(key).get(k));
                for (String _key : coach2.keySet()) {
                    if (_key.equals(k)) {
                        //添加，当前的数据
                        rel.put(_key, coach.get(_key));
                        continue;
                    }
                    //同名的组件
                    if (coach2.get(_key).getChildOfBomLine().equals(mapOfPackNo.get(key).get(k).getChildOfBomLine())) {
                        //查看组件的工位是否一样，工位相同，自增1
                        if (coach2.get(_key).getStation().equals(mapOfPackNo.get(key).get(k).getStation())) {
                            //自增1
                            mapOfPackNo.get(key).get(k).setNumber(String.valueOf(Integer.parseInt(mapOfPackNo.get(key).get(k).getNumber()) + 1));
                            //移除当前标志的缓存位
                            mapOfPackNo.get(key).remove(_key);
                        }
                    }
                }
                transBomService.getInput().getItem().add(mapOfPackNo.get(key).get(k).getZpptci005());
                coach.put(String.valueOf(mapOfPackNo.get(key).get(k).getZpptci005().getPSORTF()), mapOfPackNo.get(key).get(k));
            }*/

            /***********************part2******************/
            for (String k : mapOfPackNo.get(key).keySet()) {
                transBomService.getInput().getItem().add(mapOfPackNo.get(key).get(k).getZpptci005());
                coach.put(String.valueOf(mapOfPackNo.get(key).get(k).getZpptci005().getPSORTF()), mapOfPackNo.get(key).get(k));
            }

            if (!SynMaterielService.debug)
                transBomService.execute();
            /**
             * 总数计数
             */
            total += mapOfPackNo.get(key).size();
            List<ZPPTCO005> resultPool = transBomService.getOut().getItem();
            for (ZPPTCO005 zpptco005 : resultPool) {
                if (null == zpptco005.getPTYPE()) {
                    totalOfUnknown++;
                    continue;
                }
                ReflectBom bean = coach.get(zpptco005.getPZITEM());
                if (bean == null) {
                    totalOfUnknown++;
                    continue;
                }
                IntegrateMsgDTO msgDTO = new IntegrateMsgDTO();
                msgDTO.setMsg(zpptco005.getPMESSAGE());
                msgDTO.setItemId(bean.getChildOfBomLine());
                msgDTO.setPuid(bean.getPuid());

                if ("S".equalsIgnoreCase(zpptco005.getPTYPE())) {
                    success.add(msgDTO);
                    totalOfSuccess++;
                } else {
                    fail.add(msgDTO);
                    totalOfFail++;
                }
            }
        }

        IntegrateMsgDTO msgDTO = new IntegrateMsgDTO();
        msgDTO.setItemId("无");
        msgDTO.setMsg("无");
        if (totalOfFail == 0) {
            fail.add(msgDTO);
        }
        if (totalOfSuccess == 0) {
            success.add(msgDTO);
        }
        System.out.println("--------------------------" + total + "-------------------------");


        result.put("success", success);
        result.put("fail", fail);

        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);
        return result;
    }

    /**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     */
//    @Override
//    public JSONObject synAllByProjectUid2(String projectPuid) {
//        //每次都清空缓存
//        transBomService.setClearInputEachTime(true);
//        StringBuilder sbs = new StringBuilder();
//        StringBuilder sbf = new StringBuilder();
//        sbs.append("更新成功项:<br/>");
//        sbf.append("更新失败项:<br/>");
//        JSONObject result = new JSONObject();
//
//        boolean hasFail = false;
//
//        List<HzMBomToERPBean> beanListIs2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 1);
//        List<HzMBomToERPBean> beanListIsnot2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 0);
//        //父层所有的
//        List<HzMBomToERPBean> beanListOfParent;
//        //实际上是所有的
//        List<HzMBomToERPBean> beanListOfChildren;
//
//        //所有数据
//        List<HzMBomToERPBean> all = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 3);
//
//        //筛选所有父id和子id
//        List<String> children = new ArrayList<>();
//        //父层puid集合
//        List<String> parentPuids = new ArrayList<>();
//        Set<String> parents = new HashSet<>();
//
//        all.stream().filter(a -> a.getParentUID() != null && !("".equalsIgnoreCase(a.getParentUID()))).forEach(a -> {
//            children.add(a.getPuid());
//            parents.add(a.getParentUID());
//        });
//        //存放所有父层puid
//        parentPuids.addAll(parents);
//
//
//        List<String> isNot2YUids = new ArrayList<>();
//        List<String> is2YUids = new ArrayList<>();
//
//        筛选puid
//        beanListIs2Y.forEach(l -> is2YUids.add(l.getPuid()));
//        beanListIsnot2Y.forEach(l -> isNot2YUids.add(l.getPuid()));
//         再次获取数据
//        int count = is2YUids.size() / 1000;
//
//        HzMBomToERPBean bom = new HzMBomToERPBean();
//
//        beanListIs2Y = splitListThenQuery(bom, projectPuid, is2YUids, 1)hzMBomToERPDao.selectByUidOfBatch(projectPuid, is2YUids);
//        beanListIsnot2Y = splitListThenQuery(bom, projectPuid, isNot2YUids, 1) hzMBomToERPDao.selectByUidOfBatch(projectPuid, isNot2YUids);
//        //父层
//        beanListOfParent = splitListThenQuery(bom, projectPuid, parentPuids, 2) hzMBomToERPDao.selectbyParentUidOfBatch(projectPuid, parentPuids);
//        //所有的
//        beanListOfChildren = splitListThenQuery(bom, projectPuid, children, 1)hzMBomToERPDao.selectByUidOfBatch(projectPuid, children);
//
//
//        Map<String, HzMBomToERPBean> mapOf2YUid = new HashMap<>();
//        Map<String, HzMBomToERPBean> mapOfIsnot2YUid = new HashMap<>();
//        Map<String, HzMBomToERPBean> mapOfparents = new HashMap<>();
//
//
//        //没有找到父层结构的，不发送，告诉用户哪个发送失败
//        List<HzMBomToERPBean> mapOfFailure = new ArrayList<>();
//        //行号，记录该行的位置
//        Map<String, HzMBomToERPBean> lineNumofBean = new HashMap<>();
//
//        //存储数据包号,包含行号数据
//        Map<String, Map<String, HzMBomToERPBean>> mapOfPackNo = new HashMap<>();
//
//        //同一个父的使用同一个包号
//        Map<String, String> packNumOfParent = new HashMap<>();
//
//        //2Y层进缓存
//        beanListIs2Y.forEach(bean -> {
//            mapOf2YUid.put(bean.getBomUid(), bean);
//        });
//        //parent层进入缓存
//        beanListOfParent.forEach(bean -> {
//            mapOfparents.put(bean.getBomUid(), bean);
//        });
//
//        //包号
//        String packNum = UUIDHelper.generateUpperUid();
//        for (HzMBomToERPBean bean : beanListOfChildren) {
//            HzMBomToERPBean parent = null;
//            //判断是否是2Y层，2Y层没有父层
//            if (mapOfparents.containsKey(bean.getPuid())) {
//                continue;
//            } else {
//                //从2Y层中取
//                if (mapOf2YUid.containsKey(bean.getParentUID())) {
//                    parent = mapOf2YUid.get(bean.getParentUID());
//                }
//                //从parent中取，parent容量>2Y容量
//                else if (mapOfparents.containsKey(bean.getParentUID())) {
//                    parent = mapOfparents.get(bean.getParentUID());
//                }
//            }
//            if (parent != null) {
//                //初始化映射
//                ReflectBom reflectBom = new ReflectBom(bean);
//                //设置父层
//                reflectBom.setHeadOfBomLine(parent.getBomLineId());
//                //包号进缓存
//                if (!packNumOfParent.containsKey(parent.getBomLineId())) {
//                    //重新生成包号
//                    packNum = UUIDHelper.generateUpperUid();
//                    packNumOfParent.put(parent.getBomLineId(), packNum);
//                }
//                //设置数据包号，从缓存中获取
//                reflectBom.setPackNo(packNumOfParent.get(parent.getBomLineId()));
//                //设置行号
//                reflectBom.setLineNum(bean.getBomOrderNum());
//                //第一次发送是传所有，都是新增
//                reflectBom.setActionFlag(ActionFlagOption.ADD);
//                //添加进缓存
//                transBomService.getInput().getItem().add(reflectBom.getZpptci005());
//                //将行号和bean进行绑定
//                lineNumofBean.put(bean.getBomOrderNum(), bean);
//
//                if (mapOfPackNo.containsKey(packNum)) {
//                    if (mapOfPackNo.get(packNum).containsKey(bean.getBomOrderNum())) {
//                        System.out.println("重复的行号");
//                    } else {
//                        //放进行号集合中
//                        mapOfPackNo.get(packNum).put(bean.getBomOrderNum(), bean);
//                    }
//                } else {
//                    Map<String, HzMBomToERPBean> _lineNumofBean = new HashMap<>();
//                    _lineNumofBean.putAll(lineNumofBean);
//                    mapOfPackNo.put(packNum, _lineNumofBean);
//                }
//            } else {
//                mapOfFailure.add(bean);
//            }
//        }
//
//        transBomService.execute();
//        List<ZPPTCO005> resultPool = transBomService.getOut().getItem();
//        int counter = 0;
//        String br = "&emsp;&emsp;";
//        for (ZPPTCO005 zpptco005 : resultPool) {
//            if ("S".equalsIgnoreCase(zpptco005.getTYPE())) {
//                //3开始断行
//                if (counter % 3 == 0) {
//                    br = "<br/>";
//                    counter = 0;
//                }
//                sbs.append("&emsp;&emsp;&emsp;&emsp;" + lineNumofBean.get(zpptco005.getZITEM()).getBomLineId() + "(" + zpptco005.getMESSAGE() +")"+ br);
//            } else {
//                sbf.append("&emsp;&emsp;&emsp;&emsp;" + lineNumofBean.get(zpptco005.getZITEM()).getBomLineId() + "(" + zpptco005.getMESSAGE() + ")<br/>");
//                hasFail = true;
//            }
//            if (br.equals("<br/>")) {
//                br = "&emsp;&emsp;";
//            }
//            counter++;
//        }
//        if (hasFail) {
//            result.put("msg", sbs.append("<br/>" + sbf).toString());
//        } else {
//            result.put("msg", sbs.toString());
//        }
//        result.put("status", true);
//        return result;
//    }

    /**
     * 执行操作,不考虑2Y层
     *
     * @param projectPuid 项目的puid
     * @param puids       操作BOM行的puid
     * @param option      操作方式
     * @return
     */
    private JSONObject execute(String projectPuid, List<String> puids, ActionFlagOption option) {
        //每次都清空缓存
        transBomService.setClearInputEachTime(true);
        transBomService.getInput().getItem().clear();
        transBomService.getOut().getItem().clear();
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();
        /**
         * 没有父层的项
         */
        List<HzMBomToERPBean> mapOfFailure = new ArrayList<>();

        /***
         * 计数
         */
        int total = 0;
        int totalOfSuccess = 0;
        int totalOfFail = 0;
        int totalOfOutOfParent = 0;
        int totalOfUnknown = 0;

        //同一个父的使用同一个包号
        Map<String, ReflectBom> packNumMap = new HashMap<>();
        //
        Map<String, HzMBomToERPBean> parentPuidMap = new HashMap<>();

        List<HzMBomToERPBean> is2Y = new ArrayList<>();
        List<HzMBomToERPBean> beans = new ArrayList<>();
        List<HzMBomToERPBean> parents = new ArrayList<>();
        JSONObject result = validate(projectPuid, puids, beans, parents);
        parents.forEach(p -> {
//            parentPuidMap.put(p.getBomUid(), p);
            parentPuidMap.put(p.getBomUid(), p);
        });

        if (!result.getBoolean("status")) {
            return result;
        }
        for (HzMBomToERPBean bean : beans) {
            if (parentPuidMap.containsKey(bean.getParentUID())) {
                String packNum = UUIDHelper.generateUpperUid();
                ReflectBom reflectBom = new ReflectBom(bean);
                //设置父层
                reflectBom.setHeadOfBomLine(parentPuidMap.get(bean.getParentUID()).getBomLineId());
                //设置数据包号，从缓存中获取
                reflectBom.setPackNo(packNum);
                //设置行号
                reflectBom.setLineNum(reflectBom.getOrderOfBomLine());
                //设置状态，代码好像少了
                // TODO: 2018/11/29 根据option进行设置action代码，首先判断 bean 的状态是否已经传输过sap，不能盲目设置option
//                //动作描述代码
                if (option == ActionFlagOption.ADD) {
                    //没有发送过，添加发送
                    if (null == bean.getIsSended() || bean.getIsSended() == 0) {
                        reflectBom.setActionFlag(option);
                    }
                    //有发送过，执行更新
                    else {
                        reflectBom.setActionFlag(ActionFlagOption.UPDATE);
                    }
                }
                //执行更新或删除
                else {
                    reflectBom.setActionFlag(option);
                }

                reflectBom.setActionFlag(option);
                transBomService.getInput().getItem().add(reflectBom.getZpptci005());
                packNumMap.put(packNum, reflectBom);
            } else {
                mapOfFailure.add(bean);
                //无父项数自增
                totalOfOutOfParent++;
            }
        }
        //执行操作
        if (!SynMaterielService.debug)
            transBomService.execute();
        //获取返回值
        List<ZPPTCO005> resultPool = transBomService.getOut().getItem();
        for (ZPPTCO005 zpptco005 : resultPool) {
            //总数自增
            total++;
            if (zpptco005 == null || zpptco005.getPTYPE() == null || "".equals(zpptco005.getPTYPE())) {
                //未知错误数自增
                totalOfUnknown++;
                continue;
            }
            ReflectBom bean = packNumMap.get(zpptco005.getPPACKNO());
            if (bean == null) {
                //未知错误数自增
                totalOfUnknown++;
                continue;
            }
            IntegrateMsgDTO msgDTO = new IntegrateMsgDTO();
            msgDTO.setMsg(zpptco005.getPMESSAGE());
            msgDTO.setItemId(bean.getChildOfBomLine());
            msgDTO.setPuid(bean.getPuid());
            if ("S".equalsIgnoreCase(zpptco005.getPTYPE())) {
                success.add(msgDTO);
                //todo 用list存储已经发送成功的BOM，记录packNo和BOM 的UID，存储到数据库中，因为断点回传的是packNo，想要与BOM对应得重新开一张表记录对应值
                //成功数自增
                totalOfSuccess++;
            } else {
                fail.add(msgDTO);
                //失败数自增
                totalOfFail++;
            }
        }
        IntegrateMsgDTO msgDTO = new IntegrateMsgDTO();
        msgDTO.setItemId("无");
        msgDTO.setMsg("无");
        if (totalOfFail == 0) {
            fail.add(msgDTO);
        }
        if (totalOfSuccess == 0) {
            success.add(msgDTO);
        }
        System.out.println("--------------------------" + total + "-------------------------");
        if (totalOfFail > 0) {
            result.put("status", false);
        } else {
            result.put("status", true);
        }
        //todo 添加更新MBOM的 is_sent字段，标识已经发送过一次SAP
        result.put("success", success);
        result.put("fail", fail);
        result.put("total", total);
        result.put("totalOfSuccess", totalOfSuccess);
        result.put("totalOfFail", totalOfFail);
        result.put("totalOfOutOfParent", totalOfOutOfParent);
        result.put("totalOfUnknown", totalOfUnknown);

        return result;
    }


    /**
     * 验证数据正确性
     *
     * @param projectPuid
     * @param puids
     * @param beans
     * @param parents
     */
    private JSONObject validate(String projectPuid, List<String> puids, List<HzMBomToERPBean> beans, List<HzMBomToERPBean> parents) {
        JSONObject result = new JSONObject();
        HzMBomToERPBean bom = new HzMBomToERPBean();
        if (projectPuid == null || "".equalsIgnoreCase(projectPuid)) {
            result.put("status", false);
            result.put("result", "请选择项目再操作!");
            return result;
        } else if (puids == null || puids.isEmpty()) {
            result.put("status", false);
            result.put("result", "没有选择要删除的BOM行");
            return result;
        }
        List<HzMBomToERPBean> bean = splitListThenQuery(bom, projectPuid, puids, 1);
        if (bean == null || bean.isEmpty()) {
            result.put("status", false);
            result.put("result", "查无数据，请联系系统管理员");
            return result;
        } else if ("1".equals(bean.get(0).getIs2Y())) {
            result.put("status", false);
            result.put("result", bean.get(0).getBomLineId() + "是2Y层，没有父层结构");
            return result;
        }
        /***获取父节点**/
        List<String> parentPuid = new ArrayList<>();
        bean.forEach(b -> parentPuid.add(b.getParentUID()));

        List<HzMBomToERPBean> parent = splitListThenQuery(bom, projectPuid, parentPuid, 2);
        if (parent == null || parent.isEmpty()) {
            result.put("status", false);
            result.put("result", bean.get(0).getBomLineId() + "没有父层结构,请联系系统管理员");
            return result;
        }
        result.put("status", true);
        beans.addAll(bean);
        parents.addAll(parent);
        return result;
    }


    /**
     * 拆分list，然后查询数据库
     *
     * @param t           类型
     * @param projectPuid 项目puid
     * @param list        uid集合
     * @param byUid       方式，用PUID还是P_BOM_PUID方式查
     * @return
     */
    public <T> List<T> splitListThenQuery(T t, String projectPuid, List<String> list, int byUid) {
        int intCount = list.size() / capacity;
        int index = 0;
        int endIndex;
        List<T> result = null;

        if (intCount == 0) {
            return executeByType(t, projectPuid, list, byUid);
        } else if (intCount > 0) {
            endIndex = index + capacity;
            result = new ArrayList<>();
            while (true) {
                //第一次查0-1000，以后都从1000-2000...查
                result.addAll(executeByType(t, projectPuid, list.subList(index, endIndex), byUid));
                //向后截列表
                index += capacity;
                //加1000
                endIndex = index + capacity;
                if (endIndex >= list.size()) {
                    endIndex = list.size();
                    result.addAll(executeByType(t, projectPuid, list.subList(index, endIndex), byUid));
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 根据类型进行筛选
     *
     * @param t
     * @param projectPuid
     * @param list
     * @param byUid
     * @param <T>
     * @return
     */
    private <T> List<T> executeByType(T t, String projectPuid, List<String> list, int byUid) {
        switch (byUid) {
            //找子x
            case 1:
                return (List<T>) hzMBomToERPDao.selectByUidOfBatch(projectPuid, list);
            case 2:
                return (List<T>) hzMBomToERPDao.selectbyParentUidOfBatch(projectPuid, list);
            default:
                return null;
        }
    }
}
