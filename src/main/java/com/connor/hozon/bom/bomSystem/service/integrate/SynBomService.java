package com.connor.hozon.bom.bomSystem.service.integrate;

import com.connor.hozon.bom.bomSystem.dao.bom.HzMBomToERPDao;
import com.connor.hozon.bom.bomSystem.helper.IntegrateMsgDTO;
import com.connor.hozon.bom.bomSystem.service.iservice.integrate.ISynBomService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import sql.pojo.bom.HzMBomToERPBean;
import webservice.base.bom.ZPPTCO005;
import webservice.base.helper.UUIDHelper;
import webservice.logic.ReflectBom;
import webservice.option.ActionFlagOption;
import webservice.service.impl.bom5.TransBomService;

import java.util.*;

@Configuration
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
     * @param puidOfUpdate
     * @return
     */
    @Override
    public JSONObject updateByUids(String projectPuid, String puidOfUpdate) {
        JSONObject result = deleteByUids(projectPuid, puidOfUpdate);
        if (result.getBoolean("status")) {
            result = execute(projectPuid, puidOfUpdate, ActionFlagOption.ADD, "更新");
        }
        return result;
    }

    /**
     * 删除时候传
     *
     * @param puidOfDelete 需要删除的BOM行的Puid
     * @param projectPuid  项目的Puid
     * @return
     */
    @Override
    public JSONObject deleteByUids(String projectPuid, String puidOfDelete) {
        return execute(projectPuid, puidOfDelete, ActionFlagOption.DELETE, "删除");
    }

    /**
     * 添加1行
     *
     * @param addedPuid   被添加的BOM行的puid
     * @param projectPuid 项目的PUID
     * @return
     */
    @Override
    public JSONObject addOne(String projectPuid, String addedPuid) {
        return execute(projectPuid, addedPuid, ActionFlagOption.ADD, "添加");
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
        /**
         * 成功项
         */
        List<IntegrateMsgDTO> success = new ArrayList<>();
        /**
         * 失败项
         */
        List<IntegrateMsgDTO> fail = new ArrayList<>();

        JSONObject result = new JSONObject();

        List<HzMBomToERPBean> beanListIs2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 1);
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
            //判断是否是2Y层，2Y层没有父层
            if (mapOfparents.containsKey(bean.getPuid())) {
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
            if (parent != null) {
                //初始化映射
                ReflectBom reflectBom = new ReflectBom(bean);
                //设置父层
                reflectBom.setHeadOfBomLine(parent.getBomLineId());
                //包号进缓存
                if (!packNumOfParent.containsKey(parent.getBomLineId())) {
                    //重新生成包号
                    packNum = UUIDHelper.generateUpperUid();
                    packNumOfParent.put(parent.getBomLineId(), packNum);
                }
                //设置数据包号，从缓存中获取
                reflectBom.setPackNo(packNumOfParent.get(parent.getBomLineId()));
                //设置行号
                reflectBom.setLineNum(bean.getBomOrderNum());
                //第一次发送是传所有，都是新增
                reflectBom.setActionFlag(ActionFlagOption.ADD);
//                //添加进缓存
//                transBomService.getInput().getItem().add(reflectBom.getZpptci005());
//                //将行号和bean进行绑定
//                lineNumofBean.put(bean.getBomOrderNum(), bean);

                if (mapOfPackNo.containsKey(packNum)) {
                    if (mapOfPackNo.get(packNum).containsKey(bean.getBomOrderNum())) {
                        System.out.println("重复的行号");
                    } else {
                        //放进行号集合中
                        mapOfPackNo.get(packNum).put(bean.getBomOrderNum(), reflectBom);
                    }
                } else {
                    Map<String, ReflectBom> _lineNumofBean = new HashMap<>();
                    _lineNumofBean.put(bean.getBomOrderNum(), reflectBom);
                    mapOfPackNo.put(packNum, _lineNumofBean);
                }
            } else {
                mapOfFailure.add(bean);
            }
        }
        int size = 0;
        for (Map<String, ReflectBom> values : mapOfPackNo.values()) {
            for (String key : values.keySet()) {
                transBomService.getInput().getItem().add(values.get(key).getZpptci005());
            }
            transBomService.execute();
            size += values.size();
            List<ZPPTCO005> resultPool = transBomService.getOut().getItem();
            for (ZPPTCO005 zpptco005 : resultPool) {
                ReflectBom bean = mapOfPackNo.get(zpptco005.getZPACKNO()).get(zpptco005.getZITEM());
                if (bean == null) {
                    continue;
                }

                IntegrateMsgDTO msgDTO = new IntegrateMsgDTO();
                msgDTO.setMsg(zpptco005.getMESSAGE());
                msgDTO.setItemId(bean.getChildOfBomLine());
                msgDTO.setPuid(bean.getPuid());

                if ("S".equalsIgnoreCase(zpptco005.getTYPE())) {
                    success.add(msgDTO);
                } else {
                    fail.add(msgDTO);
                }
            }
        }
        System.out.println("--------------------------" + size + "-------------------------");


        result.put("success", success);
        result.put("fail", fail);
        return result;
    }
    /* *//**
     * 一开始同步所有数据到ERP
     *
     * @param projectPuid
     * @return
     *//*
    @Override
    public JSONObject synAllByProjectUid2(String projectPuid) {
        //每次都清空缓存
        transBomService.setClearInputEachTime(true);
        StringBuilder sbs = new StringBuilder();
        StringBuilder sbf = new StringBuilder();
        sbs.append("更新成功项:<br/>");
        sbf.append("更新失败项:<br/>");
        JSONObject result = new JSONObject();

        boolean hasFail = false;

        List<HzMBomToERPBean> beanListIs2Y = hzMBomToERPDao.selectByProjectUidWithCondition(projectPuid, 1);
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

        *//**
     * 筛选puid
     *//*
        beanListIs2Y.forEach(l -> is2YUids.add(l.getPuid()));
        beanListIsnot2Y.forEach(l -> isNot2YUids.add(l.getPuid()));
        *//**
     * 再次获取数据
     *//*
        int count = is2YUids.size() / 1000;

        HzMBomToERPBean bom = new HzMBomToERPBean();

        beanListIs2Y = splitListThenQuery(bom, projectPuid, is2YUids, 1)*//*hzMBomToERPDao.selectByUidOfBatch(projectPuid, is2YUids)*//*;
        beanListIsnot2Y = splitListThenQuery(bom, projectPuid, isNot2YUids, 1)*//* hzMBomToERPDao.selectByUidOfBatch(projectPuid, isNot2YUids)*//*;
        //父层
        beanListOfParent = splitListThenQuery(bom, projectPuid, parentPuids, 2)*//* hzMBomToERPDao.selectbyParentUidOfBatch(projectPuid, parentPuids)*//*;
        //所有的
        beanListOfChildren = splitListThenQuery(bom, projectPuid, children, 1)*//*hzMBomToERPDao.selectByUidOfBatch(projectPuid, children)*//*;


        Map<String, HzMBomToERPBean> mapOf2YUid = new HashMap<>();
        Map<String, HzMBomToERPBean> mapOfIsnot2YUid = new HashMap<>();
        Map<String, HzMBomToERPBean> mapOfparents = new HashMap<>();


        //没有找到父层结构的，不发送，告诉用户哪个发送失败
        List<HzMBomToERPBean> mapOfFailure = new ArrayList<>();
        //行号，记录该行的位置
        Map<String, HzMBomToERPBean> lineNumofBean = new HashMap<>();

        //存储数据包号,包含行号数据
        Map<String, Map<String, HzMBomToERPBean>> mapOfPackNo = new HashMap<>();

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
            //判断是否是2Y层，2Y层没有父层
            if (mapOfparents.containsKey(bean.getPuid())) {
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
            if (parent != null) {
                //初始化映射
                ReflectBom reflectBom = new ReflectBom(bean);
                //设置父层
                reflectBom.setHeadOfBomLine(parent.getBomLineId());
                //包号进缓存
                if (!packNumOfParent.containsKey(parent.getBomLineId())) {
                    //重新生成包号
                    packNum = UUIDHelper.generateUpperUid();
                    packNumOfParent.put(parent.getBomLineId(), packNum);
                }
                //设置数据包号，从缓存中获取
                reflectBom.setPackNo(packNumOfParent.get(parent.getBomLineId()));
                //设置行号
                reflectBom.setLineNum(bean.getBomOrderNum());
                //第一次发送是传所有，都是新增
                reflectBom.setActionFlag(ActionFlagOption.ADD);
                //添加进缓存
                transBomService.getInput().getItem().add(reflectBom.getZpptci005());
                //将行号和bean进行绑定
                lineNumofBean.put(bean.getBomOrderNum(), bean);

                if (mapOfPackNo.containsKey(packNum)) {
                    if (mapOfPackNo.get(packNum).containsKey(bean.getBomOrderNum())) {
                        System.out.println("重复的行号");
                    } else {
                        //放进行号集合中
                        mapOfPackNo.get(packNum).put(bean.getBomOrderNum(), bean);
                    }
                } else {
                    Map<String, HzMBomToERPBean> _lineNumofBean = new HashMap<>();
                    _lineNumofBean.putAll(lineNumofBean);
                    mapOfPackNo.put(packNum, _lineNumofBean);
                }
            } else {
                mapOfFailure.add(bean);
            }
        }

        transBomService.execute();
        List<ZPPTCO005> resultPool = transBomService.getOut().getItem();
        int counter = 0;
        String br = "&emsp;&emsp;";
        for (ZPPTCO005 zpptco005 : resultPool) {
            if ("S".equalsIgnoreCase(zpptco005.getTYPE())) {
                //3开始断行
                if (counter % 3 == 0) {
                    br = "<br/>";
                    counter = 0;
                }
                sbs.append("&emsp;&emsp;&emsp;&emsp;" + lineNumofBean.get(zpptco005.getZITEM()).getBomLineId() + *//*"(" + zpptco005.getMESSAGE() +")"+*//* br);
            } else {
                sbf.append("&emsp;&emsp;&emsp;&emsp;" + lineNumofBean.get(zpptco005.getZITEM()).getBomLineId() + "(" + zpptco005.getMESSAGE() + ")<br/>");
                hasFail = true;
            }
            if (br.equals("<br/>")) {
                br = "&emsp;&emsp;";
            }
            counter++;
        }
        if (hasFail) {
            result.put("msg", sbs.append("<br/>" + sbf).toString());
        } else {
            result.put("msg", sbs.toString());
        }
        result.put("status", true);
        return result;
    }
*/

    /**
     * 执行操作
     *
     * @param projectPuid 项目的puid
     * @param puid        操作BOM行的puid
     * @param option      操作方式
     * @return
     */
    private JSONObject execute(String projectPuid, String puid, ActionFlagOption option, String strOption) {
        //每次都清空缓存
        transBomService.setClearInputEachTime(true);
        StringBuilder sbs = new StringBuilder();
        StringBuilder sbf = new StringBuilder();
        sbs.append(strOption + "成功项:<br/>");
        sbf.append(strOption + "失败项:<br/>");

        boolean hasFail = false;

        JSONObject result = validate(projectPuid, puid);
        if (!result.getBoolean("status")) {
            return result;
        }
        Object objBean = (HzMBomToERPBean) result.get("bean");
        Object objParent = (HzMBomToERPBean) result.get("bean");
        HzMBomToERPBean bean = null;
        HzMBomToERPBean parent = null;
        if (objBean instanceof HzMBomToERPBean) {
            bean = (HzMBomToERPBean) objBean;
        }
        if (objParent instanceof HzMBomToERPBean) {
            parent = (HzMBomToERPBean) objParent;
        }
        String packNum = UUIDHelper.generateUpperUid();
        ReflectBom reflectBom = new ReflectBom(bean);
        //设置父层
        reflectBom.setHeadOfBomLine(parent.getBomLineId());
        //设置数据包号，从缓存中获取
        reflectBom.setPackNo(packNum);
        //设置行号
        reflectBom.setLineNum(bean.getBomOrderNum());
        //设置为删除状态
        reflectBom.setActionFlag(option);
        //添加进缓存
        transBomService.getInput().getItem().add(reflectBom.getZpptci005());

        //执行操作
        transBomService.execute();
        //获取返回值
        List<ZPPTCO005> resultPool = transBomService.getOut().getItem();
        int counter = 0;
        String br = "&emsp;&emsp;";
        for (ZPPTCO005 zpptco005 : resultPool) {
            if ("S".equalsIgnoreCase(zpptco005.getTYPE())) {
                //3开始断行
                if (counter % 3 == 0) {
                    br = "<br/>";
                    counter = 0;
                }
                sbs.append("&emsp;&emsp;&emsp;&emsp;" + bean.getBomLineId() + /*"(" + zpptco005.getMESSAGE() +")"+*/ br);
            } else {
                sbf.append("&emsp;&emsp;&emsp;&emsp;" + bean.getBomLineId() + "(" + zpptco005.getMESSAGE() + ")<br/>");
                hasFail = true;
            }
            if (br.equals("<br/>")) {
                br = "&emsp;&emsp;";
            }
            counter++;
        }
        if (hasFail) {
            result.put("msg", sbs.append("<br/>" + sbf).toString());
        } else {
            result.put("msg", sbs.toString());
        }
        result.put("status", true);
        return result;
    }


    /**
     * 验证数据正确性
     *
     * @param projectPuid
     * @param puidOfDelete
     */
    private JSONObject validate(String projectPuid, String puidOfDelete) {
        JSONObject result = new JSONObject();
        if (projectPuid == null || "".equalsIgnoreCase(projectPuid)) {
            result.put("status", false);
            result.put("result", "请选择项目再操作!");
            return result;
        } else if (puidOfDelete == null || "".equalsIgnoreCase(puidOfDelete)) {
            result.put("status", false);
            result.put("result", "没有选择要删除的BOM行");
            return result;
        }
        List<HzMBomToERPBean> bean = hzMBomToERPDao.selectByUidOfBatch(projectPuid, Collections.singletonList(puidOfDelete));
        if (bean == null || bean.isEmpty()) {
            result.put("status", false);
            result.put("result", "查无数据，请联系系统管理员");
            return result;
        } else if (bean.get(0).getIs2Y().equals("1")) {
            result.put("status", false);
            result.put("result", bean.get(0).getBomLineId() + "是2Y层，没有父层结构");
            return result;
        }
        List<HzMBomToERPBean> parent = hzMBomToERPDao.selectbyParentUidOfBatch(projectPuid, Collections.singletonList(bean.get(0).getBomUid()));
        if (parent == null || parent.isEmpty()) {
            result.put("status", false);
            result.put("result", bean.get(0).getBomLineId() + "没有父层结构,请联系系统管理员");
            return result;
        }
        result.put("status", true);
        result.put("bean", bean.get(0));
        result.put("parent", parent.get(0));
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
