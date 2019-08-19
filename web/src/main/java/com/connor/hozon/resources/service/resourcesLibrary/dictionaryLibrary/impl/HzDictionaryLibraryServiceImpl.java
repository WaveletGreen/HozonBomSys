package com.connor.hozon.resources.service.resourcesLibrary.dictionaryLibrary.impl;

import cn.net.connor.hozon.dao.pojo.configuration.feature.HzFeatureValue;
import cn.net.connor.hozon.dao.dao.configuration.feature.HzFeatureValueDao;
import com.connor.hozon.resources.domain.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.resources.domain.dto.request.UpdateHzDictionaryLibraryReqDTO;
import com.connor.hozon.resources.domain.dto.response.HzDictionaryLibraryRespDTO;
import cn.net.connor.hozon.common.entity.WriteResultRespDTO;
import com.connor.hozon.resources.domain.model.HzDictionaryLibraryFactory;
import com.connor.hozon.resources.domain.query.HzDictionaryLibraryQuery;
import com.connor.hozon.resources.mybatis.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryDao;
import com.connor.hozon.resources.page.Page;
import com.connor.hozon.resources.service.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.net.connor.hozon.dao.pojo.depository.dictionaryLibrary.HzDictionaryLibrary;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/30
 * Time: 15:23
 */
@Service("hzDictionaryLibraryService")
public class HzDictionaryLibraryServiceImpl implements HzDictionaryLibraryService {
    @Autowired
    HzDictionaryLibraryDao hzDictionaryLibraryDao;

