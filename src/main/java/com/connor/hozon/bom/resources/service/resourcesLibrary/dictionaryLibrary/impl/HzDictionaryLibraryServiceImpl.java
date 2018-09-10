package com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary.impl;

import com.connor.hozon.bom.resources.domain.dto.request.AddHzDictionaryLibraryReqDTO;
import com.connor.hozon.bom.resources.domain.dto.response.HzDictionaryLibraryRespDTO;
import com.connor.hozon.bom.resources.domain.dto.response.OperateResultMessageRespDTO;
import com.connor.hozon.bom.resources.domain.model.HzDictionaryLibraryFactory;
import com.connor.hozon.bom.resources.domain.query.HzDictionaryLibraryQuery;
import com.connor.hozon.bom.resources.mybatis.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryDao;
import com.connor.hozon.bom.resources.page.Page;
import com.connor.hozon.bom.resources.service.resourcesLibrary.dictionaryLibrary.HzDictionaryLibraryService;
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
            int j = hzDictionaryLibraryDao.findDictionaryLibraryOrCodeToCount(reqDTO.getEigenValue());
            OperateResultMessageRespDTO resultMessageRespDTO = new OperateResultMessageRespDTO();
            if (j>0){
                resultMessageRespDTO.setErrMsg("对不起！您插入的特性值已存在");
                resultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return resultMessageRespDTO;
            }
            HzDictionaryLibrary library = HzDictionaryLibraryFactory.addDictionaryDTOHzDictionaryLibrary(reqDTO);
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
            int j =hzDictionaryLibraryDao.findDictionaryLibraryOrCodeToCount(reqDTO.getEigenValue());
            HzDictionaryLibrary hzDictionaryLibrary = hzDictionaryLibraryDao.findDictionaryLibraryOrCode(reqDTO.getEigenValue());
            OperateResultMessageRespDTO resultMessageRespDTO = new OperateResultMessageRespDTO();
            if (j >1){
                resultMessageRespDTO.setErrMsg("对不起！您修改的特性值已存在");
                resultMessageRespDTO.setErrCode(OperateResultMessageRespDTO.FAILED_CODE);
                return  resultMessageRespDTO;
            }
            if (j==1&&hzDictionaryLibrary.getPuid().equals(reqDTO.getPuid())==false){
                resultMessageRespDTO.setErrMsg("对不起！您修改的特性值已存在");
                return resultMessageRespDTO;
            }
            HzDictionaryLibrary library = HzDictionaryLibraryFactory.addDictionaryDTOHzDictionaryLibrary(reqDTO);
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
                HzDictionaryLibraryRespDTO respDTO =HzDictionaryLibraryFactory.libraryToRespDTO(library);
                respDTO.setNo(num++);
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
