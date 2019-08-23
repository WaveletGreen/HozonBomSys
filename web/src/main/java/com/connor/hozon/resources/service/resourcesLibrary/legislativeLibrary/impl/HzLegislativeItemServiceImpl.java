package com.connor.hozon.resources.service.resourcesLibrary.legislativeLibrary.impl;

import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import cn.net.connor.hozon.dao.pojo.depository.legislativeLibrary.HzLegislativeItem;
import com.connor.hozon.bom.resources.domain.dto.response.HzLegislativeItemResDTO;
import com.connor.hozon.resources.domain.dto.request.AddHzLegislativeReqDTO;
import com.connor.hozon.resources.domain.dto.request.UpdateHzLegislativeReqDTO;
import com.connor.hozon.resources.domain.model.HzLegislativeFactory;
import com.connor.hozon.resources.domain.query.HzLegislativeItemQuery;
import com.connor.hozon.resources.mybatis.resourcesLibrary.legislativeLibrary.HzLegislativeItemDao;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.resourcesLibrary.legislativeLibrary.HzLegislativeItemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("HzLegislativeItemService")
public class HzLegislativeItemServiceImpl implements HzLegislativeItemService {

    @Autowired
    private HzLegislativeItemDao hzLegislativeItemDao;

    /**
     * 分页获取法规件库整车级的数据
     *
     * @param query
     * @return
     */
    @Override
    public Page<HzLegislativeItemResDTO> findHzLegislativeItemToPage(HzLegislativeItemQuery query) {
        try {
            Page<HzLegislativeItem> libraries = hzLegislativeItemDao.findItemToPage(query);
            int num = (libraries.getPageNumber() - 1) * libraries.getPageSize();
            if (libraries == null || libraries.getResult() == null) {
                return new Page<>(libraries.getPageNumber(), libraries.getPageSize(), 0);
            }
            List<HzLegislativeItem> list = libraries.getResult();
            List<HzLegislativeItemResDTO> respDTOList = new ArrayList<>();
            for (HzLegislativeItem library : list) {
                HzLegislativeItemResDTO respDTO = new HzLegislativeItemResDTO();
                respDTO.setNo(++num);
                respDTO.setPuid(library.getPuid());
                respDTO.setLegislativeName(library.getLegislativeName());
                respDTO.setLegislativeNo(library.getLegislativeNo());
                respDTO.setNoticeNo(library.getNoticeNo());
                respDTO.setApplicableModels(library.getApplicableModels());
                respDTO.setEplId(library.getEplId());
                respDTO.setPartId(library.getPartId());
                respDTO.setPartName(library.getPartName());
                respDTO.setSupplier(library.getSupplier());
                respDTO.setSupplierNo(library.getSupplierNo());
                respDTO.setTechnologyDesc(library.getTechnologyDesc());
                respDTO.setApplyDepa(library.getApplyDepa());
                respDTO.setDutyEngineer(library.getDutyEngineer());
                if (Integer.valueOf(1).equals(library.getIsHaveCcc())) {
                    respDTO.setIsHaveCcc("Y");
                } else if (Integer.valueOf(0).equals(library.getIsHaveCcc())) {
                    respDTO.setIsHaveCcc("N");
                }
                if (Integer.valueOf(1).equals(library.getIsHaveTest())) {
                    respDTO.setIsHaveTest("Y");
                } else if (Integer.valueOf(0).equals(library.getIsHaveTest())) {
                    respDTO.setIsHaveTest("N");
                }
                respDTO.setRemarks(library.getRemarks());
                respDTO.setInsertTime(library.getInsertTime());
                respDTO.setUpdateTime(library.getUpdateTime());
                respDTO.setStatus(library.getStatus());
                respDTO.setLegislativeUid(library.getLegislativeUid());
                respDTOList.add(respDTO);
            }
            return new Page<>(libraries.getPageNumber(), libraries.getPageSize(), libraries.getTotalCount(), respDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获得单条整车级数据
     *
     * @param puid
     * @return
     */
    @Override
    public HzLegislativeItemResDTO findHzLegislativeItemById(String puid) {
        HzLegislativeItem hzLegislativeItem = hzLegislativeItemDao.selectByPuid(puid);
        if (hzLegislativeItem!=null){
            HzLegislativeItemResDTO respDTO = new HzLegislativeItemResDTO();
            respDTO.setPuid(hzLegislativeItem.getPuid());
            respDTO.setLegislativeName(hzLegislativeItem.getLegislativeName());
            respDTO.setLegislativeNo(hzLegislativeItem.getLegislativeNo());
            respDTO.setNoticeNo(hzLegislativeItem.getNoticeNo());
            respDTO.setApplicableModels(hzLegislativeItem.getApplicableModels());
            respDTO.setEplId(hzLegislativeItem.getEplId());
            respDTO.setPartId(hzLegislativeItem.getPartId());
            respDTO.setPartName(hzLegislativeItem.getPartName());
            respDTO.setSupplier(hzLegislativeItem.getSupplier());
            respDTO.setSupplierNo(hzLegislativeItem.getSupplierNo());
            respDTO.setTechnologyDesc(hzLegislativeItem.getTechnologyDesc());
            respDTO.setApplyDepa(hzLegislativeItem.getApplyDepa());
            respDTO.setDutyEngineer(hzLegislativeItem.getDutyEngineer());
            if (Integer.valueOf(1).equals(hzLegislativeItem.getIsHaveCcc())) {
                respDTO.setIsHaveCcc("Y");
            } else if (Integer.valueOf(0).equals(hzLegislativeItem.getIsHaveCcc())) {
                respDTO.setIsHaveCcc("N");
            }
            if (Integer.valueOf(1).equals(hzLegislativeItem.getIsHaveTest())) {
                respDTO.setIsHaveTest("Y");
            } else if (Integer.valueOf(0).equals(hzLegislativeItem.getIsHaveTest())) {
                respDTO.setIsHaveTest("N");
            }
            respDTO.setRemarks(hzLegislativeItem.getRemarks());
            respDTO.setInsertTime(hzLegislativeItem.getInsertTime());
            respDTO.setUpdateTime(hzLegislativeItem.getUpdateTime());
            respDTO.setStatus(hzLegislativeItem.getStatus());
            respDTO.setLegislativeUid(hzLegislativeItem.getLegislativeUid());
            return  respDTO;
        }
        return null;
    }

    /**
     * 添加一条法规件
     *
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO addHzLegislativeRecord(AddHzLegislativeReqDTO reqDTO) {
        String legislativeNo = reqDTO.getLegislativeNo();
        if (StringUtils.isBlank(legislativeNo)) {
            return WriteResultRespDTO.failResultRespDTO("法规件编码不能为空!");
        }
        int count = hzLegislativeItemDao.selectCountBylegislativeNo(legislativeNo);
        if (count > 0) {
            return WriteResultRespDTO.failResultRespDTO("当前法规件编码已存在!");
        }

        HzLegislativeItem legItemReq = HzLegislativeFactory.addLegReqDTOToRecord(reqDTO);

        int inReqCou = hzLegislativeItemDao.insertLeg(legItemReq);//插入法规件表
        if (inReqCou > 0) {
            int inItemCou = hzLegislativeItemDao.insertItem(legItemReq);//插入法规件Item表
            if (inItemCou <= 0) {
                return WriteResultRespDTO.getFailResult();
            }
        } else {
            return WriteResultRespDTO.getFailResult();
        }

        return WriteResultRespDTO.getSuccessResult();
    }

    @Override
    public WriteResultRespDTO updateHzLegislativeRecord(UpdateHzLegislativeReqDTO reqDTO) {

        if(reqDTO == null || reqDTO.getPuid() == null){
            return WriteResultRespDTO.IllgalArgument();
        }
        HzLegislativeItem hzLegislativeItem = hzLegislativeItemDao.selectByPuid(reqDTO.getPuid());
        if (hzLegislativeItem==null){
            return WriteResultRespDTO.failResultRespDTO("当前要修改的法规件不存在！");
        }

        HzLegislativeItem legItemReq = HzLegislativeFactory.updateLegReqDTOToRecord(reqDTO);
        int upReqCou = hzLegislativeItemDao.updateHzLegislative(legItemReq);
        if (upReqCou <= 0) {
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getSuccessResult();
    }
}