    @Autowired
    HzFeatureValueDao hzFeatureValueDao;
    /**
     * 添加数据
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO insertHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO) {
        try {
//            boolean b  = PrivilegeUtil.writePrivilege();
//            if(!b){
//                return WriteResultRespDTO.getFailPrivilege();
//            }
            int j = hzDictionaryLibraryDao.findDictionaryLibraryOrCodeToCount(reqDTO.getEigenValue());
            WriteResultRespDTO resultMessageRespDTO = new WriteResultRespDTO();
            if (j>0){
                resultMessageRespDTO.setErrMsg("对不起！您插入的特性值已存在");
                resultMessageRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return resultMessageRespDTO;
            }
            HzDictionaryLibrary library = HzDictionaryLibraryFactory.addDictionaryDTOHzDictionaryLibrary(reqDTO);
            int i = hzDictionaryLibraryDao.insert(library);
            if (i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
        return null;
    }

    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    @Override
    public WriteResultRespDTO updateHzDictionaryLibrary(UpdateHzDictionaryLibraryReqDTO reqDTO) {
        try {
//            boolean b  = PrivilegeUtil.writePrivilege();
//            if(!b){
//                return WriteResultRespDTO.getFailPrivilege();
//            }
            int j =hzDictionaryLibraryDao.findDictionaryLibraryOrCodeToCount(reqDTO.getEigenValue());
            HzDictionaryLibrary hzDictionaryLibrary = hzDictionaryLibraryDao.findDictionaryLibraryOrCode(reqDTO.getEigenValue());
            WriteResultRespDTO resultMessageRespDTO = new WriteResultRespDTO();
            if (j >1){
                resultMessageRespDTO.setErrMsg("对不起！您修改的特性值已存在");
                resultMessageRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
                return  resultMessageRespDTO;
            }
            if (j==1&&hzDictionaryLibrary.getPuid().equals(reqDTO.getPuid())==false){
                resultMessageRespDTO.setErrMsg("对不起！您修改的特性值已存在");
                return resultMessageRespDTO;
            }
            //判断其修改数据是否特性和特性值没变，如没变则修改特性表中的特性描述和特性值描述
            List<HzFeatureValue> hzFeatureValueList = hzFeatureValueDao.selectByDictionaryLibId(reqDTO.getPuid());
            for(HzFeatureValue hzFeatureValue : hzFeatureValueList){
                if(hzFeatureValue.getCfgIsInProcess()!=null&& hzFeatureValue.getCfgIsInProcess()==1){
                    resultMessageRespDTO.setErrMsg("修改的特性已在特性变更流程中，请先结束流程后再进行修改");
                    return resultMessageRespDTO;
                }
            }
            if(hzFeatureValueList !=null&& hzFeatureValueList.size()>0){
                HzDictionaryLibrary hzDictionaryLibrary1 = hzDictionaryLibraryDao.findDictionaryLibrary(reqDTO.getPuid());
                if(hzDictionaryLibrary1.getFamillyCode().equals(reqDTO.getFamillyCode())&&hzDictionaryLibrary1.getEigenValue().equals(reqDTO.getEigenValue())) {
                    HzFeatureValue hzFeatureValue = new HzFeatureValue();
                    hzFeatureValue.setCfgDicLibUid(hzDictionaryLibrary1.getPuid());
                    hzFeatureValue.setpCfg0FamilyDesc(reqDTO.getFamillyCh());
                    hzFeatureValue.setpCfg0Desc(reqDTO.getValueDescCh());
                    if (hzFeatureValueDao.updateDescByDictionaryLib(hzFeatureValue) <= 0 ? true : false) {
                        resultMessageRespDTO.setErrMsg("修改特性表中对应数据的描述失败");
                        return resultMessageRespDTO;
                    }
                }else {
                    resultMessageRespDTO.setErrMsg("您修改了配置字典的特性或特性值，但该条数据已在特性中被引用，请在特性中删除引用后再进行修改");
                    return resultMessageRespDTO;
                }
            }
            HzDictionaryLibrary library = HzDictionaryLibraryFactory.updateDictionaryDTOHzDictionaryLibrary(reqDTO);
            int i = hzDictionaryLibraryDao.update(library);
            if (i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    /**
     * 分页获取字典数据
     * @param query
     * @return
     */
    @Override
    public Page<HzDictionaryLibraryRespDTO> findHzDictionaryLibraryToPage(HzDictionaryLibraryQuery query) {
        try {
            Page<HzDictionaryLibrary> libraries = hzDictionaryLibraryDao.findDictionaryLibraryToPage(query);
            int num = (libraries.getPageNumber()-1)*libraries.getPageSize();
            if(libraries == null || libraries.getResult() == null){
                return  new Page<>(libraries.getPageNumber(),libraries.getPageSize(),0);
            }
            List<HzDictionaryLibrary> list = libraries.getResult();
            List<HzDictionaryLibraryRespDTO>reqDTOList = new ArrayList<>();
            for (HzDictionaryLibrary library :list){
                HzDictionaryLibraryRespDTO respDTO =HzDictionaryLibraryFactory.libraryToRespDTO(library);
                respDTO.setNo(++num);
                reqDTOList.add(respDTO);
            }
            return new Page<>(libraries.getPageNumber(),libraries.getPageSize(),libraries.getTotalCount(),reqDTOList);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据PUID查询一条数据
     * @param puid
     * @return
     */
    @Override
    public HzDictionaryLibraryRespDTO findHzDictionaryLibraryByPuid(String puid) {
        HzDictionaryLibrary library = hzDictionaryLibraryDao.findDictionaryLibrary(puid);
        try {
            if (library !=null){
               return HzDictionaryLibraryFactory.libraryToRespDTO(library);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    /**
     * 删除一条数据
     * @param puid
     * @return
     */
    @Override
    public WriteResultRespDTO deleteHzDictionaryLibrary(String puid) {
        List<HzFeatureValue> hzFeatureValueList = hzFeatureValueDao.selectByDictionaryLibId(puid);
        if(hzFeatureValueList !=null&& hzFeatureValueList.size()>0){
            WriteResultRespDTO writeResultRespDTO = new WriteResultRespDTO();
            writeResultRespDTO.setErrCode(WriteResultRespDTO.FAILED_CODE);
            writeResultRespDTO.setErrMsg("该特性已被引用，不能被删除");
            return writeResultRespDTO;
        }
        try {
//            boolean b  = PrivilegeUtil.writePrivilege();
//            if(!b){
//                return WriteResultRespDTO.getFailPrivilege();
//            }
            int i = hzDictionaryLibraryDao.delete(puid);
            if (i>0){
                return WriteResultRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return WriteResultRespDTO.getFailResult();
        }
        return WriteResultRespDTO.getFailResult();
    }

    @Override
    public HzDictionaryLibrary queryLibraryDTOByCfgObject(String cfgObjectId) {
        return hzDictionaryLibraryDao.findDictionaryLibraryOrCode(cfgObjectId);
    }
}
