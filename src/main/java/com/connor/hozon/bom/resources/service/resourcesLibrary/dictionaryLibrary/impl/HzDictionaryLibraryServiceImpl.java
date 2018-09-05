package com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary.impl;

import com.connor.hozon.bom.resources.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.dto.response.HzDictionaryLibraryRespDTO;
import com.connor.hozon.bom.resources.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryDao;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.query.HzDictionaryLibraryQuery;
import com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryService;
import com.connor.hozon.bom.resources.util.DateUtil;
import com.connor.hozon.bom.resources.util.PrivilegeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sql.pojo.resourcesLibrary.dictionaryLibrary.HzDictionaryLibrary;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xlf
 * Date: 2018/8/30
 * Time: 15:23
 */
@Service("HzDictionaryLibraryService")
public class HzDictionaryLibraryServiceImpl implements HzDictionaryLibraryService {
    @Autowired
    HzDictionaryLibraryDao hzDictionaryLibraryDao;

    /**
     * 添加数据
     * @param reqDTO
     * @return
     */
    @Override
    public OperateResultMessageRespDTO insertHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO) {
        try {
            boolean b  = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzDictionaryLibrary library = new HzDictionaryLibrary();
            library.setPuid(UUID.randomUUID().toString());
            library.setProfessionCh(reqDTO.getProfessionCh());
            library.setProfessionEn(reqDTO.getProfessionEn());
            library.setClassificationCh(reqDTO.getClassificationCh());
            library.setClassificationEn(reqDTO.getClassificationEn());
            library.setGroupCode(reqDTO.getGroupCode());
            library.setGroupCh(reqDTO.getGroupCh());
            library.setGroupEn(reqDTO.getGroupEn());
            library.setFamillyCode(reqDTO.getFamillyCode());
            library.setFamillyCh(reqDTO.getFamillyCh());
            library.setFamillyEn(reqDTO.getFamillyEn());
            library.setEigenValue(reqDTO.getEigenValue());
            library.setValueDescCh(reqDTO.getValueDescCh());
            library.setValueDescEn(reqDTO.getValueDescEn());
            library.setType(reqDTO.getType());
            library.setValueSource(reqDTO.getValueSource());

            library.setEffectTime(reqDTO.getEffectTime());
            library.setFailureTime(reqDTO.getFailureTime());

            library.setNote(reqDTO.getNote());
            int i = hzDictionaryLibraryDao.insert(library);
            if (i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return null;
    }

    /**
     * 编辑一条数据
     * @param reqDTO
     * @return
     */
    @Override
    public OperateResultMessageRespDTO updateHzDictionaryLibrary(AddHzDictionaryLibraryReqDTO reqDTO) {
        try {
            boolean b  = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            HzDictionaryLibrary library = new HzDictionaryLibrary();
            library.setPuid(reqDTO.getPuid());
            library.setProfessionCh(reqDTO.getProfessionCh());
            library.setProfessionEn(reqDTO.getProfessionEn());
            library.setClassificationCh(reqDTO.getClassificationCh());
            library.setClassificationEn(reqDTO.getClassificationEn());
            library.setGroupCode(reqDTO.getGroupCode());
            library.setGroupCh(reqDTO.getGroupCh());
            library.setGroupEn(reqDTO.getGroupEn());
            library.setFamillyCode(reqDTO.getFamillyCode());
            library.setFamillyCh(reqDTO.getFamillyCh());
            library.setFamillyEn(reqDTO.getFamillyEn());
            library.setEigenValue(reqDTO.getEigenValue());
            library.setValueDescCh(reqDTO.getValueDescCh());
            library.setValueDescEn(reqDTO.getValueDescEn());
            library.setType(reqDTO.getType());
            library.setValueSource(reqDTO.getValueSource());
            library.setEffectTime(reqDTO.getEffectTime());
            library.setFailureTime(reqDTO.getFailureTime());
            library.setNote(reqDTO.getNote());
            int i = hzDictionaryLibraryDao.update(library);
            if (i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
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
                HzDictionaryLibraryRespDTO reqDTO = new HzDictionaryLibraryRespDTO();
                reqDTO.setNo(++num);
                reqDTO.setPuid(library.getPuid());
                reqDTO.setProfessionCh(library.getProfessionCh());
                reqDTO.setProfessionEn(library.getProfessionEn());
                reqDTO.setClassificationCh(library.getClassificationCh());
                reqDTO.setClassificationEn(library.getClassificationEn());
                reqDTO.setGroupCode(library.getGroupCode());
                reqDTO.setGroupCh(library.getGroupCh());
                reqDTO.setGroupEn(library.getGroupEn());
                reqDTO.setFamillyCode(library.getFamillyCode());
                reqDTO.setFamillyCh(library.getFamillyCh());
                reqDTO.setFamillyEn(library.getFamillyEn());
                reqDTO.setEigenValue(library.getEigenValue());
                reqDTO.setValueDescCh(library.getValueDescCh());
                reqDTO.setValueDescEn(library.getValueDescEn());
                reqDTO.setType(library.getType());
                reqDTO.setValueSource(library.getValueSource());
                reqDTO.setEffectTime(DateUtil.formatDirDate(library.getEffectTime()));
                reqDTO.setFailureTime(DateUtil.formatDirDate(library.getFailureTime()));
                reqDTO.setNote(library.getNote());
                reqDTOList.add(reqDTO);
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
                HzDictionaryLibraryRespDTO dto = new HzDictionaryLibraryRespDTO();
                dto.setPuid(library.getPuid());
                dto.setProfessionCh(library.getProfessionCh());
                dto.setProfessionEn(library.getProfessionEn());
                dto.setClassificationCh(library.getClassificationCh());
                dto.setClassificationEn(library.getClassificationEn());
                dto.setGroupCode(library.getGroupCode());
                dto.setGroupCh(library.getGroupCh());
                dto.setGroupEn(library.getGroupEn());
                dto.setFamillyCode(library.getFamillyCode());
                dto.setFamillyCh(library.getFamillyCh());
                dto.setFamillyEn(library.getFamillyEn());
                dto.setEigenValue(library.getEigenValue());
                dto.setValueDescCh(library.getValueDescCh());
                dto.setValueDescEn(library.getValueDescEn());
                dto.setType(library.getType());
                dto.setValueSource(library.getValueSource());
                dto.setEffectTime(DateUtil.formatDirDate(library.getEffectTime()));
                dto.setFailureTime(DateUtil.formatDirDate(library.getEffectTime()));
                dto.setNote(library.getNote());
                return dto;
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
    public OperateResultMessageRespDTO deleteHzDictionaryLibrary(String puid) {
        try {
            boolean b  = PrivilegeUtil.writePrivilege();
            if(!b){
                return OperateResultMessageRespDTO.getFailPrivilege();
            }
            int i = hzDictionaryLibraryDao.delete(puid);
            if (i>0){
                return OperateResultMessageRespDTO.getSuccessResult();
            }
        } catch (Exception e) {
            return OperateResultMessageRespDTO.getFailResult();
        }
        return OperateResultMessageRespDTO.getFailResult();
    }
}
